package com.keruiyun.saike.setting.dialog;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.keruiyun.saike.R;
import com.keruiyun.saike.setting.util.TimeZoneSetting;
import com.keruiyun.upload.SelectPicPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/18.
 */

public class DialogFragment_PicSelect extends BaseSettingDialog {


    @BindView(R.id.btn_take_photo)
    Button btn_take_photo;
    @BindView(R.id.btn_pick_photo)
    Button btn_pick_photo;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;

   PicPopupListener picPopupListene;

    public void setPicPopupListene(PicPopupListener picPopupListene) {
        this.picPopupListene = picPopupListene;
    }

    @Override
    public int loadContentView() {
        return R.layout.pop_carmer;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setTxtTitle(R.string.select_pic);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        btn_pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (picPopupListene!=null){
                    picPopupListene.pickPhone((Button) v);
                }
                dismiss();
            }
        });
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (picPopupListene!=null){
                    picPopupListene.takePhone((Button) v);
                }
                dismiss();

            }
        });
    }

    @Override
    public void initTopView(View topView) {
        super.initTopView(topView);
        topView.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT));
    }


    public interface PicPopupListener{
        /**拍照*/
        public void takePhone(Button v);
        /**相册*/
        public void pickPhone(Button v);
    }
}
