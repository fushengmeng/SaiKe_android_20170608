package com.keruiyun.saike.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.DialogFragment_ValidSure;
import com.keruiyun.saike.pop.PopWheel;
import com.keruiyun.saike.pop.PopWheelDate;
import com.keruiyun.saike.setting.data.Data_validnutil;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.SharePreferenceUtil;
import com.keruiyun.saike.wheelpicker.WheelPicker;
import com.util.DateTime;
import com.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----有效期
 */

public class ViewHolderValiduntil extends BaseViewHolder {

    @BindView(R.id.box_valid_3)
    CheckBox boxValid3;
    @BindView(R.id.box_valid_6)
    CheckBox boxValid6;
    @BindView(R.id.box_valid_12)
    CheckBox boxValid12;
    @BindView(R.id.box_valid_18)
    CheckBox boxValid18;
    @BindView(R.id.box_valid_24)
    CheckBox boxValid24;
    @BindView(R.id.box_valid_0)
    CheckBox boxValid0;
    @BindView(R.id.box_valid_custom)
    CheckBox boxValidCustom;
    @BindView(R.id.txt_custom_year)
    TextView txtCustomYear;
    @BindView(R.id.txt_custom_month)
    TextView txtCustomMonth;
    @BindView(R.id.txt_custom_day)
    TextView txtCustomDay;
    @BindView(R.id.wheel_setting_year)
    WheelPicker wheelYear;
    @BindView(R.id.wheel_setting_month)
    WheelPicker wheelMonth;
    @BindView(R.id.wheel_setting_day)
    WheelPicker wheelDay;
    @BindView(R.id.txt_sure)
    TintTextView txtSure;

    CheckBox[] checkBoxes;
    int position=0;
    String year="0000",month="00",day="00";
    Data_validnutil data_validnutil;
    int adminLev=2;
    List<String> datasYear ,datasMonth,datasDay;

    private WheelPicker.OnItemSelectListener onItemSelectListener;

    public ViewHolderValiduntil(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public boolean isAuthValid() {
        return false;
    }

    public void setAdminLev(int adminLev) {
        this.adminLev = adminLev;
        position=data_validnutil.isBoxValidPosition(adminLev);
        setCheck(position);
        initCustomData();
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_valid;
    }

    @Override
    public void initView(final Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        year="0000";month="00";day="00";
        data_validnutil=new Data_validnutil();
        checkBoxes=new CheckBox[]{  boxValid3,boxValid6,boxValid12,
                                    boxValid18,boxValid24,boxValid0,
                                    boxValidCustom};

        setCheck(data_validnutil.isBoxValidPosition(adminLev));

        initCustomData();

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    switch (buttonView.getId()){
                        case R.id.box_valid_3:
                            position=0;
                            break;
                        case R.id.box_valid_6:
                            position=1;
                            break;
                        case R.id.box_valid_12:
                            position=2;
                            break;
                        case R.id.box_valid_18:
                            position=3;
                            break;
                        case R.id.box_valid_24:
                            position=4;
                            break;
                        case R.id.box_valid_0:
                            position=5;
                            break;
                        case R.id.box_valid_custom:
                            position=6;
                            break;

                    }
                    setCheck(position);
                    data_validnutil.setBoxValidPosition(adminLev,position);
                }


            }
        };
        onItemSelectListener=customPickerListener();


        for (int i=0;i<checkBoxes.length;i++){
            checkBoxes[i].setOnCheckedChangeListener(onCheckedChangeListener);
        }


        wheelYear.setOnItemSelectListener(onItemSelectListener);
        wheelMonth.setOnItemSelectListener(onItemSelectListener);
        wheelDay.setOnItemSelectListener(onItemSelectListener);

        txtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogFragment_ValidSure(new DialogFragment_ValidSure.OnValidSureListener() {
                    @Override
                    public void onValidSure(boolean isEnable) {
                        if (position==6){
                            String time=year+"-"+month+"-"+day+" 23:59:59";
                            long settingTime = DateTime.getLongByFormat(time);
                            if (settingTime<=System.currentTimeMillis()){
                                ToastUtil.showToast(context.getResources().getString(R.string.settingtime_valid));
                               return;
                            }
                        }
                        data_validnutil.setEnable(adminLev,isEnable);
                    }
                }).show(((BaseActivity)context).getSupportFragmentManager(),DialogFragment_ValidSure.class.getName());
            }
        });

        setListener();
    }

    private void setCheck(int position){
        for (int i=0;i<checkBoxes.length;i++){
            checkBoxes[i].setChecked(position==i);
        }
    }

    private void setListener(){
        View.OnClickListener onClickListener =  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.txt_custom_year:
//                        showDataSelect(0,v);
                        wheelYear.setVisibility(View.VISIBLE);
                        wheelMonth.setVisibility(View.GONE);
                        wheelDay.setVisibility(View.GONE);
                        break;
                    case R.id.txt_custom_month:
//                        showDataSelect(1,v);
                        wheelYear.setVisibility(View.GONE);
                        wheelMonth.setVisibility(View.VISIBLE);
                        wheelDay.setVisibility(View.GONE);
                        break;
                    case R.id.txt_custom_day:
//                        showDataSelect(2,v);
                        wheelYear.setVisibility(View.GONE);
                        wheelMonth.setVisibility(View.GONE);
                        wheelDay.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        txtCustomYear.setOnClickListener(onClickListener);
        txtCustomMonth.setOnClickListener(onClickListener);
        txtCustomDay.setOnClickListener(onClickListener);
    }

    private void showDataSelect(final int type,View v){
        List<String> datas=new ArrayList<>();
        switch (type){
            case 0:
                datas=datasYear;
                break;
            case 1:
                datas=datasMonth;
                break;
            case 2:
                refreshDays();
                datas=datasDay;
                break;


        }
        PopWheelDate popWheel = new PopWheelDate(context,type);
        popWheel.setDatas(datas);
        popWheel.setOnItemSelectListener(new WheelPicker.OnItemSelectListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {

            }

            @Override
            public void onItemClick(WheelPicker picker, Object data, int position) {
//                int value = Integer.parseInt(data.toString());
//                txtAirInfoSetting.setText(valueSetting+data+unit);
                switch (type){
                    case 0:
                        year = (String) data;
                        txtCustomYear.setText(year);
                        data_validnutil.setWheelYear(adminLev,year);
                        refreshDays();
                        break;
                    case 1:
                        month = (String) data;
                        txtCustomMonth.setText(month);
                        data_validnutil.setWheelMonth(adminLev,month);
                        refreshDays();
                        break;
                    case 2:
                        day = (String) data;
                        txtCustomDay.setText(day);
                        data_validnutil.setWheelDay(adminLev,day);
                        break;
                }
            }
        });
        popWheel.showAsRightCenter(v,345,95);
    }

    private WheelPicker.OnItemSelectListener customPickerListener(){
       return new WheelPicker.OnItemSelectListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                LogCus.msg("picker.getId:"+picker.getId()+":"+year+"-"+month+"-"+day);
                switch (picker.getId()) {
                    case R.id.wheel_setting_year:
                        year = (String) data;
                        txtCustomYear.setText(year);
                        data_validnutil.setWheelYear(adminLev,year);
                        refreshDays();
                        break;
                    case R.id.wheel_setting_month:
                        month = (String) data;
                        txtCustomMonth.setText(month);
                        data_validnutil.setWheelMonth(adminLev,month);
                        refreshDays();
                        break;
                    case R.id.wheel_setting_day:
                        day = (String) data;
                        txtCustomDay.setText(day);
                        data_validnutil.setWheelDay(adminLev,day);
                        break;

                }
            }

            @Override
            public void onItemClick(WheelPicker picker, Object data, int position) {

            }
        };
    }




    private void refreshDays(){
        datasDay=daysOfMonth(Integer.parseInt(year),Integer.parseInt(month));
        wheelDay.setData(datasDay);
        wheelDay.setCurrentItem(0);
        day=wheelDay.getData().get(0)+"";
        LogCus.msg(year+"-"+month+"-"+day);
    }

    private void initCustomData(){
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

        datasYear=new ArrayList<>();
        for (int i=year;i<year+100;i++){
            datasYear.add(i+"");
        }
        wheelYear.setData(datasYear);
        this.year=data_validnutil.getWheelYear(adminLev)+"";
        wheelYear.setCurrentItem(getPosition(data_validnutil.getWheelYear(adminLev),datasYear));
        txtCustomYear.setText(""+data_validnutil.getWheelYear(adminLev));


        datasMonth=new ArrayList<>();
        for (int i=1;i<=12;i++){
            datasMonth.add(i<10?("0"+i):(i+""));
        }
        this.month= data_validnutil.getWheelMonth(adminLev);
        txtCustomMonth.setText(this.month);
        wheelMonth.setCurrentItem(getPosition(this.month,wheelMonth.getData()));


        datasDay=daysOfMonth(year,month+1);
        wheelDay.setData(datasDay);
        day=data_validnutil.getWheelDay(adminLev);
        txtCustomDay.setText(day);
        wheelDay.setCurrentItem(getPosition(day,wheelDay.getData()));



    }

    private int getPosition(String value,List<String> data){

        for (int i=0;i<data.size();i++){
            if (value.equals(data.get(i))){
                return i;
            }
        }
        return 0;
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
}
