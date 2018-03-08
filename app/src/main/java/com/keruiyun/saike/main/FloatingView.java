package com.keruiyun.saike.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintImageView;
import com.keruiyun.saike.R;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.keruiyun.saike.setting.data.Data_Smartstart;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.SharePreferenceUtil;


/**
 * Created by xiang on 2016/12/28.
 *
 * im悬浮窗视图
 */

public class FloatingView extends RelativeLayout{

    // 悬浮栏位置
    private final static int LEFT = 0;
    private final static int RIGHT = 1;
    private final static int TOP = 3;
    private final static int BUTTOM = 4;

    private int dpi;
    private int screenHeight;
    private int screenWidth;
    private WindowManager.LayoutParams wmParams;
    private WindowManager wm;
    private float x, y;
    private float mTouchStartX;
    private float mTouchStartY;
    private boolean isScroll;

    private TintImageView tintImageView;

    public static boolean isLaunch;
    public static int var=0;

    public FloatingView(Activity activity) {
        super(activity);
        LayoutInflater.from(activity).inflate(R.layout.layout_suspended, this);
//        setBackgroundResource(R.drawable.chat_btn);
        wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
       activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        //通过像素密度来设置按钮的大小
        dpi = dpi(dm.densityDpi);
        //屏宽
        screenWidth = wm.getDefaultDisplay().getWidth();
        //屏高
        screenHeight = wm.getDefaultDisplay().getHeight();
        //布局设置
        wmParams = new WindowManager.LayoutParams();
        // 设置window type
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置Window flag
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = dpi;
        wmParams.height = dpi;
        wmParams.x=SharePreferenceUtil.getInt("wmParams.x",0);
        wmParams.y =SharePreferenceUtil.getInt("wmParams.x",screenHeight - dpi-96);
        wm.addView(this, wmParams);
        hide();
        Animation operatingAnim = AnimationUtils.loadAnimation(activity, R.anim.rotate_floating);
        tintImageView= (TintImageView) this.findViewById(R.id.img_rotate);
        tintImageView.startAnimation(operatingAnim);


    }

    /**一步启动*/
    public void oneStepLaunch(){
        if (isLaunch){
            //关闭
            SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,   0);

            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, 0);

            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_PRESSURE_KEY, 0);
            tintImageView.setImageTintList(R.color.yellow);
            var=0;
        }else {
            //开启
            Data_Smartstart data_smartstart=new Data_Smartstart();
            boolean isLight1=data_smartstart.isSwitchLighting1();
            boolean isLight2=data_smartstart.isSwitchLighting2();
            boolean isFilmViewer=data_smartstart.isSwitchFilmViewer();

            boolean isIntraoperative=data_smartstart.isSwitchIntraoperative();
            boolean isShadowlessLamp=data_smartstart.isSwitchShadowlessLamp();
            boolean isExhaustFan=data_smartstart.isSwitchExhaustFan();

            boolean isWritingCounter=data_smartstart.isSwitchWritingCounter();
            boolean isAirStartStop=data_smartstart.isSwitchAirStartStop();
            boolean isVacuumRun=data_smartstart.isSwitchVacuumRun();

            String value="";
            value=(isLight1 ? 1 : 0)+value;
            value=(isLight2 ? 1 : 0)+value;
            value=(isExhaustFan ? 1 : 0)+value;
            value=(isFilmViewer ? 1 : 0)+value;
            value=(isShadowlessLamp ? 1 : 0)+value;
            value=(isWritingCounter ? 1 : 0)+value;
            value=(isIntraoperative ? 1 : 0)+value;
            int temp = Integer.valueOf(value,2);
            var=temp;
            SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_CONTROL_KEY,   temp);

            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_POWER_KEY, isAirStartStop?1:0);

            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_PRESSURE_KEY, isVacuumRun?1:0);
            //空调温度设定值
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_TEMPERATURE, data_smartstart.getTxtValueTemp() );
            //空调湿度增加
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_HUMIDITY, data_smartstart.getTxtValueRh());

            //气压增加
            SerialSaunaThread.writeCmdQueue(1,
                    SerialSaunaThread.ADDR_SET_PRESSURE, data_smartstart.getTxtValuePa());
            tintImageView.setImageTintList(R.color.green);
        }
        isLaunch=!isLaunch;
    }





    /**
     * 根据密度选择控件大小
     *
     */
    private int dpi(int densityDpi) {
        if (densityDpi <= 120) {
            return 72;
        } else if (densityDpi <= 160) {
            return 96;
        } else if (densityDpi <= 240) {
            return 144;
        } else if (densityDpi <= 320) {
            return 196;
        }
        return 108;
    }

    public void show() {
        if (isShown()) {
            return;
        }
        setVisibility(View.VISIBLE);
    }


    public void hide() {
        setVisibility(View.GONE);
    }

    public void destory() {
        hide();
        wm.removeViewImmediate(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标， 以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // setBackgroundDrawable(openDrawable);
                // invalidate();
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isScroll) {
                    updateViewPosition();
                } else {
                    // 当前不处于连续滑动状态 则滑动小于图标1/3则不滑动
                    if (Math.abs(mTouchStartX - event.getX()) > dpi / 3
                            || Math.abs(mTouchStartY - event.getY()) > dpi / 3) {
                        updateViewPosition();
                    } else {
                        break;
                    }
                }
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                // 拖动
                if (isScroll) {
                    updateViewPosition();
                    // setBackgroundDrawable(closeDrawable);
                    // invalidate();
//                    LogCus.msg("ACTION_MOVE---ACTION_UP:"+isLaunch+":isScroll:"+isScroll);
                } else {
                    // 当前显示功能区，则隐藏
                    // setBackgroundDrawable(openDrawable);
                    // invalidate();
//                    LogCus.msg("ACTION_UP:"+isLaunch+":isScroll:"+isScroll);
                    oneStepLaunch();
                }
                isScroll = false;
                mTouchStartX = mTouchStartY = 0;
                break;
        }

        return super.onTouchEvent(event);
    }




    // 更新浮动窗口位置参数
    private void updateViewPosition() {
        wmParams.x = (int) (x - mTouchStartX);
        //是否存在状态栏（提升滑动效果）
        // 不设置为全屏（状态栏存在） 标题栏是屏幕的1/25
        wmParams.y = (int) (y - mTouchStartY - screenHeight / 25);
        SharePreferenceUtil.putInt("wmParams.x",wmParams.x);
        SharePreferenceUtil.putInt("wmParams.y",wmParams.y);
        wm.updateViewLayout(this, wmParams);
    }
}

