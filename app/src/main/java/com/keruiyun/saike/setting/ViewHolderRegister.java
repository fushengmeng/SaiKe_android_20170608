package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.db.T_M_User;
import com.keruiyun.db.User;
import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.R;
import com.keruiyun.upload.SelectPicTools;
import com.util.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----注册
 */

public class ViewHolderRegister extends BaseViewHolder {

    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.input_username)
    EditText inputUsername;
    @BindView(R.id.input_user_psw)
    EditText inputUserPsw;
    @BindView(R.id.input_user_pswsure)
    EditText inputUserPswsure;
    @BindView(R.id.group_usertype)
    RadioGroup radioGroupUserType;
    @BindView(R.id.box_admin)
    RadioButton boxAdmin;
    @BindView(R.id.box_operator)
    RadioButton boxOperator;
    @BindView(R.id.box_superadmin)
    RadioButton boxSuperadmin;
    @BindView(R.id.txt_register)
    TintTextView txtRegister;

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener;
    SelectPicTools selectPicTools;

    T_M_User t_m_user;

    User user;
    private String pathImg;

    public ViewHolderRegister(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (t_m_user==null)
            t_m_user=new T_M_User(context);
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
        t_m_user=null;

        if (pathImg!=null){
            File file=new File(pathImg);
            if (file.exists()){
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
        selectPicTools=new SelectPicTools(context){
            @Override
            public void selectImgResult(String path) {
                super.selectImgResult(path);
                pathImg=path;
                Bitmap bitmap= BitmapFactory.decodeFile(pathImg);
                user.setUserPortrait(bitmap);
                imgProfile.setImageBitmap(bitmap);
            }
        };
        user=new User();

        radioGroupUserType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
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

    }




    @OnClick({R.id.img_profile, R.id.txt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_profile:
                selectPicTools.loadImg(imgProfile);
                break;
            case R.id.txt_register:
                register();
                break;
        }
    }

    private void register(){
        String userStr=inputUsername.getText().toString().trim();
        Resources res=context.getResources();
        if (TextUtils.isEmpty(userStr)){
            ToastUtil.showToast(res.getString(R.string.msg_user_null));
            return;
        }else {
            user.setUser(userStr);
        }
        String psw=inputUserPsw.getText().toString().trim();
        if (TextUtils.isEmpty(psw)){
            ToastUtil.showToast(res.getString(R.string.msg_user_pswnull));
            return;
        }
        String pswSure=inputUserPswsure.getText().toString().trim();
        if (TextUtils.isEmpty(psw)){
            ToastUtil.showToast(res.getString(R.string.msg_user_pswsurenull));
            return;
        }
        if (!psw.equals(pswSure)){
            ToastUtil.showToast(res.getString(R.string.msg_user_pswwrong));
            return;
        }else {
            user.setPsw(pswSure);
        }
       boolean isReg = t_m_user.insertUser(user);

    }
}
