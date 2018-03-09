package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.keruiyun.db.T_M_User;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;

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
    @BindView(R.id.layout_register)
    View layoutRegister;
    @BindView(R.id.layout_container)
    FrameLayout layoutContainer;
    BaseViewHolder[] baseViewHolders;
    T_M_User t_m_user;

    public ViewHolderUser(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);

    }

    @Override
    public boolean isAuthValid() {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseViewHolders=new BaseViewHolder[2];
        t_m_user=new T_M_User(context);
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
                loadContainerView(0);
                break;
            case R.id.layout_register:
            case R.id.txt_register:
                loadContainerView(1);
                break;
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        boolean isLogin = PreferencesUtil.getInstance(context).getBooleanValue("User", "login",false);
        if (isLogin){
            String user=PreferencesUtil.getInstance(context).getStringValue("User", "login_user","");
            int type=t_m_user.getUserType(user);
            LogCus.msg("账户类型："+user+":"+type);
            if (type>0){
                layoutRegister.setVisibility(View.VISIBLE);
                txtRegister.setVisibility(View.VISIBLE);
            }else {
                layoutRegister.setVisibility(View.GONE);
                txtRegister.setVisibility(View.GONE);
            }
            txtLogin.setSelected(true);
            txtRegister.setSelected(false);
            refreshRegisterView(R.layout.item_user_info);
        }else {
            txtLogin.setText(context.getResources().getString(R.string.login));
            txtLogin.setSelected(true);
            txtRegister.setSelected(false);
            loadContainerView(0);
            layoutRegister.setVisibility(View.GONE);
            txtRegister.setVisibility(View.GONE);
        }

    }



    private void loadContainerView(int position){
        boolean isLogin = PreferencesUtil.getInstance(context).getBooleanValue("User", "login",false);
        if (position==1){
            txtLogin.setSelected(false);
            txtRegister.setSelected(true);
            if (!isLogin){
                txtLogin.setText(context.getResources().getString(R.string.login));
            }

            baseViewHolders[1]=new ViewHolderRegister(context,layoutContainer,onSettingListener);
        }else {
            txtLogin.setSelected(true);
            txtRegister.setSelected(false);

            if (isLogin){
                refreshRegisterView(R.layout.item_user_info);
            }else {
                baseViewHolders[0]=new ViewHolderLogin(context,layoutContainer,onSettingListener);
            }



        }
    }

    public void refreshRegisterView(int resource){
        switch (resource){
            case R.layout.item_register:
                txtLogin.setText(context.getResources().getString(R.string.register));
                break;
            case R.layout.item_user_info:
                txtLogin.setText(context.getResources().getString(R.string.user_info));
                break;
            case R.layout.item_user_modify:
                txtLogin.setText(context.getResources().getString(R.string.modify_psw));
                break;
        }
        baseViewHolders[1]=new ViewHolderRegister(context,layoutContainer,onSettingListener);
        ViewHolderRegister viewHolderRegister= (ViewHolderRegister) baseViewHolders[1];
        viewHolderRegister.loadRegisterView(resource);
    }

}
