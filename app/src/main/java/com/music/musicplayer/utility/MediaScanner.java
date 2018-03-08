package com.music.musicplayer.utility;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.util.LogCus;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MediaScanner {

    private volatile static MediaScanner instance;


    private String fileType = null;
    private Context mContext;
    PlayMusic playMusic;
    Handler handler;

    /**
     * 要扫描的媒体文件类型 eg: audio/mp3  media/*  application/ogg
     *             image/jpeg  image/png  video/mpeg   video/3gpp
     *             ......
     */
    private MediaScanner(){
        mContext= MainApplication.context;
        fileType = "audio/mp3";
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        if (musicScanListener!=null)
                            musicScanListener.onScanCompleted();
                        break;
                }
            }
        };
    }

    public static MediaScanner getInstace(){
        synchronized (MediaScanner.class){
            if(instance == null){
                instance = new MediaScanner();
            }
        }
        return instance;
    }


    /**
     * 全盘扫描
     */
    public void scanMusicFile(MusicScanListener musicScanListener) {
        setMusicScanListener(musicScanListener);
        File dir= Environment.getExternalStorageDirectory();
        scanfile(dir);

    }

    private MusicScanListener musicScanListener;

    public void setMusicScanListener(MusicScanListener musicScanListener) {
        this.musicScanListener = musicScanListener;
    }

    public interface MusicScanListener{
        public void onScanCompleted();
    }

    private int curSannerPosition;


    class MusicSannerClient implements
            MediaScannerConnection.MediaScannerConnectionClient {
        private File filePath = null;
        private MediaScannerConnection mediaScanConn;

        public MusicSannerClient(File filePath) {
            this.filePath = filePath;
        }

        public void start(){
            mediaScanConn = new MediaScannerConnection(mContext, this);
            mediaScanConn.connect();
        }

        @Override
        public void onMediaScannerConnected() {

            if (filePath != null) {
                if (filePath.isDirectory()) {
                    File[] files = filePath.listFiles();
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {
                            if (files[i].isDirectory())
                                scanfile(files[i]);
                            else {
                                mediaScanConn.scanFile(
                                        files[i].getAbsolutePath(), fileType);
                            }
                        }
                    }
                }
            }

        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            // TODO Auto-generated method stub
            mediaScanConn.disconnect();
            LogCus.msg("--扫描onScanCompleted："+path);
            if(playMusic != null)
                handler.removeCallbacks(playMusic);
            playMusic = new PlayMusic(0);
            handler.postDelayed(playMusic,70);

        }


    }

    private void scanfile(File f) {
        curSannerPosition++;
        new MusicSannerClient(f).start();


    }

    class PlayMusic implements Runnable{
        int position;
        public PlayMusic(int position){
            this.position = position;
        }

        @Override
        public void run() {

            handler.sendEmptyMessage(1);
        }
    }
}