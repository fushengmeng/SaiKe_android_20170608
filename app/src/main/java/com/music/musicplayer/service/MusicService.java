package com.music.musicplayer.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.keruiyun.saike.util.LogCus;
import com.music.musicplayer.bean.Mp3Info;
import com.music.musicplayer.utility.Constants;
import com.music.musicplayer.utility.MediaScanner;
import com.music.musicplayer.utility.MediaUtil;
import com.music.musicplayer.utility.SpTools;
import com.music.musicplayer.utility.ThreadPoolUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, OnAudioFocusChangeListener {

    private static final String TAG = "MusicService";
    private List<Mp3Info> mMusic_list = new ArrayList<>();
    private Messenger mMessenger;
    private static MediaPlayer mPlayer;
    private MusicBroadReceiver receiver;
    public static int mCurrentPosition;
    private int mPosition;
    public static int playMode = 2;//1.单曲循环 2.列表循环 0.随机播放
    private Random mRandom;
    public static int prv_position;
    private Message mMessage;
    private static boolean isLoseFocus;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: musicService");
        //音乐列表
        mMusic_list = MediaUtil.getMp3Infos(this);
        regFilter();
        initPlayer();
        mRandom = new Random();
    }

    private void initPlayer() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setOnErrorListener(this);//资源出错
        mPlayer.setOnPreparedListener(this);//资源准备好的时候
        mPlayer.setOnCompletionListener(this);//播放完成的时候

        playMode=SpTools.getInt(getApplicationContext(),Constants.KEY_player_mode,2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
//            mMusic_list = intent.getParcelableArrayListExtra("music_list");
            mMessenger = (Messenger) intent.getExtras().get("messenger");
            mPosition = SpTools.getInt(getApplicationContext(), "music_current_position", 0);
            LogCus.msg(TAG+"onStartCommand:mMessenger:"+(mMessenger != null)+":isPlaying:"+isPlaying());
            if (mMessenger != null&&isPlaying()) {
                sentPreparedMessageToMain();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

        cancelMusic();
        if (receiver != null) {
            getApplicationContext().unregisterReceiver(receiver);
        }
        //stopSelf();

    }

    private void cancelMusic() {
        if (mMessenger!=null){
            mMessage = Message.obtain();
            mMessage.what = Constants.MSG_CANCEL;
            try {
                mMessenger.send(mMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        SpTools.setBoolean(getApplicationContext(),Constants.KEY_player_status,false);
        if (mPlayer != null) {
            SpTools.setInt(getApplicationContext(),Constants.KEY_player_CurrentPosition,mPlayer.getCurrentPosition());
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        Log.i(TAG, "onError: i:"+i+":i1:"+i1);

        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_NEXT);
        sendBroadcast(intent);

        return true;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.i(TAG, "onPrepared: ");
        mPlayer.start();//开始播放
        SpTools.setBoolean(getApplicationContext(),"isPlayer",true);
        if (mMessenger != null) {
            sentPreparedMessageToMain();

        }
    }

    private void sentPreparedMessageToMain() {
        int totalDuration = mPlayer.getDuration();
        Message mMessage = new Message();
        mMessage.what = Constants.MSG_PREPARED;
        mMessage.arg1 = mPosition;
        mMessage.arg2 = totalDuration;
        mMessage.obj = mPlayer.isPlaying();
        try {
            //发送播放位置
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mMusicProgressHandler.sendEmptyMessage(1);
    }

    private void sentPlayStateToMain() {
        if (mMessenger==null)
            return;
        mMessage = Message.obtain();
        mMessage.what = Constants.MSG_PLAY_STATE;
        mMessage.obj = mPlayer.isPlaying();
        try {
            //发送播放状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private Handler mMusicProgressHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (mMessenger!=null){
                        if (mPlayer.isPlaying()) {
                            //1.准备好的时候.告诉activity,当前歌曲的总时长
                            int currentPosition = mPlayer.getCurrentPosition();
                            int totalDuration = mPlayer.getDuration();
                            mMessage = Message.obtain();
                            mMessage.what = Constants.MSG_PROGRESS;
                            mMessage.arg1 = currentPosition;
                            mMessage.arg2 = totalDuration;
                            //2.发送消息
                            try {
                                mMessenger.send(mMessage);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        mMusicProgressHandler.sendEmptyMessageDelayed(1,1000);
                    }else {
                        mMusicProgressHandler.removeCallbacksAndMessages(null);
                    }

                    break;

            }

        }
    };


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_NEXT);
        sendBroadcast(intent);
    }

    /**
     * 播放
     */
    private void play(int position) {
        if (mPlayer != null && mMusic_list.size() > 0) {
            mPlayer.reset();
            try {
                mPosition=position;
                SpTools.setInt(getApplicationContext(), "music_current_position", mPosition);
                mPlayer.setDataSource(mMusic_list.get(position).getUrl());
                mPlayer.prepareAsync();
                sentMusicPosition();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        SpTools.setBoolean(getApplicationContext(),Constants.KEY_player_status,false);
        if (mPlayer != null)
            SpTools.setInt(getApplicationContext(),Constants.KEY_player_CurrentPosition,mPlayer.getCurrentPosition());
        if (mPlayer != null && mPlayer.isPlaying()) {
            mCurrentPosition = mPlayer.getCurrentPosition();
            mPlayer.pause();
            sentPlayStateToMain();
        }
    }

    /**
     *
     */
    private void seekTo(int position) {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.seekTo(position);
            sentPlayStateToMain();
        }
    }

    private void refreshMusicList(){
        MediaScanner.getInstace().scanMusicFile(new MediaScanner.MusicScanListener() {
            @Override
            public void onScanCompleted() {
                mMusic_list.clear();
                mMusic_list=MediaUtil.getMp3Infos(MusicService.this);
                if (mMessenger==null)
                    return;
                sentMusicList();
            }
        });
    };

    private void clearMusicList(){
        if (isPlaying())
            pause();
        MediaUtil.clearMedia(this);
        mMusic_list.clear();
        sentMusicList();
    }

    private void sentMusicList(){
        if (mMessenger==null)
            return;
        mMessage = Message.obtain();
        mMessage.what = Constants.MSG_REFRESH_MUSICList;
        try {
            //发送播放状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };
    private void sentMusicPosition(){
        if (mMessenger==null)
            return;
        mMessage = Message.obtain();
        mMessage.what = Constants.MSG_REFRESH_MUSIC_Position;
        mMessage.arg1=mPosition;

        try {
            //发送播放状态
            mMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    };

    /**
     * 注册广播
     */
    private void regFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_REFRESH_LIST);
        intentFilter.addAction(Constants.ACTION_LIST_ITEM);
        intentFilter.addAction(Constants.ACTION_PAUSE);
        intentFilter.addAction(Constants.ACTION_PLAY);
        intentFilter.addAction(Constants.ACTION_NEXT);
        intentFilter.addAction(Constants.ACTION_PRV);
        intentFilter.addAction(Constants.ACTION_CLEAR);
        intentFilter.addAction(Constants.ACTION_CLOSE);
        intentFilter.addAction(Constants.ACTION_SEEK);
        intentFilter.addAction(Constants.ACTION_CANCEL_MSG);
        intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        intentFilter.setPriority(1000);
        if (receiver == null) {
            receiver = new MusicBroadReceiver();
        }
        getApplicationContext().registerReceiver(receiver, intentFilter);
    }

    /**
     * 广播接收者
     */
    public class MusicBroadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.ACTION_LIST_ITEM:
                    Log.i(TAG, "onReceive: ACTION_LIST_ITEM");
                    //点击左侧菜单
                    mPosition = intent.getIntExtra("position", 0);
                    play(mPosition);
                    break;
                case Constants.ACTION_REFRESH_LIST:
                    //刷新列表
                    refreshMusicList();
                    break;
                case Constants.ACTION_PAUSE:
                    Log.i(TAG, "onReceive: ACTION_PAUSE");
                    //暂停播放
                    pause();
                    break;
                case Constants.ACTION_PLAY:
                    Log.i(TAG, "onReceive: ACTION_PLAY");
                    //开始播放
                    if (mPlayer != null) {
//                        mPlayer.seekTo(mCurrentPosition);
                        mPlayer.start();
                        //通知是否在播放
                        sentPlayStateToMain();
                    }else {
                        initPlayer();
                        play(mPosition);
                    }
                    break;
                case Constants.ACTION_NEXT:
                    Log.i(TAG, "onReceive: ACTION_NEXT");
                    //下一首
                    prv_position = mPosition;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(mPosition);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        mPosition++;
                        if (mPosition <= mMusic_list.size() - 1) {
                            play(mPosition);
                        } else {
                            mPosition = 0;
                            play(mPosition);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case Constants.ACTION_PRV:
                    Log.i(TAG, "onReceive: ACTION_PRV");
                    //上一首
                    prv_position = mPosition;
                    if (playMode % 3 == 1) {//1.单曲循环
                        play(mPosition);
                    } else if (playMode % 3 == 2) {//2.列表播放
                        mPosition--;
                        if (mPosition < 0) {
                            mPosition = mMusic_list.size() - 1;
                            play(mPosition);
                        } else {
                            play(mPosition);
                        }
                    } else if (playMode % 3 == 0) {// 0.随机播放
                        play(getRandom());
                    }
                    break;
                case Constants.ACTION_CLEAR:
                    Log.i(TAG, "onReceive: ACTION_CLEAR");
                    clearMusicList();

                    break;
                case Constants.ACTION_CLOSE:
                    Log.i(TAG, "onReceive: ACTION_CLOSE");
                    cancelMusic();
                    break;
                case Constants.ACTION_SEEK://seekbar手动控制
                    Log.i(TAG, "onReceive: ACTION_SEEK");
                    if (mPlayer != null && mPlayer.isPlaying()) {
                        mCurrentPosition = mPlayer.getCurrentPosition();
                        int seekto = intent.getIntExtra("seekto", mCurrentPosition);
                        seekTo(seekto);
                    }

                    break;
                case Constants.ACTION_CANCEL_MSG://取消监听
                    Log.i(TAG, "onReceive: ACTION_CANCEL_MSG");
                    mMessenger=null;
                    break;
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    Log.i(TAG, "onReceive: ACTION_AUDIO_BECOMING_NOISY");
                    //如果耳机拨出时暂停播放
                    Intent intent_pause = new Intent();
                    intent_pause.setAction(Constants.ACTION_PAUSE);
                    sendBroadcast(intent_pause);
                    break;
            }
        }
    }

    private int getRandom() {
        mPosition = mRandom.nextInt(mMusic_list.size());
        return mPosition;
    }

    public static boolean isPlaying(){
        if(mPlayer != null){
            return mPlayer.isPlaying();
        }
        return false;
    }

    /**
     * ---------------音频焦点处理相关的方法---------------
     **/
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN://你已经得到了音频焦点。
                Log.i(TAG, "onAudioFocusChange: -------------AUDIOFOCUS_GAIN---------------");
                // resume playback
                if (isLoseFocus) {
                    isLoseFocus = false;
                    mPlayer.start();
                    mPlayer.setVolume(1.0f, 1.0f);
                    sentPlayStateToMain();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS://你已经失去了音频焦点很长时间了。你必须停止所有的音频播放
                Log.i(TAG, "onAudioFocusChange: -------------AUDIOFOCUS_LOSS---------------");
                // Lost focus for an unbounded amount of time: stop playback and release media player
                isLoseFocus = false;
                SpTools.setBoolean(getApplicationContext(),Constants.KEY_player_status,false);
                if (mPlayer!=null)
                    SpTools.setInt(getApplicationContext(),Constants.KEY_player_CurrentPosition,mPlayer.getCurrentPosition());
                if (mPlayer.isPlaying()) {

                    mPlayer.stop();
                    sentPlayStateToMain();
                }
                mPlayer.release();
                mPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://你暂时失去了音频焦点
                Log.i(TAG, "onAudioFocusChange: -------------AUDIOFOCUS_LOSS_TRANSIENT---------------");
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mPlayer.isPlaying()) {
                    isLoseFocus = true;
                    mPlayer.pause();
                    sentPlayStateToMain();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://你暂时失去了音频焦点，但你可以小声地继续播放音频（低音量）而不是完全扼杀音频。
                Log.i(TAG, "onAudioFocusChange: -------------AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK---------------");
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mPlayer.isPlaying()) {
                    isLoseFocus = true;
                    mPlayer.setVolume(0.1f, 0.1f);
                    sentPlayStateToMain();
                }
                break;
        }

    }

}
