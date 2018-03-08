package com.keruiyun.saike.setting.util;

import android.app.AlarmManager;
import android.content.Context;
import android.provider.Settings;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by cangxuanxiao on 18-1-18.
 */

public class TimeZoneSetting {
    private Context mContext;

    public TimeZoneSetting(Context mContext) {
        this.mContext = mContext;
    }

    /**判断系统的时区是否是自动获取的*/

    public boolean isTimeZoneAuto(){
        try {
            return  android.provider.Settings.Global.getInt(mContext.getContentResolver(),
                    android.provider.Settings.Global.AUTO_TIME_ZONE) > 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**设置系统的时区是否自动获取*/

    public void setAutoTimeZone(int checked){
        android.provider.Settings.Global.putInt(mContext.getContentResolver(),
                android.provider.Settings.Global.AUTO_TIME_ZONE, checked);
    }


    /**设置系统时区*/
    public void setTimeZone(String timeZone){
//        final Calendar now = Calendar.getInstance();
//        TimeZone tz = TimeZone.getTimeZone(timeZone);
//        now.setTimeZone(tz);

        AlarmManager timeZoneM= (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        timeZoneM.setTimeZone(timeZone);
    }

    /**获取系统当前的时区*/
    public String getDefaultTimeZone(){
        return TimeZone.getDefault().getDisplayName();
    }


}
