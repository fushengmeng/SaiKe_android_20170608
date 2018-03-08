package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keruiyun.saike.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseViewHolder {
    View view;
    Context context;
    ViewGroup viewParent;
    OnSettingListener onSettingListener;


    public BaseViewHolder(Context context, ViewGroup viewParent,OnSettingListener onSettingListener) {
        this.context=context;
        this.viewParent=viewParent;
        this.onSettingListener=onSettingListener;
        onSettingListener.onAuthVaild(isAuthValid());
        onCreate();
        refresh();
    }

    public abstract int loadContentView();

    /*
    *是否判断有效期
    * */
    public boolean isAuthValid(){
        return true;
    }

    public void onCreate(){}

    public void onDestory(){}

    public void onActivityResultViewHolder(int requestCode, int resultCode, Intent data){}

    public void refresh(){
        viewParent.removeAllViews();
        if (view==null){
            view= LayoutInflater.from(context).inflate(loadContentView(),viewParent,false);
            ButterKnife.bind(this, view);
        }
        viewParent.addView(view);
        onSettingListener.onAuthVaild(isAuthValid());
        initView(context,viewParent);

    }

    public void initView(Context context, ViewGroup viewParent){

    }

}