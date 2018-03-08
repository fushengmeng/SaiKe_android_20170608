package com.keruiyun.saike.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keruiyun.saike.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 *
 */

@SuppressLint("ValidFragment")
public class DialogFragment_ValidSure extends BaseDialogFragment {


    Unbinder unbinder;

    @SuppressLint("ValidFragment")
    public DialogFragment_ValidSure(OnValidSureListener onValidSureListener) {
        this.onValidSureListener = onValidSureListener;
    }

    @Override
    public int loadContentView() {
        return R.layout.fragment_validsure;
    }

   /* @Override
    public int loadTintImage() {
        return R.drawable.bg_validsure;
    }
*/
    @Override
    public void initView(View view) {
        super.initView(view);

    }


    @OnClick({R.id.txt_sure, R.id.txt_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_sure:
                if (onValidSureListener!=null)
                    onValidSureListener.onValidSure(true);
                dismiss();
                break;
            case R.id.txt_cancle:
                if (onValidSureListener!=null)
                    onValidSureListener.onValidSure(false);
                dismiss();
                break;
        }
    }

    OnValidSureListener onValidSureListener;

    public interface OnValidSureListener{
        public void  onValidSure(boolean isEnable);
    }
}
