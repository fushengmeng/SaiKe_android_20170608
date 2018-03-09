package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.db.T_M_User;
import com.keruiyun.db.User;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.saike.timerserver.ServiceFiveTimer;
import com.keruiyun.saike.util.PreferencesUtil;
import com.keruiyun.upload.SelectPicTools;
import com.util.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----注册
 */

public class ViewHolderRegister extends BaseViewHolder {

    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.container_register)
    FrameLayout containerRegister;
    LayoutInflater inflater;

    SelectPicTools selectPicTools;

    T_M_User t_m_user;
    User user;
    private String pathImg;

    public ViewHolderRegister(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }
    @Override
    public boolean isAuthValid() {
        return false;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (t_m_user == null)
            t_m_user = new T_M_User(context);
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_register;
    }

    @Override
    public void onActivityResultViewHolder(int requestCode, int resultCode, Intent data) {
        super.onActivityResultViewHolder(requestCode, resultCode, data);
        selectPicTools.photoSelectActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestory() {
        super.onDestory();
        t_m_user.close();
        t_m_user = null;

        if (pathImg != null) {
            File file = new File(pathImg);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        viewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseActivity.hideSoftInput(v);
            }
        });

        inflater = LayoutInflater.from(context);
        loadRegisterView(R.layout.item_register);

        selectPicTools = new SelectPicTools(context) {
            @Override
            public void selectImgResult(String path) {
                super.selectImgResult(path);
                pathImg = path;
                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                if (user != null)
                    user.setUserPortrait(bitmap);
                imgProfile.setImageBitmap(bitmap);
            }
        };


    }

    protected void loadRegisterView(int resource) {
        View view = inflater.inflate(resource, null);
        containerRegister.removeAllViews();
        containerRegister.addView(view);
        switch (resource){
            case R.layout.item_register:
                new ViewHolderItemRegister(view);
                break;
            case R.layout.item_user_info:
                new ViewHolderUserInfo(view);
                break;
            case R.layout.item_user_modify:
                new ViewHolderUserModify(view);
                break;
        }

    }

    /**
     * 注册
     */
    class ViewHolderItemRegister {
        @BindView(R.id.input_username)
        EditText inputUsername;
        @BindView(R.id.input_user_psw)
        EditText inputUserPsw;
        @BindView(R.id.input_user_pswsure)
        EditText inputUserPswsure;
        @BindView(R.id.box_admin)
        RadioButton boxAdmin;
        @BindView(R.id.box_operator)
        RadioButton boxOperator;
        @BindView(R.id.box_superadmin)
        RadioButton boxSuperadmin;
        @BindView(R.id.group_usertype)
        RadioGroup groupUsertype;
        @BindView(R.id.txt_register)
        TintTextView txtRegister;

        View.OnClickListener onClickListener;

        ViewHolderItemRegister(View view) {
            ButterKnife.bind(this, view);
            user = new User();
            if (pathImg != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                user.setUserPortrait(bitmap);
            }
            initUi();
        }

        void initUi() {
            groupUsertype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.box_superadmin:
                            user.setType(2);
                            break;
                        case R.id.box_admin:
                            user.setType(1);
                            break;
                        case R.id.box_operator:
                            user.setType(0);
                            break;
                    }
                }
            });

            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.img_profile:
                            selectPicTools.loadImg(imgProfile);
                            break;
                        case R.id.txt_register:
                            register();
                            break;
                    }
                }
            };
            imgProfile.setOnClickListener(onClickListener);
            txtRegister.setOnClickListener(onClickListener);
        }

        private void register() {
            String userStr = inputUsername.getText().toString().trim();
            Resources res = context.getResources();
            if (TextUtils.isEmpty(userStr)) {
                ToastUtil.showToast(res.getString(R.string.msg_user_null));
                return;
            } else {
                user.setUser(userStr);
            }
            String psw = inputUserPsw.getText().toString().trim();
            if (TextUtils.isEmpty(psw)) {
                ToastUtil.showToast(res.getString(R.string.msg_user_pswnull));
                return;
            }
            String pswSure = inputUserPswsure.getText().toString().trim();
            if (TextUtils.isEmpty(psw)) {
                ToastUtil.showToast(res.getString(R.string.msg_user_pswsurenull));
                return;
            }
            if (!psw.equals(pswSure)) {
                ToastUtil.showToast(res.getString(R.string.msg_user_pswwrong));
                return;
            } else {
                BaseActivity.hideSoftInput(inputUserPswsure);
                user.setPsw(pswSure);
            }
            boolean isReg = t_m_user.insertUser(user);
            if (isReg){
                onSettingListener.onLogout();
            }
        }
    }



    class ViewHolderUserInfo {
        @BindView(R.id.txt_username)
        TextView txtUsername;
        @BindView(R.id.txt_login_time)
        TextView txtLoginTime;
        @BindView(R.id.txt_modify)
        TintTextView txtModify;
        @BindView(R.id.txt_logout)
        TintTextView txtLogout;

        ViewHolderUserInfo(View view) {
            ButterKnife.bind(this, view);
            initUi();
        }
        void initUi(){
            String user= PreferencesUtil.getInstance(context).getStringValue("User", "login_user","");
            txtUsername.setText(user+"");
            long loginTime = PreferencesUtil.getInstance(context).getLongValue("User", "login_user_time",System.currentTimeMillis());
            long time=(System.currentTimeMillis()-loginTime)/1000;
            int minutes= ((int) (time/60))+1;
            String formatStr=context.getResources().getString(R.string.logintime);
            txtLoginTime.setText(String.format(formatStr,minutes)+"");

            Bitmap bitmap =t_m_user.getPortrait(user);
            if (bitmap != null)
                imgProfile.setImageBitmap(bitmap);

            if (minutes>5){
                PreferencesUtil.getInstance(context).setBooleanValue("User", "login",false);
                context.sendBroadcast(new Intent(ServiceFiveTimer.ACTION_FiveTimer));
            }

            txtModify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSettingListener.toModifyPsw();
                }
            });
            txtLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BaseActivity.hideSoftInput(txtLogout);
                    onSettingListener.onLogout();
                }
            });
        }
    }

    class ViewHolderUserModify {
        @BindView(R.id.input_user_oldpsw)
        EditText inputUserOldpsw;
        @BindView(R.id.input_user_newpsw)
        EditText inputUserNewpsw;
        @BindView(R.id.input_user_newpswsure)
        EditText inputUserNewpswsure;
        @BindView(R.id.txt_sure)
        TintTextView txtSure;

        ViewHolderUserModify(View view) {
            ButterKnife.bind(this, view);
            txtSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyPsw();
                }
            });
        }

        void modifyPsw(){
            String user= PreferencesUtil.getInstance(context).getStringValue("User", "login_user","");
            String oldNew=t_m_user.getUserPsw(user);
            String psw = inputUserOldpsw.getText().toString().trim();

            Resources res = context.getResources();
            if (TextUtils.isEmpty(psw)) {
                ToastUtil.showToast(res.getString(R.string.err_oldpsw_null));
                return;
            }else if (!oldNew.equals(psw)){
                ToastUtil.showToast(res.getString(R.string.err_oldpsw));
                return;
            }
            String newpsw = inputUserNewpsw.getText().toString().trim();
            if (TextUtils.isEmpty(newpsw)) {
                ToastUtil.showToast(res.getString(R.string.err_newpsw_null));
                return;
            }

            String pswSure = inputUserNewpswsure.getText().toString().trim();
            if (TextUtils.isEmpty(pswSure)) {
                ToastUtil.showToast(res.getString(R.string.err_newpswsure_null));
                return;
            }
            if (!newpsw.equals(pswSure)) {
                ToastUtil.showToast(res.getString(R.string.err_newpswsure));
                return;
            }
            boolean success=t_m_user.modifyUserPsw(user,pswSure);
            if (success){
                BaseActivity.hideSoftInput(txtSure);
                ToastUtil.showToast(res.getString(R.string.psw_modify_success));
                onSettingListener.onModifyPsw(user);
            }else {
                ToastUtil.showToast(res.getString(R.string.psw_modify_failed));
            }
        }

    }
}
