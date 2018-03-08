package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.keruiyun.saike.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 退出系统
 */


public class DialogFragment_Sure_Quit extends BaseDialogFragment {

    @BindView(R.id.txt_msg)
    TextView txtMsg;


    @Override
    public int loadContentView() {
        return R.layout.fragment_validsure;
    }

   /* @Override
    public int loadTintImage() {
        return R.drawable.bg_validsure;
    }*/

    @Override
    public void initView(View view) {
        super.initView(view);
        txtMsg.setText(getResources().getString(R.string.msg_quit_system));
    }


    @OnClick({R.id.txt_sure, R.id.txt_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_sure:
                System.exit(0);
                break;
            case R.id.txt_cancle:

                dismiss();
                break;
        }
    }


}
