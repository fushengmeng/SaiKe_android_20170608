package com.music.soundpool;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.keruiyun.saike.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SoundPlayer {
    private Context mContext;
    SoundPool sp;
    Map<Integer,Integer> spMap;

    public void init(Context mContext) {
        this.mContext = mContext;
        spMap=new HashMap<>();
        sp=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        spMap.put(0,sp.load(mContext, R.raw.click,0));
        spMap.put(1,sp.load(mContext, R.raw.slide,1));
        spMap.put(2,sp.load(mContext, R.raw.timing_alarm,1));

    }
    public static class SoundPlayerHolder{
        private static final SoundPlayer instance=new SoundPlayer();
    }

    public static synchronized SoundPlayer getInstance(){
        return SoundPlayerHolder.instance;
    }

    public void playerClick(){
        sp.play(spMap.get(0),1,1,0,0,1);
    }

    public void playerSlide(){
        sp.play(spMap.get(1),1,1,0,0,1);
    }

    public void playerAlarm(){
        sp.play(spMap.get(2),1,1,0,0,1);
    }
}
