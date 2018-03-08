package com.keruiyun.saike.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bilibili.magicasakura.widgets.TintImageView;
import com.bilibili.magicasakura.widgets.TintLinearLayout;
import com.keruiyun.saike.uiview.NoSeekBar;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;
import com.music.musicplayer.bean.Mp3Info;
import com.music.musicplayer.functions.Subscriber;
import com.music.musicplayer.service.MusicService;
import com.music.musicplayer.utility.Constants;
import com.music.musicplayer.utility.LrcUtil;
import com.music.musicplayer.utility.MediaUtil;
import com.music.musicplayer.utility.SpTools;
import com.music.musicplayer.view.LrcView;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.pop.PopMusicMode;
import com.keruiyun.saike.pop.PopVoice;
import com.keruiyun.saike.serialservice.SerialSaunaThread;
import com.sd.SDCardUtils;
import com.sd.StorageInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * 音乐播放器
 */

public class DialogFragment_Music extends BaseDialogFragment {

    @BindView(R.id.music_lrc)
    LrcView mCurrentLrc;
    @BindView(R.id.music_local_icon)
    TintImageView musicLocalIcon;
    @BindView(R.id.music_local_txt)
    TextView musicLocalTxt;
    @BindView(R.id.music_local)
    TintLinearLayout musicLocal;
    @BindView(R.id.music_remote_icon)
    TintImageView musicRemoteIcon;
    @BindView(R.id.music_remote_txt)
    TextView musicRemoteTxt;
    @BindView(R.id.music_remote)
    TintLinearLayout musicRemote;
    @BindView(R.id.music_bluetooth_icon)
    TintImageView musicBluetoothIcon;
    @BindView(R.id.music_bluetooth_txt)
    TextView musicBluetoothTxt;
    @BindView(R.id.music_bluetooth)
    TintLinearLayout musicBluetooth;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.img_back)
    ImageView imgBack;

    private String TAG = DialogFragment_Music.class.getSimpleName();
    private PopVoice popVoice;

    ViewHolderMusicTools viewHolderMusicTools;
    private int mPosition;
    private boolean mIsPlaying = false;
    private List<Mp3Info> mMusicList = new ArrayList<>();
    private Mp3Info mMp3Info;
    PlayQueueFragment playQueueFragment;

    @Override
    public int loadContentView() {
        return R.layout.fragment_music;
    }

    @Override
    public int loadContentBottomView() {
        return R.layout.layout_bottom_music;
    }

    @Override
    public int[] setWidthHeight() {
        return new int[]{1027, 768};
    }

    @Override
    public void initBottomView(View bottomView) {
        super.initBottomView(bottomView);
        albumArt.setImageResource(R.drawable.bg_music);
        viewHolderMusicTools = new ViewHolderMusicTools(bottomView);
    }

    @Override
    public void initView(View view) {

        super.initView(view);

        initMusicLeft();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initMusicData();
    }

    @Override
    public void onDestroy() {
        sendBroadcast(Constants.ACTION_CANCEL_MSG);
        super.onDestroy();
        SpTools.setInt(mContext, "music_current_position", mPosition);

        if (!mIsPlaying)
            mContext.stopService(new Intent(mContext,MusicService.class));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 10:
                    if (isDialogShow)
                        mCurrentLrc.reset();
                    break;
                case Constants.MSG_PROGRESS:
                    if (!isDialogShow)
                        break;
                    int currentPosition = msg.arg1;
                    int totalDuration = msg.arg2;
                    viewHolderMusicTools.playSeek.setProgress(currentPosition);
                    viewHolderMusicTools.musicDurationPlayed.setText(formatTime(currentPosition));
//                viewHolderMusicTools.playSeek.setMax(totalDuration);
                    mCurrentLrc.updateTime(currentPosition);
                    break;
                case Constants.MSG_PREPARED:
                    mPosition = msg.arg1;
                    mIsPlaying = (boolean) msg.obj;
                    totalDuration = msg.arg2;
                    if (!isDialogShow)
                        break;
                    viewHolderMusicTools.playSeek.setMax(totalDuration);
                    viewHolderMusicTools.musicDuration.setText(formatTime(totalDuration));
                    switchSongUI(mPosition, mIsPlaying);
                    break;
                case  Constants.MSG_PLAY_STATE:
                    mIsPlaying = (boolean) msg.obj;
                    break;
                case  Constants.MSG_REFRESH_MUSICList:
                    mMusicList=MediaUtil.getMp3Infos(mContext);
                    if (playQueueFragment!=null)
                        playQueueFragment.refrshList();
                    break;
                case  Constants.MSG_REFRESH_MUSIC_Position:
                    int position=msg.arg1;
                    LogCus.msg("当前播放："+position);
                    if (playQueueFragment!=null)
                        playQueueFragment.refreshPosition(position);
                    break;
                case Constants.MSG_CANCEL:
                    mIsPlaying = false;
                    dismiss();
                    break;

            }


        }
    };

    private void initMusicData() {
        //音乐列表
        mMusicList = MediaUtil.getMp3Infos(mContext);
        //启动音乐服务
        startMusicService();

        //初始化控件UI，默认显示历史播放歌曲
        mPosition = SpTools.getInt(mContext, "music_current_position", 0);
        mIsPlaying = MusicService.isPlaying();

        switchSongUI(mPosition, mIsPlaying);
    }


    private void initMusicLeft() {

        musicLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBackChange(0);
                SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 0);
            }
        });

        musicRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBackChange(1);
                SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 1);
            }
        });

        musicBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBackChange(2);
                SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 2);
            }
        });
        int type = PreferencesUtil.getInstance(mContext).getIntValue("musicType", "musicType",
                0);
        musicBackChange(type);
    }



    /**
     * 格式化时间，将毫秒转换为分:秒格式
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 开始音乐服务并传输数据
     */
    private void startMusicService() {
        Intent musicService = new Intent();
        musicService.setClass(mContext, MusicService.class);
//        musicService.putParcelableArrayListExtra("music_list", (ArrayList<? extends Parcelable>) mMusicList);
        musicService.putExtra("messenger", new Messenger(handler));
        mContext.startService(musicService);
    }

    /**
     * 刷新播放控件的歌名，歌手，图片，按钮的形状
     */
    private void switchSongUI(int position, boolean isPlaying) {
        if (isPlaying) {
            viewHolderMusicTools.playingPlay.setImageResource(R.drawable.sk_leku_46);
        } else {
            viewHolderMusicTools.playingPlay.setImageResource(R.drawable.sk_leku_47);
        }
        switch (MusicService.playMode){
            case 0:
                viewHolderMusicTools.playingMode.setImageResource(R.drawable.sk_leku_36_1);
                break;
            case 1:
                viewHolderMusicTools.playingMode.setImageResource(R.drawable.sk_leku_38_1);
                break;
            case 2:
                viewHolderMusicTools.playingMode.setImageResource(R.drawable.sk_leku_55_1);
                break;
        }

        if (mMusicList.size() > 0 && position < mMusicList.size()) {
            // 1.获取播放数据
            mMp3Info = mMusicList.get(position);
            // 2.设置歌曲名，歌手
            String mSongTitle = mMp3Info.getTitle();
            String mSingerArtist = mMp3Info.getArtist();
            viewHolderMusicTools.txtMusicName.setText(mSongTitle);
//            mSinger.setText(mSingerArtist);
            // 4.更换音乐背景
            Bitmap mBitmap = MediaUtil.getArtwork(mContext, mMp3Info.getId(), mMp3Info.getAlbumId(), true, true);
            viewHolderMusicTools.imgMusic.setImageBitmap(mBitmap);
            mBitmap = MediaUtil.getArtwork(mContext, mMp3Info.getId(), mMp3Info.getAlbumId(), true, false);
            albumArt.setImageBitmap(mBitmap);


            // 5.设置歌词
            File mFile = MediaUtil.getLrcFile(mMp3Info.getUrl());
            if (mFile != null) {
                Log.i(TAG, "switchSongUI: mFile != null");
                try {
                    BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(mFile)));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = inputStreamReader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                    mCurrentLrc.loadLrc(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                LrcUtil.getMusicLrc(mMp3Info.getTitle(), mMp3Info.getArtist(), new Subscriber<String>() {
                    @Override
                    public void onComplete(String s) {
                        if (isDialogShow)
                            mCurrentLrc.loadLrc(s);
                        //保存歌词到本地
                        File file = new File(mMp3Info.getUrl().replace(".mp3", ".lrc"));
                        FileOutputStream fileOutputStream;
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                            outputStreamWriter.write(s);
                            outputStreamWriter.close();
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                        handler.sendEmptyMessage(10);

                    }
                });
            }

        } else {
//            Toast.makeText(this, "当前没有音乐，记得去下载再来。", Toast.LENGTH_LONG).show();
        }
    }

    private int musicType=-1;

    /*0=电话，1：背景音乐*/
    private void musicBackChange(int type) {
//        LogCus.msg("背景音乐 MUSIC:musicType:"+musicType+":type:"+type);
        if (musicType==type)
            return;
        musicType=type;
       PreferencesUtil.getInstance(mContext).setIntValue("musicType", "musicType",
                0);
        if (1 == musicType) {
//                tvMusicBack.setText("背景音乐 MUSIC");

        } else {//
//                tvMusicBack.setText("电话");

        }
        musicLocalIcon.setImageTintList(R.color.white);
        musicLocalTxt.setSelected(false);

        musicRemoteIcon.setImageTintList(R.color.white);
        musicRemoteTxt.setSelected(false);

        musicBluetoothIcon.setImageTintList(R.color.white);
        musicBluetoothTxt.setSelected(false);
        switch (musicType) {
            case 0:
                musicLocalIcon.setImageTintList(R.color.theme_color_primary);
                musicLocalTxt.setSelected(true);
                viewHolderMusicTools.playingPlaylist.setEnabled(true);
                break;
            case 1:
                musicRemoteIcon.setImageTintList(R.color.theme_color_primary);
                musicRemoteTxt.setSelected(true);
                viewHolderMusicTools.playingPlaylist.setEnabled(false);
                break;
            case 2:
                musicBluetoothIcon.setImageTintList(R.color.theme_color_primary);
                musicBluetoothTxt.setSelected(true);
                viewHolderMusicTools.playingPlaylist.setEnabled(false);
                break;

        }



//        if (musicRemoteTxt==null)
//            return;
//
//        if (musicRemoteTxt.isSelected() || musicBluetoothTxt.isSelected()) {
//            if (1 == musicBack || 2 == musicBack) {
////                tvMusicBack.setText("背景音乐 MUSIC");
//                viewHolderMusicTools.playingPlaylist.setEnabled(false);
//            } else {//
////                tvMusicBack.setText("");
//                viewHolderMusicTools.playingPlaylist.setEnabled(true);
//            }
//        }
    }

    private int musicBack;
    @Override
    public void updateSerialData(int addr, short data) {
        super.updateSerialData(addr, data);
        switch (addr) {
            case SerialSaunaThread.ADDR_VOLUMN_KEY:
                if (popVoice != null)
                    popVoice.updateVolume(data);
                break;
            case SerialSaunaThread.ADDR_MUSIC_KEY:
                musicBack=data;
                if (data==0){
                    //关闭免提按键
                    SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_HANDFREE_KEY,0);
                    int type = PreferencesUtil.getInstance(mContext).getIntValue("musicType", "musicType",
                            0);
                    SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, type);
                    musicBackChange(type);
//                    LogCus.msg("背景音乐："+musicBack);
                }
                break;
        }
    }


    class ViewHolderMusicTools {
        @BindView(R.id.img_music)
        ImageView imgMusic;
        @BindView(R.id.txt_music_name)
        TextView txtMusicName;
        @BindView(R.id.music_duration_played)
        TextView musicDurationPlayed;
        @BindView(R.id.music_duration)
        TextView musicDuration;
        @BindView(R.id.play_seek)
        NoSeekBar playSeek;
        @BindView(R.id.playing_pre)
        ImageView playingPre;
        @BindView(R.id.playing_play)
        ImageView playingPlay;
        @BindView(R.id.playing_next)
        ImageView playingNext;
        @BindView(R.id.playing_mode)
        ImageView playingMode;
        @BindView(R.id.playing_voice)
        ImageView playingVoice;
        @BindView(R.id.playing_playlist)
        ImageView playingPlaylist;
        @BindView(R.id.playing_addlist)
        ImageView playingAddlist;

        ViewHolderMusicTools(View view) {
            ButterKnife.bind(this, view);
            setTools();
        }

        private void setTools() {

            playingMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopMusicMode popMode = new PopMusicMode(mContext, playingMode);
                    popMode.setColorBg("#272829");
                    popMode.showAsTopCenter(playingMode, 490, 130);

                }
            });

            playingPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendBroadcast(Constants.ACTION_PRV);
                }
            });
            playingVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popVoice = new PopVoice(mContext);
                    popVoice.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            popVoice = null;
                        }
                    });
                    popVoice.setColorBg("#272829");
                    popVoice.showAsTopCenter(v, 438, 130);
                }
            });

            playingPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SerialSaunaThread.writeCmdQueue(1, SerialSaunaThread.ADDR_MUSIC_KEY, 0);
                    musicBackChange(0);
                    if (mIsPlaying) {
                        sendBroadcast(Constants.ACTION_PAUSE);
                        playingPlay.setImageResource(R.drawable.sk_leku_47);
                    } else {
                        sendBroadcast(Constants.ACTION_PLAY);
                        playingPlay.setImageResource(R.drawable.sk_leku_46);
                    }

                }
            });

            playingNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendBroadcast(Constants.ACTION_NEXT);
                }
            });

            playingPlaylist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playQueueFragment = new PlayQueueFragment();
                    playQueueFragment.setOnDialogFragmentListener(new OnDialogFragmentListener() {
                        @Override
                        public void onDialogFragmentDismissed() {
                            playQueueFragment=null;
                        }
                    });
                    playQueueFragment.show(getChildFragmentManager(), "playlistframent");
                }
            });

            playingAddlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DialogFragment_Scan_Audio()
                            .show(getChildFragmentManager(),DialogFragment_Scan_Audio.class.getName());


            /*
                SimpleMoreFragment moreFragment = SimpleMoreFragment.newInstance(MusicPlayer.getCurrentAudioId());
                moreFragment.show(getSupportFragmentManager(), "music");*/

                }
            });

            /*playSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    Intent intent = new Intent();
                    intent.setAction(Constants.ACTION_SEEK);
                    intent.putExtra("seekto",progress);
                    mContext.sendBroadcast(intent);

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });*/


        /*收藏*/
        /* mFav.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (isFav) {
                        mPlaylistsManager.removeItem(PlayingActivity.this, IConstants.FAV_PLAYLIST,
                                MusicPlayer.getCurrentAudioId());
                        mFav.setImageResource(R.drawable.play_rdi_icn_love);
                        isFav = false;
                    } else {
                        try {
                            MusicInfo info = MusicPlayer.getPlayinfos().get(MusicPlayer.getCurrentAudioId());
                            mPlaylistsManager.insertMusic(PlayingActivity.this, IConstants.FAV_PLAYLIST, info);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mFav.setImageResource(R.drawable.play_icn_loved);
                        isFav = true;
                    }

                    Intent intent = new Intent(IConstants.PLAYLIST_COUNT_CHANGED);
                    sendBroadcast(intent);
                }
            });*/


        }


    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        mContext.sendBroadcast(intent);
    }
}

