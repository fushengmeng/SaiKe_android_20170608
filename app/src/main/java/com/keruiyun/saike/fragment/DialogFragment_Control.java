package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.main.FloatingView;
import com.keruiyun.saike.main.Fragment_Air;
import com.keruiyun.saike.main.Fragment_Main;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.data.Data_Smartstart;
import com.keruiyun.saike.util.DrawableUtil;
import com.keruiyun.saike.util.LogCus;
import com.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 综合控制
 */

public class DialogFragment_Control extends BaseDialogFragment {

    ViewHolder[] viewHolder=new ViewHolder[8];

    private int isLight1 = 0, isLight2 = 0, isWind = 0, isLook = 0,
            isShaow = 0, isDesk = 0,isSpare = 0,isBackUp_1 = 0,isBackUp_2 = 0,isOpSwitchOpen=0, folloAddr = 0;


    int val;

    @Override
    public int loadContentView() {
        return R.layout.fragment_control;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        viewHolder[0]=new ViewHolder(findById(R.id.layout_control_1));
        viewHolder[1]=new ViewHolder(findById(R.id.layout_lighting_2));
        viewHolder[2]=new ViewHolder(findById(R.id.layout_exhaust_fan));
        viewHolder[3]=new ViewHolder(findById(R.id.layout_film_viewer));

        viewHolder[4]=new ViewHolder(findById(R.id.layout_shadowless_lamp));
        viewHolder[5]=new ViewHolder(findById(R.id.layout_escritorio));
        viewHolder[6]=new ViewHolder(findById(R.id.layout_spare_1));
        viewHolder[7]=new ViewHolder(findById(R.id.layout_spare_2));
        initUi();

    }
    int[] icon;
    private void initUi(){

        icon=new int[]{R.drawable.sk_zhkz1_03,R.drawable.sk_zhkz1_05,R.drawable.sk_zhkz1_07,R.drawable.sk_zhkz1_09,
                R.drawable.sk_zhkz1_15,R.drawable.sk_zhkz1_16,R.drawable.sk_zhkz1_18,R.drawable.sk_zhkz1_19};
        String[] name=getActivity().getResources().getStringArray(R.array.arr_fragment_control);
        for (int i=0;i<icon.length;i++){
            viewHolder[i].view.setBackgroundDrawable(new DrawableUtil(getActivity()).getStateListDrawable( viewHolder[i].imgControl));
            viewHolder[i].imgControl.setImageResource(icon[i]);
            viewHolder[i].view.setTag(i);
            viewHolder[i].txtControl.setTextSize(16);
            viewHolder[i].txtControl.setText(name[i]);
            viewHolder[i].view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer tag= (Integer) v.getTag();
                    boolean isSelect=v.isSelected();
                    v.setSelected(!isSelect);
                    if (!Fragment_Main.isConnectModBus){
                        v.setSelected(true);
                        val=FloatingView.var;
                        isLight1 = val & 0x01;
                        isLight2 = (val >> 1) & 0x01;
                        isWind = (val >> 2) & 0x01;
                        isLook = (val >> 3) & 0x01;
                        isShaow = (val >> 4) & 0x01;
                        isDesk = (val >> 5) & 0x01;
                        isSpare = (val >> 6) & 0x01;

                        switch (tag) {
                            case 0://照明1
                                isLight1=1;
                                break;
                            case 1://照明2
                                isLight2=1;
                                break;
                            case 2://排风机
                                isWind=1;
                                break;
                            case 3://观片灯
                                isLook=1;
                                break;
                            case 4://无影灯
                                isShaow=1;
                                break;
                            case 5://书写台
                                isDesk=1;
                                break;
                            case 6://空调启停
                                Fragment_Air.isOpSwitchOpen=1;
                                isOpSwitchOpen=1;
                                opSwitchChange();
                                break;
                            case 7://空调值班
                                Fragment_Air.folloAddr=1;
                                folloAddr=1;
                                opWaitingChange();
                                break;
                        }

                        val = val | (isLight1);
                        val = val | (isLight2 << 1);
                        val = val | (isWind << 2);
                        val = val | (isLook << 3);
                        val = val | (isShaow << 4);
                        val = val | (isDesk << 5);
                        val = val | (isSpare << 6);
                        FloatingView.var=val;
                        updateSerialData(SerialSaunaThread.ADDR_CONTROL_KEY, (short) val);
                        return;
                    }
                    switch (tag){
                        case 0://照明1
                            light1();
                            break;
                        case 1://照明2
                            light2();
                            break;
                        case 2://排风机
                            wind();
                            break;
                        case 3://观片灯
                            look();
                            break;
                        case 4://无影灯
                            shaow();
                            break;
                        case 5://书写台
                            desk();
                            break;
                        case 6://空调启停

//                            backUp1();
                            airSwitch();
                            break;
                        case 7://空调值班
//                            backUp2();
                            airWaiting();
                            break;
                    }

                }
            });
        }

        if (!Fragment_Main.isConnectModBus){
            updateSerialData(SerialSaunaThread.ADDR_CONTROL_KEY, (short) FloatingView.var);

            isOpSwitchOpen= Fragment_Air.isOpSwitchOpen;
            opSwitchChange();
            folloAddr = Fragment_Air.folloAddr;
            opWaitingChange();
        }
    }


    @Override
    public void updateSerialData(int addr,short data) {
        super.updateSerialData(addr,data);
        if (addr==SerialSaunaThread.ADDR_CONTROL_KEY){
            isLight1 = data & 0x01;
            isLight2 = (data >> 1) & 0x01;
            isWind = (data >> 2) & 0x01;
            isLook = (data >> 3) & 0x01;
            isShaow = (data >> 4) & 0x01;
            isDesk = (data >> 5) & 0x01;
            isSpare = (data >> 6) & 0x01;
            isBackUp_1 = (data >> 7) & 0x01;
            isBackUp_2 = (data >> 8) & 0x01;
            light1Change();
            light2Change();
            windChange();
            lookChange();
            shaowChange();
            deskChange();
//            spareChange();
//            backUp1Change();
//            backUp2Change();
        }

        if (addr==SerialSaunaThread.ADDR_POWER_KEY){
            isOpSwitchOpen=data;
            opSwitchChange();
        }

        if (addr==SerialSaunaThread.ADDR_DUTY_KEY){
            folloAddr=data;
            opWaitingChange();
        }
//        LogCus.msg("data:"+data+
//                ":isLight1:"+isLight1+
//                ":isLight2:"+isLight2+
//                ":isWind:"+isWind+
//                ":isLook:"+isLook+
//                ":isShaow:"+isShaow+
//                ":isDesk:"+isDesk+
//                ":isOpSwitchOpen:"+isOpSwitchOpen+
//                ":folloAddr:"+folloAddr);
    }
    // 空调开关
    private void airSwitch() {
        if (isOpSwitchOpen == 0) {
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 1);
            isOpSwitchOpen = 1;
        } else if (isOpSwitchOpen == 1) {
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 0);
            isOpSwitchOpen = 0;
        }
        opSwitchChange();
    }

    // 空调值班
    private void airWaiting() {
        if (folloAddr == 0) {
            folloAddr=1;
            SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,1);
        } else if (folloAddr == 1) {
            folloAddr=0;
            SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_DUTY_KEY,0);
        }

        opWaitingChange();
    }

    private void opSwitchChange() {
        viewHolder[6].setSelected(isOpSwitchOpen==1);
    }

    private void opWaitingChange() {
        if (folloAddr == 0) {
            // ivLight1.setImageResource(R.drawable.c4_normal);
            viewHolder[7].setSelected(false);
        } else if (folloAddr == 1) {
            // ivLight1.setImageResource(R.drawable.c4);
            viewHolder[7].setSelected(true);
        }
    }

    private void light1() {
        int val = 0;

        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isLight1 == 0 ? 1 : 0;
        val = val | temp;

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isLight1 = temp;
        light1Change();
    }

    private void light2() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isLight2 == 0 ? 1 : 0;
        val = val | (temp << 1);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isLight2 = temp;
        light2Change();
    }

    private void wind() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isWind == 0 ? 1 : 0;
        val = val | (temp << 2);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isWind = temp;
        windChange();
    }

    private void look() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isLook == 0 ? 1 : 0;
        val = val | (temp << 3);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isLook = temp;
        lookChange();
    }

    private void shaow() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isShaow == 0 ? 1 : 0;
        val = val | (temp << 4);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isShaow = temp;
        shaowChange();
    }

    private void desk() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);
        val = val | (isBackUp_2 << 8);

        int temp = isDesk == 0 ? 1 : 0;
        val = val | (temp << 5);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isDesk = temp;
        deskChange();
    }


    private void backUp1() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);

        val = val | (isBackUp_2 << 8);

        int temp = isBackUp_1 == 0 ? 1 : 0;
        val = val | (temp << 7);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isBackUp_1 = temp;
        backUp1Change();
    }

    private void backUp2() {
        int val = 0;

        val = val | (isLight1);
        val = val | (isLight2 << 1);
        val = val | (isWind << 2);
        val = val | (isLook << 3);
        val = val | (isShaow << 4);
        val = val | (isDesk << 5);
        val = val | (isSpare << 6);
        val = val | (isBackUp_1 << 7);

        int temp = isBackUp_1 == 0 ? 1 : 0;
        val = val | (temp << 8);

        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,
                val);

        isBackUp_2 = temp;
        backUp2Change();
    }


    // 照明1
    private void light1Change() {
        if (isLight1 == 0) {
            // ivLight1.setImageResource(R.drawable.c4_normal);
            viewHolder[0].setSelected(false);
        } else if (isLight1 == 1) {
            // ivLight1.setImageResource(R.drawable.c4);
            viewHolder[0].setSelected(true);
        }
    }

    // 照明2
    private void light2Change() {
        if (isLight2 == 0) {
            // ivLight2.setImageResource(R.drawable.c4_normal);
            viewHolder[1].setSelected(false);
        } else if (isLight2 == 1) {
            // ivLight2.setImageResource(R.drawable.c4);
            viewHolder[1].setSelected(true);
        }
    }

    // 排风机
    private void windChange() {
        if (isWind == 0) {
            // ivWind.setImageResource(R.drawable.c5_normal);
            viewHolder[2].setSelected(false);
        } else if (isWind == 1) {
            // ivWind.setImageResource(R.drawable.c5);
            viewHolder[2].setSelected(true);
        }
    }

    // 观片灯
    private void lookChange() {
        if (isLook == 0) {
            // ivLook.setImageResource(R.drawable.c6_normal);
            viewHolder[3].setSelected(false);
        } else if (isLook == 1) {
            // ivLook.setImageResource(R.drawable.c6);
            viewHolder[3].setSelected(true);
        }
    }

    // 无影灯灯
    private void shaowChange() {
        if (isShaow == 0) {
            // ivShaow.setImageResource(R.drawable.c6_normal);
            viewHolder[4].setSelected(false);
        } else if (isShaow == 1) {
            // ivShaow.setImageResource(R.drawable.c6);
            viewHolder[4].setSelected(true);
        }
    }

    // 书写台
    private void deskChange() {
        if (isDesk == 0) {
            // ivDesk.setImageResource(R.drawable.c6_normal);
            viewHolder[5].setSelected(false);
        } else if (isDesk == 1) {
            // ivDesk.setImageResource(R.drawable.c6);
            viewHolder[5].setSelected(true);
        }
    }



    //备用1
    private void backUp1Change() {
        if (isBackUp_1 == 0) {
            // ivSpare.setImageResource(R.drawable.c7_normal);
            viewHolder[6].setSelected(false);
        } else if (isBackUp_1 == 1) {
            // ivSpare.setImageResource(R.drawable.c7);
            viewHolder[6].setSelected(true);
        }
    }

    //备用2
    private void backUp2Change() {
        if (isBackUp_2 == 0) {
            // ivSpare.setImageResource(R.drawable.c7_normal);
            viewHolder[7].setSelected(false);
        } else if (isBackUp_2 == 1) {
            // ivSpare.setImageResource(R.drawable.c7);
            viewHolder[7].setSelected(true);
        }
    }

    class ViewHolder {
        View view;
        @BindView(R.id.img_control)
        ImageView imgControl;
        @BindView(R.id.txt_control)
        TextView txtControl;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            this.view=view;
        }


        public void setSelected(boolean selected) {
            view.setSelected(selected);
            imgControl.setSelected(selected);
            txtControl.setSelected(selected);
        }
    }


}
