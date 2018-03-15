package com.keruiyun.saike.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.sd.CustomExce;
import com.sd.Enum_Dir;
import com.sd.FileUtils;
import com.sd.SDCardUtils;
import com.sd.StorageInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 扫描u盘音频文件并复制到本地
 */


public class DialogFragment_Scan_Audio extends BaseDialogFragment {

    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Override
    public int loadContentView() {
        return R.layout.layout_scan_audio;
    }

    @Override
    public int loadTintImage() {
        return R.drawable.bg_validsure;
    }

    @Override
    public void initView(View view) {
        super.initView(view);

        scanAudio();
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    txtTitle.setText(getResources().getString(R.string.scan_noaudio));
                    break;
                case 2:
                    String msgStr=getResources().getString(R.string.copyfile);
                    txtTitle.setText(String.format(msgStr,msg.arg1,msg.arg2));
                    progressBar.setProgress(msg.arg1);
                    break;
                case 3:
                    txtTitle.setText(getResources().getString(R.string.scan_finish));
                    break;
                case 4:
                    progressBar.setMax(msg.arg1);
                    break;
            }
        }
    };


    private void scanAudio(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<StorageInfo> roots = SDCardUtils.listAvaliableStorage(mContext);
                LogCus.msg("挂载点：size:"+roots.size());
                ArrayList<File> listFile=new ArrayList<>();
                for (StorageInfo info:roots){
                    if (info.isRemoveable)
                        listFile.addAll(SDCardUtils.getAudioList(info.path));
                }
                Message msg=mHandler.obtainMessage();
                msg.what=4;
                msg.arg1=listFile.size();
                mHandler.sendMessage(msg);
                if (listFile.size()>0){
                    try {
                        File dirMusic=SDCardUtils.getDirFile(Enum_Dir.DIR_file);
                        for (int i=0;i<listFile.size();i++){
                            msg=mHandler.obtainMessage();
                            msg.what=2;
                            msg.arg1=1+i;
                            msg.arg2=listFile.size();
                            mHandler.sendMessage(msg);

                            String name=listFile.get(i).getPath();
                            name=name.substring(name.lastIndexOf("/")+1);
                            File destFile = new File(dirMusic, name);
                            LogCus.msg("音频文件:destFile:"+destFile.getPath());
                            copyFile(listFile.get(i).getAbsolutePath(),destFile.getAbsolutePath());
//                            FileUtils.copyFile(listFile.get(i).getAbsolutePath(),destFile.getAbsolutePath());
                        }
                        mHandler.sendEmptyMessage(3);
                    } catch (CustomExce customExce) {
                        customExce.printStackTrace();
                    }

                }else {
                    mHandler.sendEmptyMessage(1);
                }

            }
        }).start();
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024*4];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }
}
