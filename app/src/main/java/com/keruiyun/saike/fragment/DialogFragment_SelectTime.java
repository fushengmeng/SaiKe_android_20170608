package com.keruiyun.saike.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/11.
 */

public class DialogFragment_SelectTime extends BaseDialogFragment {
    @BindView(R.id.wheel_hour)
    WheelPicker wheelHour;
    @BindView(R.id.wheel_minute)
    WheelPicker wheelMinute;

    TextView txtSure;
    String hour="00",minute="00";

    @Override
    public int loadTintImage() {
        return R.drawable.sk_djs1_03;
    }

    @Override
    public boolean isTintList() {
        return false;
    }


    @Override
    public int loadContentView() {
        return R.layout.fragment_select_time;
    }

    @Override
    public int loadContentBottomView() {
        return R.layout.layout_sure;
    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        txtSure=findById(bottomView,R.id.txt_sure);
        txtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectTimeListener!=null)
                    onSelectTimeListener.onSelectTime(hour,minute);
                dismiss();
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        wheelHour.setOnItemSelectListener(onItemSelectListener);
        wheelMinute.setOnItemSelectListener(onItemSelectListener);
    }
    WheelPicker.OnItemSelectListener onItemSelectListener = new WheelPicker.OnItemSelectListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {

                case R.id.wheel_hour:
                    hour = (String) data;
                    break;
                case R.id.wheel_minute:
                    minute = (String) data;
                    break;

            }
        }

        @Override
        public void onItemClick(WheelPicker picker, Object data, int position) {

        }
    };



    private OnSelectTimeListener onSelectTimeListener;

    public DialogFragment_SelectTime setOnSelectTimeListener(OnSelectTimeListener onSelectTimeListener) {
        this.onSelectTimeListener = onSelectTimeListener;
        return this;
    }

    public interface OnSelectTimeListener{
        public void onSelectTime(String hour,String minute);
    }

}
