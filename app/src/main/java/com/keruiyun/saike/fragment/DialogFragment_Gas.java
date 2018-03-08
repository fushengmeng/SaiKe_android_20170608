package com.keruiyun.saike.fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.serialservice.SerialSaunaThread;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 气体状态
 */

public class DialogFragment_Gas extends BaseDialogFragment {


    @BindView(R.id.txt_err)
    TextView txtErr;
    @BindView(R.id.txt_Nor)
    TextView txtNor;
    @BindView(R.id.txt_low)
    TextView txtLow;
    ViewHolder[] viewHolders=new ViewHolder[7];

    @Override
    public int loadContentView() {
        return R.layout.fragment_gas;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        viewHolders[0]=new ViewHolder(findById(R.id.layout_oxygen));
        viewHolders[1]=new ViewHolder(findById(R.id.layout_laughing));
        viewHolders[2]=new ViewHolder(findById(R.id.layout_nitrogen));
        viewHolders[3]=new ViewHolder(findById(R.id.layout_co2));

        viewHolders[4]=new ViewHolder(findById(R.id.layout__pa));
        viewHolders[5]=new ViewHolder(findById(R.id.layout_air));
        viewHolders[6]=new ViewHolder(findById(R.id.layout_argon));
        initUi();
    }
    int[] icon=new int[]{R.drawable.sk_qtzt_13,R.drawable.sk_qtzt_15,R.drawable.sk_qtzt_18};

    private void initUi(){
        String[] name=getActivity().getResources().getStringArray(R.array.arr_fragment_gas);

        for (int i=0;i<viewHolders.length;i++){
            viewHolders[i].imgGas.setImageResource(R.drawable.sk_qtzt_13);
            if (name[i].contains("2")){
                SpannableString spannableString=new SpannableString(name[i]);
                RelativeSizeSpan sizeSpan01 = new RelativeSizeSpan(0.7f);
                int charAt=name[i].indexOf("2");
                spannableString.setSpan(sizeSpan01, charAt, charAt+1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                viewHolders[i].txtGasName.setText(spannableString);
            }else {
                viewHolders[i].txtGasName.setText(name[i]);
            }

        }
    }

    @Override
    public void updateSerialData(int addr,short data) {
        super.updateSerialData(addr,data);
        switch (addr){
            case SerialSaunaThread.ADDR_AIR_ALARM:
                // 氧气
                int isO2High = data & 0x01;
                int isO2Low = (data >> 1) & 0x01;
                viewHolders[0].updateStatus(isO2High, isO2Low);
                // 氮气
                int isNitrogenHigh = (data >> 2) & 0x01;
                int isNitrogenLow = (data >> 3) & 0x01;
                viewHolders[2].updateStatus(isNitrogenHigh, isNitrogenLow);
                // 笑气
                int isLaughHigh = (data >> 4) & 0x01;
                int isLaughLow = (data >> 5) & 0x01;
                viewHolders[1].updateStatus(isLaughHigh, isLaughLow);
                // 氩气
                int isArgonHigh = (data >> 6) & 0x01;
                int isArgonLow = (data >> 7) & 0x01;
                viewHolders[6].updateStatus(isArgonHigh, isArgonLow);
                // 压缩空气
                int isCompressO2High = (data >> 8) & 0x01;
                int isCompressO2Low = (data >> 9) & 0x01;
                viewHolders[5].updateStatus(isCompressO2High, isCompressO2Low);
                break;
            case SerialSaunaThread.ADDR_OTHER_ALARM:
                //二氧化碳
                int isCarbonHigh = data & 0x01;
                int isCarbonLow = (data >> 1) & 0x01;
                viewHolders[3].updateStatus(isCarbonHigh, isCarbonLow);

                //负压吸引
                int isNegHigh = (data >> 2) & 0x01;
                int isNegLow = (data >> 3) & 0x01;
                viewHolders[4].updateStatus(isNegHigh, isNegLow);
                break;
            case SerialSaunaThread.ADDR_AIR_PRESS_01://氧气
                viewHolders[0].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_02://氮气
                viewHolders[2].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_03://笑气
                viewHolders[1].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_04://氩气
                viewHolders[6].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_05://压缩空气
                viewHolders[5].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_06://二氧化碳
                viewHolders[3].setValue(String.format("%.2f", data/100.0f));
                break;

            case SerialSaunaThread.ADDR_AIR_PRESS_07://负压吸引
                viewHolders[4].setValue(String.valueOf(data));
                break;
        }

    }



    class ViewHolder {
        @BindView(R.id.txt_gas_name)
        TextView txtGasName;
        @BindView(R.id.img_gas)
        ImageView imgGas;
        @BindView(R.id.txt_gas_value)
        TextView txtGasValue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void setValue(String value){
            txtGasValue.setText(value+"");
        }

        void updateStatus(int high, int low){
            imgGas.setImageResource(icon[0]);

            if (high == 1) {
                imgGas.setImageResource(icon[1]);
            }
            if (low == 1) {
                imgGas.setImageResource(icon[2]);
            }

        }
    }
}
