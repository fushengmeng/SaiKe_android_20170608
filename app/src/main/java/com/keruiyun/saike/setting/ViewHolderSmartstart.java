package com.keruiyun.saike.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.bilibili.magicasakura.widgets.TintThemeTextView;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.DialogFragment_Music;
import com.keruiyun.saike.fragment.DialogFragment_SelectTime;
import com.keruiyun.saike.setting.data.Data_Air;
import com.keruiyun.saike.setting.data.Data_Smartstart;
import com.keruiyun.saike.setting.util.OnTouchAdd;
import com.keruiyun.saike.setting.util.OnTouchListenerAddSub;
import com.keruiyun.saike.setting.util.OnTouchSub;
import com.keruiyun.saike.timerserver.AlarmReceiver;
import com.keruiyun.saike.uiview.SwitchButton;
import com.keruiyun.saike.util.LogCus;
import com.util.DateTime;

import java.math.BigDecimal;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----智能启动
 */

public class ViewHolderSmartstart extends BaseViewHolder {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.switch_lighting_1)
    SwitchButton switchLighting1;
    @BindView(R.id.switch_lighting_2)
    SwitchButton switchLighting2;
    @BindView(R.id.switch_film_viewer)
    SwitchButton switchFilmViewer;
    @BindView(R.id.switch_intraoperative)
    SwitchButton switchIntraoperative;
    @BindView(R.id.switch_shadowless_lamp)
    SwitchButton switchShadowlessLamp;
    @BindView(R.id.switch_exhaust_fan)
    SwitchButton switchExhaustFan;
    @BindView(R.id.switch_writing_counter)
    SwitchButton switchWritingCounter;
    @BindView(R.id.switch_air_start_stop)
    SwitchButton switchAirStartStop;
    @BindView(R.id.switch_vacuum_run)
    SwitchButton switchVacuumRun;
    @BindView(R.id.b_temp_sub)
    ImageView bTempSub;
    @BindView(R.id.txt_value_temp)
    TintThemeTextView txtValueTemp;
    @BindView(R.id.b_temp_add)
    ImageView bTempAdd;
    @BindView(R.id.b_timer_on_sub)
    ImageView bTimerOnSub;
    @BindView(R.id.txt_value_timer_on)
    TintThemeTextView txtValueTimerOn;
    @BindView(R.id.b_timer_on_add)
    ImageView bTimerOnAdd;
    @BindView(R.id.b_rh_sub)
    ImageView bRhSub;
    @BindView(R.id.txt_value_rh)
    TintThemeTextView txtValueRh;
    @BindView(R.id.b_rh_add)
    ImageView bRhAdd;
    @BindView(R.id.b_timer_off_sub)
    ImageView bTimerOffSub;
    @BindView(R.id.txt_value_timer_off)
    TintThemeTextView txtValueTimerOff;
    @BindView(R.id.b_timer_off_add)
    ImageView bTimerOffAdd;
    @BindView(R.id.b_pa_sub)
    ImageView bPaSub;
    @BindView(R.id.txt_value_pa)
    TintThemeTextView txtValuePa;
    @BindView(R.id.b_pa_add)
    ImageView bPaAdd;

    Data_Smartstart data_smartstart;
    Data_Air data_air;
    SwitchButton.OnCheckedChangeListener onCheckedChangeListener;

    public ViewHolderSmartstart(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }


    @Override
    public int loadContentView() {
        return R.layout.layout_setting_smartstart;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        data_smartstart=new Data_Smartstart();
        data_air=new Data_Air();

        switchLighting1.setChecked(data_smartstart.isSwitchLighting1());

        switchLighting2.setChecked(data_smartstart.isSwitchLighting2());

        switchFilmViewer.setChecked(data_smartstart.isSwitchFilmViewer());

        switchIntraoperative.setChecked(data_smartstart.isSwitchIntraoperative());

        switchShadowlessLamp.setChecked(data_smartstart.isSwitchShadowlessLamp());

        switchExhaustFan.setChecked(data_smartstart.isSwitchExhaustFan());

        switchWritingCounter.setChecked(data_smartstart.isSwitchWritingCounter());

        switchAirStartStop.setChecked(data_smartstart.isSwitchAirStartStop());

        switchVacuumRun.setChecked(data_smartstart.isSwitchVacuumRun());

        txtValueTemp.setText(data_smartstart.getTxtValueTemp()+"");

        txtValueTimerOn.setText(data_smartstart.getTxtValueTimerOn()+"");

        txtValueRh.setText(data_smartstart.getTxtValueRh()+"");

        txtValueTimerOff.setText(data_smartstart.getTxtValueTimerOff()+"");

        txtValuePa.setText(data_smartstart.getTxtValuePa()+"");

        setListener();

        bTempSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                if (data_smartstart.getTxtValueTemp()>data_air.getTxtValueTempMin())
                    data_smartstart.setTxtValueTemp(data_smartstart.getTxtValueTemp()-1);
                txtValueTemp.setText(data_smartstart.getTxtValueTemp()+"");
            }
        }));
        bTempAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                if (data_smartstart.getTxtValueTemp()<data_air.getTxtValueTempMax())
                    data_smartstart.setTxtValueTemp(data_smartstart.getTxtValueTemp()+1);
                txtValueTemp.setText(data_smartstart.getTxtValueTemp()+"");
            }
        }));

        bRhSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                if (data_smartstart.getTxtValueRh()>data_air.getTxtValueRhMin())
                    data_smartstart.setTxtValueRh(data_smartstart.getTxtValueRh()-1);
                txtValueRh.setText(data_smartstart.getTxtValueRh()+"");
            }
        }));
        bRhAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                if (data_smartstart.getTxtValueRh()<data_air.getTxtValueRhMax())
                    data_smartstart.setTxtValueRh(data_smartstart.getTxtValueRh()+1);
                txtValueRh.setText(data_smartstart.getTxtValueRh()+"");
            }
        }));

        bPaSub.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchSub() {
            @Override
            public void onSub() {
                if (data_smartstart.getTxtValuePa()>data_air.getTxtValuePaMin())
                    data_smartstart.setTxtValuePa(data_smartstart.getTxtValuePa()-1);
                txtValuePa.setText(data_smartstart.getTxtValuePa()+"");
            }
        }));
        bPaAdd.setOnTouchListener(new OnTouchListenerAddSub(new OnTouchAdd() {
            @Override
            public void onAdd() {
                if (data_smartstart.getTxtValuePa()<data_air.getTxtValuePaMax())
                    data_smartstart.setTxtValuePa(data_smartstart.getTxtValuePa()+1);
                txtValuePa.setText(data_smartstart.getTxtValuePa()+"");
            }
        }));

    }

    private void setListener(){
        onCheckedChangeListener=new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                switch (view.getId()){
                    case R.id.switch_lighting_1:
                        data_smartstart.setSwitchLighting1(isChecked);
                        break;
                    case R.id.switch_lighting_2:
                        data_smartstart.setSwitchLighting2(isChecked);
                        break;
                    case R.id.switch_film_viewer:
                        data_smartstart.setSwitchFilmViewer(isChecked);
                        break;
                    case R.id.switch_intraoperative:
                        data_smartstart.setSwitchIntraoperative(isChecked);
                        break;
                    case R.id.switch_shadowless_lamp:
                        data_smartstart.setSwitchShadowlessLamp(isChecked);
                        break;
                    case R.id.switch_exhaust_fan:
                        data_smartstart.setSwitchExhaustFan(isChecked);
                        break;
                    case R.id.switch_writing_counter:
                        data_smartstart.setSwitchWritingCounter(isChecked);
                        break;
                    case R.id.switch_air_start_stop:
                        data_smartstart.setSwitchAirStartStop(isChecked);
                        break;
                    case R.id.switch_vacuum_run:
                        data_smartstart.setSwitchVacuumRun(isChecked);
                        break;
                }
            }
        };
        switchLighting1.setOnCheckedChangeListener(onCheckedChangeListener);

        switchLighting2.setOnCheckedChangeListener(onCheckedChangeListener);

        switchFilmViewer.setOnCheckedChangeListener(onCheckedChangeListener);

        switchIntraoperative.setOnCheckedChangeListener(onCheckedChangeListener);

        switchShadowlessLamp.setOnCheckedChangeListener(onCheckedChangeListener);

        switchExhaustFan.setOnCheckedChangeListener(onCheckedChangeListener);

        switchWritingCounter.setOnCheckedChangeListener(onCheckedChangeListener);

        switchAirStartStop.setOnCheckedChangeListener(onCheckedChangeListener);

        switchVacuumRun.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @OnClick({
            R.id.b_temp_sub, R.id.b_temp_add,
            R.id.b_rh_sub, R.id.b_rh_add,
            R.id.b_pa_sub, R.id.b_pa_add,
            R.id.b_timer_on_sub, R.id.b_timer_on_add,
            R.id.txt_value_timer_on, R.id.txt_value_timer_on_1,

            R.id.b_timer_off_sub, R.id.b_timer_off_add,
            R.id.txt_value_timer_off,R.id.txt_value_timer_off_1,           })
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.b_temp_sub:
//                if (data_smartstart.getTxtValueTemp()>data_air.getTxtValueTempMin())
//                    data_smartstart.setTxtValueTemp(data_smartstart.getTxtValueTemp()-1);
//                txtValueTemp.setText(data_smartstart.getTxtValueTemp()+"");
//                break;
//            case R.id.b_temp_add:
//                if (data_smartstart.getTxtValueTemp()<data_air.getTxtValueTempMax())
//                    data_smartstart.setTxtValueTemp(data_smartstart.getTxtValueTemp()+1);
//                txtValueTemp.setText(data_smartstart.getTxtValueTemp()+"");
//                break;
//            case R.id.b_rh_sub:
//                if (data_smartstart.getTxtValueRh()>data_air.getTxtValueRhMin())
//                    data_smartstart.setTxtValueRh(data_smartstart.getTxtValueRh()-1);
//                txtValueRh.setText(data_smartstart.getTxtValueRh()+"");
//                break;
//            case R.id.b_rh_add:
//                if (data_smartstart.getTxtValueRh()<data_air.getTxtValueRhMax())
//                    data_smartstart.setTxtValueRh(data_smartstart.getTxtValueRh()+1);
//                txtValueRh.setText(data_smartstart.getTxtValueRh()+"");
//                break;
//            case R.id.b_pa_sub:
//                if (data_smartstart.getTxtValuePa()>data_air.getTxtValuePaMin())
//                    data_smartstart.setTxtValuePa(data_smartstart.getTxtValuePa()-1);
//                txtValuePa.setText(data_smartstart.getTxtValuePa()+"");
//                break;
//            case R.id.b_pa_add:
//                if (data_smartstart.getTxtValuePa()<data_air.getTxtValuePaMax())
//                    data_smartstart.setTxtValuePa(data_smartstart.getTxtValuePa()+1);
//                txtValuePa.setText(data_smartstart.getTxtValuePa()+"");
//                break;

            case R.id.b_timer_on_sub:
//                if (data_smartstart.getTxtValueTimerOn()>0)
//                    data_smartstart.setTxtValueTimerOn(data_smartstart.getTxtValueTimerOn()-60000);
//                txtValueTimerOn.setText(DialogFragment_Music.formatTime( data_smartstart.getTxtValueTimerOn() )+"");

                break;
            case R.id.b_timer_on_add:
//                data_smartstart.setTxtValueTimerOn(data_smartstart.getTxtValueTimerOn()+60000);
//                txtValueTimerOn.setText(DialogFragment_Music.formatTime( data_smartstart.getTxtValueTimerOn() )+"");

                break;

            case R.id.b_timer_off_sub:
//                if (data_smartstart.getTxtValueTimerOff()>0)
//                    data_smartstart.setTxtValueTimerOff(data_smartstart.getTxtValueTimerOff()-60000);
//                txtValueTimerOff.setText( DialogFragment_Music.formatTime( data_smartstart.getTxtValueTimerOff() )+"");
                break;
            case R.id.b_timer_off_add:
//                data_smartstart.setTxtValueTimerOff(data_smartstart.getTxtValueTimerOff()+60000);
//
//                txtValueTimerOff.setText( DialogFragment_Music.formatTime( data_smartstart.getTxtValueTimerOff() )+"");
                break;

            case R.id.txt_value_timer_on_1:
            case R.id.txt_value_timer_on:
                new DialogFragment_SelectTime().setOnSelectTimeListener(new DialogFragment_SelectTime.OnSelectTimeListener() {
                    @Override
                    public void onSelectTime(String hour, String minute) {

                        data_smartstart.setTxtValueTimerOn( hour+":"+minute);
                        txtValueTimerOn.setText( hour+":"+minute);
                        setAlarm(context,true,hour,minute,false);
                    }
                }).show(((BaseActivity)context).getSupportFragmentManager(),DialogFragment_SelectTime.class.getName());

                break;
            case R.id.txt_value_timer_off_1:
            case R.id.txt_value_timer_off:
                new DialogFragment_SelectTime().setOnSelectTimeListener(new DialogFragment_SelectTime.OnSelectTimeListener() {
                    @Override
                    public void onSelectTime(String hour, String minute) {

                        data_smartstart.setTxtValueTimerOff(hour+":"+minute);

                        txtValueTimerOff.setText( hour+":"+minute);
                        setAlarm(context,false,hour,minute,false);
                    }
                }).show(((BaseActivity)context).getSupportFragmentManager(),DialogFragment_SelectTime.class.getName());
                break;

        }
    }

    public static void setAlarm(Context context,boolean isStart,String hourStr,String minuteStr,boolean isNext ){
        Calendar c= Calendar.getInstance();
        int hour,minute;
        try {
            hour=Integer.parseInt(hourStr);
            minute=Integer.parseInt(minuteStr);
            c.set(Calendar.HOUR_OF_DAY, hour);
            c.set(Calendar.MINUTE, minute);
            Intent intent=new Intent();
            intent.setAction("com.keruiyun.saike.timerserver.AlarmReceiver");
            intent.putExtra("isStart",isStart);
            intent.putExtra("hour",hour);
            intent.putExtra("minute",minute);

            PendingIntent pi= PendingIntent.getBroadcast(context,isStart?1:2, intent,PendingIntent.FLAG_ONE_SHOT);
            //设置一个PendingIntent对象，发送广播
            AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            //获取AlarmManager对象
            long time=c.getTimeInMillis();
            BigDecimal t=new BigDecimal(time);
            BigDecimal result=new BigDecimal(0);

            if (isNext)
                result=t.add(new BigDecimal(24*60*60*1000l));
            else
                result=new BigDecimal(time);

            LogCus.msg("---定时器设置："+":"+isStart+":---"+hour+":"+minute+"----"+result.longValue());
//            am.set(AlarmManager.RTC_WAKEUP,result.longValue() , pi);
            if(Build.VERSION.SDK_INT < 19){
                am.set(AlarmManager.RTC_WAKEUP, result.longValue() , pi);
            }else{
                am.setExact(AlarmManager.RTC_WAKEUP, result.longValue() , pi);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            return;
        }


    }


}
