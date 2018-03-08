package com.keruiyun.saike.setting;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keruiyun.saike.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/9.
 * 设置----关于
 */

public class ViewHolderAbout extends BaseViewHolder {

    @BindView(R.id.txt_dev_number)
    TextView txtDevNumber;
    @BindView(R.id.txt_system_version)
    TextView txtSystemVersion;
    @BindView(R.id.txt_system_version_update)
    TextView txtSystemVersionUpdate;
    @BindView(R.id.txt_app_version)
    TextView txtAppVersion;
    @BindView(R.id.txt_app_version_update)
    TextView txtAppVersionUpdate;
    @BindView(R.id.txt_clause)
    TextView txtClause;

    public ViewHolderAbout(Context context, ViewGroup viewParent, OnSettingListener onSettingListener) {
        super(context, viewParent, onSettingListener);
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_setting_about;
    }

    @Override
    public void initView(Context context, ViewGroup viewParent) {
        super.initView(context, viewParent);
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        txtDevNumber.setText(androidId+"");
        txtSystemVersion.setText( Build.ID);
        txtAppVersion.setText(getAppVersionName());
    }

    @OnClick({R.id.txt_system_version, R.id.txt_system_version_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_system_version:
                break;
            case R.id.txt_system_version_update:
                break;
        }
    }


    /**
     * 返回当前程序版本名
     */
    public String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

}
