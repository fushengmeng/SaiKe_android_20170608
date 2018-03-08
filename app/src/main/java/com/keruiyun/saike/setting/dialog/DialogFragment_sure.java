package com.keruiyun.saike.setting.dialog;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.keruiyun.saike.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/18.
 *
 */

@SuppressLint("ValidFragment")
public class DialogFragment_sure extends BaseSettingDialog {


    @BindView(R.id.txt_sure)
    TextView txtSure;
    @BindView(R.id.txt_cancle)
    TextView txtCancle;

    int type;

    /**
     * @param type  1---恢复出厂设置
     *              2---系统重启
     *              3---系统退出
     * */
    public DialogFragment_sure(int type ) {
        this.type = type;
    }

    @Override
    public int loadContentView() {
        return R.layout.layout_sure_cancle;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        switch (type){
            case 1:
                setTxtTitle(R.string.setting_factory_reset);

                break;
            case 2:
                setTxtTitle(R.string.setting_system_reset);
                break;
            case 3:
                setTxtTitle(R.string.setting_system_quit);
                break;
        }


    }

    @Override
    public void initTopView(View topView) {
        super.initTopView(topView);
        topView.setLayoutParams(new LinearLayout.LayoutParams(400, LinearLayout.LayoutParams.WRAP_CONTENT));
    }




    @OnClick({R.id.txt_sure, R.id.txt_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_sure:
                switch (type){
                    case 1:
                        break;
                    case 2:
                        System.exit(0);
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
                break;
            case R.id.txt_cancle:
                dismiss();
                break;
        }
    }
}
