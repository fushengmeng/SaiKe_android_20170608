package com.keruiyun.saike.pop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.setting.data.Data_Air;
import com.keruiyun.saike.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */

public class PopWheelDate extends BasePopupWindow {

    ImageView icUp,icDown;
    WheelPicker wheelpicker;
    int type;
    List<String> datas;
    int curItem;
    public PopWheelDate(Context context, int type) {
        super(context);
        this.type=type;


    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        wheelpicker.setData(datas);
    }

    @Override
    public int loadContentView() {
        return R.layout.pop_wheel;
    }

    @Override
    public void initView() {
        super.initView();
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


    private WheelPicker.OnItemSelectListener mOnItemSelectListener;
    public void setOnItemSelectListener(WheelPicker.OnItemSelectListener listener) {
        mOnItemSelectListener = listener;
    }
}
