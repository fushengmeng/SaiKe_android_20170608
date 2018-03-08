package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----账户
 */

public class ViewHolderUser extends BaseViewHolder {

    @BindView(R.id.txt_login)
    TextView txtLogin;
    @BindView(R.id.txt_register)
    TextView txtRegister;
    @BindView(R.id.layout_container)
    FrameLayout layoutContainer;
    BaseViewHolder[] baseViewHolders=new BaseViewHolder[2];

    public ViewHolderUser(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
        selectLoginLabel(true);
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_user;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);


    }

    @Override
    public void onActivityResultViewHolder(int requestCode, int resultCode, Intent data) {
        super.onActivityResultViewHolder(requestCode, resultCode, data);
        if (baseViewHolders[1]!=null)
            baseViewHolders[1].onActivityResultViewHolder(requestCode, resultCode, data);
    }

    @OnClick({R.id.txt_login, R.id.txt_register,R.id.layout_login, R.id.layout_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_login:
            case R.id.txt_login:
                selectLoginLabel(true);
                break;
            case R.id.layout_register:
            case R.id.txt_register:
                selectLoginLabel(false);
                break;
        }
    }

    private void selectLoginLabel(boolean isSelect){
        if (isSelect){
            txtLogin.setSelected(true);
            txtRegister.setSelected(false);
            loadContainerView(0);
        }else {
            txtLogin.setSelected(false);
            txtRegister.setSelected(true);
            loadContainerView(1);
        }
    }

    private void loadContainerView(int position){
        if ( baseViewHolders[position]==null){

            baseViewHolders[position]=position==1?new ViewHolderRegister(context,layoutContainer,onSettingListener):new ViewHolderLogin(context,layoutContainer,onSettingListener);
        }else {
            baseViewHolders[position].refresh();
        }

    }
}
