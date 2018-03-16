package com.keruiyun.saike.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.CountDownDialogActivityListener;
import com.keruiyun.saike.R;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 倒计时设置
 */

public class DialogFragment_Countdown extends BaseDialogFragment {


    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.wheel_hour)
    WheelPicker wheelHour;
    @BindView(R.id.wheel_minute)
    WheelPicker wheelMinute;
    @BindView(R.id.wheel_second)
    WheelPicker wheelSecond;

    TextView txtSure;
    String hour="00",minute="00",second="00";
    private   OnCountdownListener _listener = null;

    public void set_listener(OnCountdownListener _listener) {
        this._listener = _listener;
    }

    @Override
    public int loadTintImage() {
        return R.drawable.sk_djs1_03;
    }

    @Override
    public boolean isTintList() {
        return true;
    }

    @Override
    public int loadContentView() {
        return R.layout.fragment_countdown;
    }

    @Override
    public int loadContentBottomView() {
        return  R.layout.layout_sure;
    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        txtSure=findById(bottomView,R.id.txt_sure);
        txtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hour.equals("00")&&minute.equals("00")&&second.equals("00")){
                    ToastUtil.showToast("请选择时间");
                }
                int hourSelect=Integer.parseInt(hour);
                int minuteSelect=Integer.parseInt(minute);
                int secondSelect=Integer.parseInt(second);
                if (secondSelect>=60){
                    secondSelect=secondSelect-60;
                    minuteSelect+=1;
                }

                if (minuteSelect>=60){
                    minuteSelect=minuteSelect-60;
                    hourSelect+=1;
                }

                _listener.onCountdownListener(hourSelect, minuteSelect, secondSelect);
                dismiss();
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        List<String> hours=new ArrayList<>();
        for (int i=0;i<=99;i++){
            hours.add(i<10?("0"+i):(i+""));
        }
        wheelHour.setData(hours);
        wheelMinute.setData(hours);
        wheelSecond.setData(hours);

        wheelHour.setOnItemSelectListener(onItemSelectListener);
        wheelMinute.setOnItemSelectListener(onItemSelectListener);
        wheelSecond.setOnItemSelectListener(onItemSelectListener);
    }

    WheelPicker.OnItemSelectListener onItemSelectListener=new WheelPicker.OnItemSelectListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()){
                case R.id.wheel_hour:
                    hour= (String) data;
                    break;
                case R.id.wheel_minute:
                    minute= (String) data;
                    break;
                case R.id.wheel_second:
                    second= (String) data;
                    break;
            }
        }

        @Override
        public void onItemClick(WheelPicker picker, Object data, int position) {

        }
    };

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;

        }
    }

    public interface OnCountdownListener{
        public void onCountdownListener(int hour,int minute,int second);
    }
}
