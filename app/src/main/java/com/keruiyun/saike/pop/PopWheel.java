package com.keruiyun.saike.pop;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.keruiyun.saike.R;
import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.setting.data.Data_Air;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.AssetsFiles;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/27.
 */

public class PopWheel extends BasePopupWindow {
    LinearLayout layout;
    ImageView icUp,icDown;
    WheelPicker wheelpicker;
    int type;
    Data_Air data_air;
    int curItem;
    String curValue;
    public PopWheel(Context context,int type,String curValue) {
        super(context);
        this.type=type;
        this.curValue=curValue;
        data_air=new Data_Air();
        refresData();
    }

    @Override
    public int loadContentView() {
        return R.layout.pop_wheel;
    }

    @Override
    public void initView() {
        super.initView();
        layout=findById(R.id.layout);
        BitmapDrawable drawable = AssetsFiles.getImageFromAssetsDrawable(getContext(), "sk_index_tc_03.png");
        if (drawable!=null)
            layout.setBackground(drawable);
        wheelpicker=findById(R.id.wheelpicker);

        icUp=findById(R.id.ic_wheel_sub);
        icDown=findById(R.id.ic_wheel_add);
        curItem=wheelpicker.getCurrentItem();
        icUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curItem--;
                if (curItem>=0)
                    wheelpicker.setCurrentItem(curItem);
                else
                    curItem=0;
            }
        });
        icDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curItem++;
                if (curItem<wheelpicker.getData().size())
                    wheelpicker.setCurrentItem(curItem);
                else
                    curItem=wheelpicker.getData().size()-1;
            }
        });
        wheelpicker.setOnItemSelectListener(new WheelPicker.OnItemSelectListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                curItem=position;
                if (mOnItemSelectListener!=null){
                    mOnItemSelectListener.onItemSelected(picker, data, position);
                }
            }

            @Override
            public void onItemClick(WheelPicker picker, Object data, int position) {
                if (mOnItemSelectListener!=null){
                    mOnItemSelectListener.onItemClick(picker, data, position);
                }
                dismiss();
            }
        });
    }

    private void refresData(){
        int min,max,curPosition;
        List<String> data;
        switch (type){
            case 0://温度
                min=data_air.getTxtValueTempMin();
                max=data_air.getTxtValueTempMax();
                data = getDatas(min, max, 1);
                curPosition=qureyPosition(data,curValue);
                wheelpicker.setData(data);
                wheelpicker.setCurrentItem(curPosition);
                break;
            case 1://湿度
                min=data_air.getTxtValueRhMin();
                max=data_air.getTxtValueRhMax();
                data = getDatas(min, max, 1);
                curPosition=qureyPosition(data,curValue);
                wheelpicker.setData(data);
                wheelpicker.setCurrentItem(curPosition);
                break;
            case 2://压差
                min=data_air.getTxtValuePaMin();
                max=data_air.getTxtValuePaMax();
                data = getDatas(min, max, 1);
                curPosition=qureyPosition(data,curValue);
                wheelpicker.setData(data);
                wheelpicker.setCurrentItem(curPosition);
                break;
        }
    }

    private int qureyPosition(List<String> data,String value ){

        for (int i=0;i<data.size();i++){
            if (value.equals(data.get(i)))
                return i;
        }
        return 0;
    }

    private List<String> getDatas(int min,int max,int step){
        List<String> dataList=new ArrayList<>();
        String item;
        for (int i=min;i<=max;i+=step){
            item=i+"";
            if (i<10&&i>=0){
                item="0"+i;
            }
            dataList.add(item);
        }
        return dataList;
    }

    private WheelPicker.OnItemSelectListener mOnItemSelectListener;
    public void setOnItemSelectListener(WheelPicker.OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }
}
