package com.keruiyun.saike.timerserver;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;

public class ServiceFiveTimer extends Service {
    private int curSecond;
    public static final String ACTION_FiveTimer="com.keruiyun.saike.timerserver.ServiceFiveTimer";
    public ServiceFiveTimer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (curSecond==0)
            timer();
        return super.onStartCommand(intent, flags, startId);
    }

    private void timer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //300秒
                for (int i=0;i<=300;i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what=1;
                    msg.arg1=i;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                curSecond=msg.arg1;
                if (msg.arg1>=300){
                    curSecond=0;
                    PreferencesUtil.getInstance(ServiceFiveTimer.this).setBooleanValue("User", "login",false);
                    sendBroadcast(new Intent(ACTION_FiveTimer));
                    mHandler.removeCallbacksAndMessages(null);
                    stopSelf();
                }
            }
        }
    };
}
