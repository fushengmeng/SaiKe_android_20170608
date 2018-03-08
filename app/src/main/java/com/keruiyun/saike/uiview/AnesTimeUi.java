package com.keruiyun.saike.uiview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.CountDownDialogActivity;
import com.keruiyun.saike.CountDownDialogActivityListener;
import com.keruiyun.saike.MainActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.TimeDialogActivity;
import com.keruiyun.saike.fragment.DialogFragment_Countdown;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;
import com.keruiyun.saike.util.Util;
import com.music.soundpool.SoundPlayer;

import java.util.Date;

/**
 * Created by Administrator on 2017/12/14.
 */

public class AnesTimeUi extends BaseProgressBar implements DialogFragment_Countdown.OnCountdownListener {
    /**
     * 倒计时是否结束
     */
    protected boolean isTikle = true;
    /**
     * 是否是计时器；
     * Start（）时isTikle=true&&anesTimerValue==0 是为计时器
     *
     */
    protected boolean isTimer = false;
    private int anesTimer, anesTimerValue = 0;;
    private Context context;
    private Handler mAnesTwinkleHandler;

    String msgString;
    SoundPlayer soundPlayer;
    public AnesTimeUi(Context context) {
        super(context);
    }

    public AnesTimeUi(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnesTimeUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**播放间隔*/
    int playerCount;
    @Override
    public void initData() {
        context=getContext();
        msgString=getResources().getString(R.string.anesmsg);
        soundPlayer=SoundPlayer.getInstance();

        mAnesTwinkleHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        isInvisibleHour=true;
                        mAnesTwinkleHandler.sendEmptyMessageDelayed(3, 500);
                        break;
                    case 3:
                        if (playerCount>=5) {
                            playerCount=0;
                        }
                        if (playerCount==0){
                            soundPlayer.playerAlarm();
                        }
                        playerCount++;
                        isInvisibleHour=false;
                        mAnesTwinkleHandler.sendEmptyMessageDelayed(1, 500);


                        break;
                    default:

                        break;
                }
                postInvalidate();
                return false;
            }
        });
        super.initData();

        int anesCount = PreferencesUtil.getInstance(context).getIntValue("AnesTimer", "count",
                0);
        long anesTime = PreferencesUtil.getInstance(context).getLongValue("AnesTimer", "time",
                0);
        anesTimerValue = PreferencesUtil.getInstance(context).getIntValue("AnesTimer", "countdown",
                0);

        isTimer=anesTimerValue==0;
        startRotate=0;
        currRotate=startRotate;
        long tempTime = new Date().getTime();
        if (0 != anesTime && -1 != anesTime && tempTime >= anesTime)
        {
            if (0 != anesTimerValue)
            {
                if (isTikle)
                {
                    anesTimer = (int)(anesCount + (tempTime-anesTime)/1000);
                }
                else
                {
                    anesTimer = (int)(anesCount - (tempTime-anesTime)/1000);
                }
            }
            else
            {
                anesTimer = (int)(anesCount + (tempTime-anesTime)/1000);
            }
        }
        else
        {
            if (0 != anesTimerValue)
            {
                anesTimer = anesCount;
            }
            else
            {
                anesTimer = anesCount;
            }
        }



        if (0 != anesTime)
        {
            if (-1 == anesTime)
            {
                anesTimerPause();
            }
            else
            {
                anesTimerStart();
            }
        }
        LogCus.msg(":倒计时：isTikle:"+isTikle+":isTimer:"+isTimer+":anesTime:"+anesTime+":currRotate:"+currRotate+":anesTimerValue:"+anesTimerValue);
    }


    @Override
    public void setArcColors(int roundColor, int progressEndColor) {

        isTikle = PreferencesUtil.getInstance(getContext()).getBooleanValue("AnesTimer", "isTikle",false);

        if (isTikle){
            positions =  new float[]{0,0.9f,1f};
            arcColors =  new int[] {roundColor,roundColor,progressEndColor};
        }else {
            arcColors =  new int[] {progressEndColor,progressEndColor};
            positions =  new float[]{0f,1f};
        }
        // 环形颜色填充
        sweepGradient =new SweepGradient(centerX, centerY, arcColors, positions);
        fillArcPaint.setShader(sweepGradient);
        // 设置画笔为白色
//        fillArcPaint.setColor(progressEndColor);
        // 模糊效果
        fillArcPaint.setMaskFilter(mBlur);
        // 设置线的类型,边是圆的
        fillArcPaint.setStrokeCap(Paint.Cap.ROUND);

        //设置圆弧的宽度
        fillArcPaint.setStrokeWidth(roundWidth);

    }

   /* @Override
    protected int setTimeLine(int baseLine) {
        if (isStart()&&isTikle){
            return centerY;
        }else {
            return super.setTimeLine(baseLine);
        }

    }*/

    @Override
    protected void drawTextTime(Canvas canvas) {
        super.drawTextTime(canvas);
        if (isStart()&&isTikle&&!isInvisibleHour&&!isTimer){
            textPaint.setTextSize(25);
            Paint.FontMetricsInt fontMetricsDate = textPaint.getFontMetricsInt();
            int baselineDate = centerY-fontMetricsDate.top+32;
            canvas.drawText(msgString, centerX, baselineDate, textPaint);
        }
    }

    @Override
    protected void drawProgress(Canvas canvas) {

        if(isTimer){
            super.drawProgress(canvas);
        }else {
            // 确定圆弧的绘制位置，也就是里面圆弧坐标和外面圆弧坐标
            oval.set(centerX-radius_within, centerY-radius_within, centerX+radius_within, centerY+radius_within);
            canvas.save();

            canvas.rotate(-90-currRotate,centerX,centerY);
            // 画圆弧，第二个参数为：起始角度，第三个为跨的角度，第四个为true的时候是实心，false的时候为空心

            canvas.drawArc(oval,
                    0,
                    currRotate,
                    false,fillArcPaint);
            float[] centerProgress=getCenter(centerX,centerY,radius_within,0);
            float progressCircleRadius=roundWidth;
            canvas.drawCircle(centerProgress[0],centerProgress[1],progressCircleRadius,progressCirclePaint);
            canvas.restore();
        }

    }

    @Override
    public String setCurTimerTime(int progress) {
        String msg = getTimeStr(anesTimer);
        try
        {
            getContext().sendBroadcast(new Intent("com.keruiyun.saike.clock")
                    .putExtra("command", 2).putExtra("value", msg));
        }
        catch (Exception ex)
        {
        }
        return msg;
    }

    @Override
    public boolean handleMessage(Message msg) {

        switch (msg.what) {
            case 3:
                anesTimerValue = PreferencesUtil.getInstance(context).getIntValue("AnesTimer", "countdown",
                        0);
                isTikle = PreferencesUtil.getInstance(getContext()).getBooleanValue("AnesTimer", "isTikle",false);

                LogCus.msg(msg.what+"倒计时drawProgress：isTimer:"+isTimer+":isTikle:"+isTikle+":anesTimerValue:"+anesTimerValue);
                if (anesTimerValue > 0) {

                    if (anesTimer > 0) {
                        long time = new Date().getTime();
                        long oldTime = PreferencesUtil.getInstance(getContext()).getLongValue("AnesTimer", "time", 0);
                        int count = PreferencesUtil.getInstance(getContext()).getIntValue("AnesTimer", "count", 0);

                        anesTimer = count - (int)((time - oldTime) / 1000);

                        mHandler.sendMessageDelayed(
                                mHandler.obtainMessage(3, 0, 0), 500);
                    } else {

                        isTikle = true;
                        PreferencesUtil.getInstance(getContext()).setBooleanValue("AnesTimer", "isTikle",
                                isTikle);
                        setArcColors();
                        getTimeStr(anesTimer,true);

                        mAnesTwinkleHandler.removeCallbacksAndMessages(null);

                        PreferencesUtil.getInstance(getContext()).setIntValue("AnesTimer", "count",
                                0);
                        PreferencesUtil.getInstance(getContext()).setLongValue("AnesTimer", "time",
                                new Date().getTime());

                        mHandler.sendEmptyMessage(4);
                        mAnesTwinkleHandler.sendEmptyMessageDelayed(3, 500);
                    }
                } else {
                    long time = new Date().getTime();
                    long oldTime = PreferencesUtil.getInstance(getContext()).getLongValue("AnesTimer", "time", 0);
                    int count = PreferencesUtil.getInstance(getContext()).getIntValue("AnesTimer", "count", 0);

                    anesTimer = count + (int)((time - oldTime) / 1000);

                    mHandler.sendMessageDelayed(
                            mHandler.obtainMessage(3, 0, 0), 500);

                }

                postInvalidate();

                break;
            case 4:
                long time = new Date().getTime();
                long oldTime = PreferencesUtil.getInstance(context).getLongValue("AnesTimer", "time", 0);
                int count = PreferencesUtil.getInstance(context).getIntValue("AnesTimer", "count", 0);

                anesTimer = count + (int)((time - oldTime) / 1000);

                mHandler.sendMessageDelayed(
                        mHandler.obtainMessage(4, 0, 0), 500);
                break;
            default:
                break;
        }
        return false;
    }



     @Override
    protected float secondRotate(int progress) {
        if (isTikle){
            if (isTimer){
                currRotate=super.secondRotate(progress);

                return currRotate;
            }else {
                currRotate=360;
            }

        }else if (anesTimerValue!=0){
            currRotate=(anesTimerValue-anesTimer+1)/(float)anesTimerValue*360.0f;
        }

        return currRotate;


    }


    @Override
    public synchronized void stop() {
        super.stop();
        mHandler.removeCallbacksAndMessages(null);
        mAnesTwinkleHandler.removeCallbacksAndMessages(null);
        isInvisibleHour=false;
        PreferencesUtil.getInstance(context).setIntValue("AnesTimer", "count",
                anesTimer);
        PreferencesUtil.getInstance(context).setLongValue("AnesTimer", "time",
                -1);
    }

    // 手术定时器开始
    public void anesTimerStart() {
        isTikle = PreferencesUtil.getInstance(getContext()).getBooleanValue("AnesTimer", "isTikle",false);
        anesTimerValue = PreferencesUtil.getInstance(context).getIntValue("AnesTimer", "countdown",
                0);

        isTimer=false;
        if (!isStart()){
            if (isTikle) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.sendMessageDelayed(
                        mHandler.obtainMessage(4, anesTimer, 0), 0);
                if (anesTimerValue==0){
                    isTimer=true;
                    startRotate=-90;
                    currRotate=startRotate;
                }else {
                    isTimer=false;
                    startRotate=0;
                    currRotate=startRotate;
                    mAnesTwinkleHandler.removeCallbacksAndMessages(null);
                    mAnesTwinkleHandler.sendMessageDelayed(
                            mAnesTwinkleHandler.obtainMessage(1, anesTimer, 0), 0);
                }

            } else {
                mHandler.sendMessageDelayed(
                        mHandler.obtainMessage(3, anesTimer, 0), 0);
            }

            PreferencesUtil.getInstance(context).setIntValue("AnesTimer", "count",
                    anesTimer);
            PreferencesUtil.getInstance(context).setLongValue("AnesTimer", "time",
                    new Date().getTime());
            setArcColors(roundColor,progressEndColor);
            LogCus.msg("倒计时开始：isTikle:"+isTikle+":isTimer:"+isTimer+":isStart():"+isStart()+":anesTimerValue:"+anesTimerValue);
            start();
        }


    }

    // 手术定时器暂停
    public void anesTimerPause() {
        stop();
        postInvalidate();
    }

    // 手术定时器复位
    public void opTimerReset() {
        isTikle = true;
        opTimerReset(0);
    }

    public void opTimerReset(int anesTimerValue) {
        stop();
        this.anesTimerValue = anesTimerValue;
        PreferencesUtil.getInstance(context).setIntValue("AnesTimer", "countdown",
                anesTimerValue);

        mHandler.removeCallbacksAndMessages(null);
        mAnesTwinkleHandler.removeCallbacksAndMessages(null);

        anesTimer = anesTimerValue;

        PreferencesUtil.getInstance(context).setIntValue("AnesTimer", "count",
                anesTimer);
        PreferencesUtil.getInstance(context).setLongValue("AnesTimer", "time",
                0);
        isTimer=anesTimerValue==0;
        isTikle = anesTimerValue==0;
        LogCus.msg("倒计时复位：isTimer:"+isTimer+":anesTimerValue:"+anesTimerValue);
        PreferencesUtil.getInstance(context).setBooleanValue("AnesTimer", "isTikle",
                isTikle);
        setArcColors(roundColor,progressEndColor);
        if (isTimer){
            startRotate=-90;
            currRotate=startRotate;
        }else {
            startRotate=0;
            currRotate=startRotate;
        }
        postInvalidate();
    }



    private boolean isInvisibleHour;

    @Override
    public boolean isInvisibleHour() {
        return isInvisibleHour;
    }



    public void anesTimerDialog(){
       /* Intent dialog = new Intent(getContext(), TimeDialogActivity.class);
        dialog.putExtra("OpDate", getTimeStr(0));
        dialog.putExtra("AnesDate", getTimeStr(anesTimer));
        dialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(dialog);*/
        DialogFragment_Countdown dialogFragment_countdown=new DialogFragment_Countdown();
        dialogFragment_countdown.set_listener(this);
        dialogFragment_countdown.show(((BaseActivity)getContext()).getSupportFragmentManager(),this.getClass().getName());
    }



    @Override
    public void onCountdownListener(int hour, int minute, int second) {
        anesTimerValue = hour * 60 * 60 + minute * 60
                + second;
        opTimerReset(anesTimerValue);
    }
}
