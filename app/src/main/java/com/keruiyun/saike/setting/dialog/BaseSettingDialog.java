package com.keruiyun.saike.setting.dialog;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintTextView;
import com.keruiyun.saike.R;
import com.keruiyun.saike.fragment.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/18.
 */

public abstract class BaseSettingDialog extends BaseDialogFragment {

    TextView txtTitle;

    @Override
    public int loadTintImage() {
        return R.drawable.bg_border;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        tintImageView.setBackgroundTintList(R.color.line, PorterDuff.Mode.DST);
    }

    @Override
    public void initTopView(View topView) {
        super.initTopView(topView);
        txtTitle= (TextView) topView.findViewById(R.id.txt_item);


    }

    @Override
    public int loadContentTopView() {
        return R.layout.layout_setting_title;
    }

    public void setTxtTitle(int resId) {
        this.txtTitle.setText(getResources().getString(resId));
    }



}
