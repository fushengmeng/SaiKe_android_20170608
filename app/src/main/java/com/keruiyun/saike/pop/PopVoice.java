package com.keruiyun.saike.pop;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.keruiyun.saike.R;
import com.keruiyun.saike.controls.VerticalSeekBar;
import com.keruiyun.saike.main.Fragment_Main;
import com.keruiyun.saike.serialservice.SerialSaunaThread;

import butterknife.BindView;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Administrator on 2017/12/27.
 */

public class PopVoice extends BasePopupWindow {


    private FrameLayout layout;

    VerticalSeekBar seekbarVoice;

    private int currentVolume;

    AudioManager audioMgr;
    int maxVolume;

    public PopVoice(Context context) {
        super(context);


    }

    @Override
    public int loadContentView() {
        return R.layout.pop_voice;
    }

    @Override
    public void initView() {
        super.initView();
        audioMgr = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        maxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        layout=findById(R.id.layout);
        seekbarVoice=findById(R.id.seekbar_voice);
//        SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 0);
        currentVolume= Fragment_Main.volume;
        seekbarVoice.setMax(4);
        seekbarVoice.setProgress(currentVolume);
        seekbarVoice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                int voice=progress/20;
                if (progress!=0)
                    adjustVolume(maxVolume*4/progress);
                SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_VOLUMN_KEY,
                        progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setColorBg(String color){
        layout.setBackgroundColor(Color.parseColor(color));
    }


    public void updateVolume(int data) {
        currentVolume=data;
        if (data != seekbarVoice.getProgress()) {
            seekbarVoice.setProgress(currentVolume);
        }
    }

    /**
     * 调整音量
     */
    private void adjustVolume(int curVolume) {
        audioMgr.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                AudioManager.FLAG_PLAY_SOUND);
    }

}
