package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.db.T_M_User;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.util.PreferencesUtil;
import com.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----登录
 */

public class ViewHolderLogin extends BaseViewHolder {

    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_psw)
    EditText inputPsw;
    @BindView(R.id.box_psw_remember)
    CheckBox boxPswRemember;
    @BindView(R.id.txt_psw_forget)
    TextView txtPswForget;
    @BindView(R.id.txt_login)
    TintTextView txtLogin;

    T_M_User t_m_user;

    public ViewHolderLogin(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public boolean isAuthValid() {
        return false;
    }


    @Override
    public int loadContentView() {
        return R.layout.layout_login;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (t_m_user==null)
            t_m_user=new T_M_User(context);
    }

    @Override
    public void onDestory() {
        super.onDestory();
        t_m_user.close();
        t_m_user=null;
    }

    @Override
    public void initView(Context contextv, ViewGroup viewParent) {
        super.initView(contextv, viewParent);
        viewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideSoftInput(v);
            }
        });
        boolean isRememberPsw = PreferencesUtil.getInstance(context).getBooleanValue("User", "isRememberPsw",false);
        boxPswRemember.setChecked(isRememberPsw);
        String login_user =  PreferencesUtil.getInstance(context).getStringValue("User", "login_user","");
        inputUsername.setText(login_user+"");
        if (isRememberPsw){
            String psw=t_m_user.getUserPsw(login_user);
            inputPsw.setText(psw+"");
        }
        boxPswRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtil.getInstance(context).setBooleanValue("User", "isRememberPsw",isChecked);
            }
        });

    }

    @OnClick({R.id.txt_psw_forget, R.id.txt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_psw_forget:
                break;
            case R.id.txt_login:
                login();
                break;
        }
    }

    private void login(){
        String user=inputUsername.getText().toString().trim();
        Resources res=context.getResources();
        if (TextUtils.isEmpty(user)){
            ToastUtil.showToast(res.getString(R.string.msg_user_null));
            return;
        }
        String psw=inputPsw.getText().toString().trim();
        if (TextUtils.isEmpty(psw)){
            ToastUtil.showToast(res.getString(R.string.msg_user_pswnull));
            return;
        }
        if (t_m_user.isSurePsw(user,psw)){
            ToastUtil.showToast(res.getString(R.string.login_success));
            onSettingListener.onLogin(user);

        }
    }
}
