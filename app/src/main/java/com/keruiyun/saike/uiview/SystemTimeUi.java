package com.keruiyun.saike.uiview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.MainActivity;
import com.keruiyun.saike.fragment.DialogFragment_Countdown;
import com.keruiyun.saike.fragment.DialogFragment_SettingSystemTime;
import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.SystemDateTime;
import com.util.ToastUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2017/12/26.
 */

public class SystemTimeUi extends BaseProgressBar {

    private SimpleDateFormat sdf, sdf1;


    public SystemTimeUi(Context context) {
        super(context);
    }

    public SystemTimeUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SystemTimeUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void initData() {
        super.initData();
        sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        start();
    }

    @Override
    protected int setTimeLine(int baseLine) {
        return centerY;
    }

    @Override
    protected void drawTextTime(Canvas canvas) {
        super.drawTextTime(canvas);

        textPaint.setTextSize(25);
        Paint.FontMetricsInt fontMetricsDate = textPaint.getFontMetricsInt();
        int baselineDate = centerY-fontMetricsDate.top+16;
        String testString=setCurSystime();
        canvas.drawText(testString, centerX, baselineDate, textPaint);
    }

    @Override
    public String setCurSystime() {
        String curTime;
        short  enabled=0;
        if (MainApplication.getInstance()!=null)
            enabled=MainApplication.getInstance().enabled;
        if (1 == enabled )
        {
            curTime=String.format("%04d-%02d-%02d",
                    MainApplication.getInstance().year,
                    MainApplication.getInstance().month,
                    MainApplication.getInstance().day);

        }
        else
        {
            curTime=sdf1.format(new Date());

        }
        try
        {
            getContext().sendBroadcast(new Intent("com.keruiyun.saike.clock")
                    .putExtra("command", 3).putExtra("value", curTime));
        }
        catch (Exception ex)
        {ex.printStackTrace();
        }
        return curTime;
    }

    @Override
    public String setCurTimerTime(int progress) {
        String curTime;
        int sec;
        int enabled=0;
        if (MainApplication.getInstance()!=null)
            enabled=MainApplication.getInstance().enabled;
        if (1 == enabled)
        {
            curTime="%02d:%02d:%02d";
            sec=MainApplication.getInstance().second;
            this.progress=sec;
            curTime=String.format(curTime
                    , MainApplication.getInstance().hour
                    ,MainApplication.getInstance().minute
                    ,sec);

        }
        else
        {
            curTime="%1$s:%2$s:%3$s";
            String s = sdf.format(new Date());
            curTime=String.format(curTime
                    , s.substring(0, 2)
                    ,s.substring(3, 5)
                    ,s.substring(6, 8));
            try {
                sec=Integer.parseInt(s.substring(6, 8));

            }catch (NumberFormatException e){
                e.printStackTrace();
                sec=0;
            }
            this.progress=sec;
        }

        return curTime;
    }



    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }

    public void systemTimerDialog(){
       /* Intent dialog = new Intent(getContext(), TimeDialogActivity.class);
        dialog.putExtra("OpDate", getTimeStr(0));
        dialog.putExtra("AnesDate", getTimeStr(anesTimer));
        dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(dialog);*/
        DialogFragment_SettingSystemTime settingSystemTime=new DialogFragment_SettingSystemTime();
        settingSystemTime.set_listener(new DialogFragment_SettingSystemTime.OnSystemSettingListener() {
            @Override
            public void onSystemSettingListener(int year, int month, int day, int hour, int minute, int second) {
                String msg="设置成功";
                try {
                    SystemDateTime.setDateTime(year, month, day, hour, minute, second);
                } catch (IOException e) {
                    e.printStackTrace();
                    msg="设置失败";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    msg="设置失败";
                }
                ToastUtil.showToast(msg);
            }
        });
        settingSystemTime.show(((BaseActivity)getContext()).getSupportFragmentManager(),this.getClass().getName());
    }

}
