package com.keruiyun.saike.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 系统时间设置
 */

public class DialogFragment_SettingSystemTime extends BaseDialogFragment {


    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.wheel_hour)
    WheelPicker wheelHour;
    @BindView(R.id.wheel_minute)
    WheelPicker wheelMinute;
    @BindView(R.id.wheel_second)
    WheelPicker wheelSecond;
    @BindView(R.id.wheel_year)
    WheelPicker wheelYear;
    @BindView(R.id.wheel_month)
    WheelPicker wheelMonth;
    @BindView(R.id.wheel_day)
    WheelPicker wheelDay;

    String year="0000",month="00",day="00",hour = "00", minute = "00", second = "00";

    private OnSystemSettingListener _listener = null;

    public void set_listener(OnSystemSettingListener _listener) {
        this._listener = _listener;
    }

    @Override
    public int loadContentView() {
        return R.layout.fragment_systemtime;
    }

    @Override
    public int loadTintImage() {
        return R.drawable.sk_djs1_03;
    }

    @Override
    public boolean isTintList() {
        return false;
    }

    @Override
    public int loadContentBottomView() {
        return  R.layout.layout_sure;
    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        TextView txtSure = findById(bottomView, R.id.txt_sure);
        txtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogCus.msg("系统时间设置："+year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
                _listener.onSystemSettingListener(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day),
                        Integer.parseInt(hour), Integer.parseInt(minute), Integer.parseInt(second));
                dismiss();
            }
        });
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        initData();
        wheelYear.setOnItemSelectListener(onItemSelectListener);
        wheelMonth.setOnItemSelectListener(onItemSelectListener);
        wheelDay.setOnItemSelectListener(onItemSelectListener);
        wheelHour.setOnItemSelectListener(onItemSelectListener);
        wheelMinute.setOnItemSelectListener(onItemSelectListener);
        wheelSecond.setOnItemSelectListener(onItemSelectListener);
    }

    WheelPicker.OnItemSelectListener onItemSelectListener = new WheelPicker.OnItemSelectListener() {
        @Override
        public void onItemSelected(WheelPicker picker, Object data, int position) {
            switch (picker.getId()) {
                case R.id.wheel_year:
                    year = (String) data;
                    refreshDays();
                    break;
                case R.id.wheel_month:
                    month = (String) data;
                    refreshDays();
                    break;
                case R.id.wheel_day:
                    day = (String) data;
                    break;
                case R.id.wheel_hour:
                    hour = (String) data;
                    break;
                case R.id.wheel_minute:
                    minute = (String) data;
                    break;
                case R.id.wheel_second:
                    second = (String) data;
                    break;
            }
        }

        @Override
        public void onItemClick(WheelPicker picker, Object data, int position) {

        }
    };

    private void refreshDays(){
        wheelDay.setData(daysOfMonth(Integer.parseInt(year),Integer.parseInt(month)));
        wheelDay.setCurrentItem(0);
        day=wheelDay.getData().get(0)+"";
    }

    private void initData(){
        Calendar cal = Calendar.getInstance();
        //当前年
        int year = cal.get(Calendar.YEAR);
        //上月
        int month = (cal.get(Calendar.MONTH));
        //当前月的第几天：即当前日
        int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
        //当前时：HOUR_OF_DAY-24小时制；HOUR-12小时制
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        //当前分
        int minute = cal.get(Calendar.MINUTE);
        //当前秒
        int second = cal.get(Calendar.SECOND);

        List<String> years=new ArrayList<>();
        for (int i=year-50;i<year+50;i++){
            years.add(i+"");
        }
        wheelYear.setData(years);
        wheelYear.setCurrentItem(50);
        this.year=year+"";
        wheelMonth.setCurrentItem(month);
        this.month= (String) wheelMonth.getData().get(month);

        wheelDay.setData(daysOfMonth(year,month+1));
        wheelDay.setCurrentItem(day_of_month-1);
        day=day_of_month+"";

        wheelHour.setCurrentItem(hour);
        this.hour= (String) wheelHour.getData().get(hour);
        wheelMinute.setCurrentItem(minute);
        this.minute= (String) wheelMinute.getData().get(minute);
        wheelSecond.setCurrentItem(second);
        this.second= (String) wheelSecond.getData().get(second);
    }

    private boolean isLeapyear(int year){
        return (year%4==0)&&(year%100!=0)||(year%400==0);
    }

    private List<String> daysOfMonth(int year, int month){
        List<String> days=new ArrayList<>();
        int max=30;
        if (month==2){
            if (isLeapyear(year)){
                max=29;
            }else {
                max=28;
            }
        }else {
            if (month==1||month==3||month==5||month==7||month==8||month==10||month==12)
                max=31;
        }
        for (int i=1;i<=max;i++){
            if (i<10)
                days.add("0"+i);
            else
                days.add(i+"");
        }
        return days;
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                dismiss();
                break;

        }
    }

    public interface OnSystemSettingListener {
        public void onSystemSettingListener(int year,int month,int day,int hour, int minute, int second);
    }
}
