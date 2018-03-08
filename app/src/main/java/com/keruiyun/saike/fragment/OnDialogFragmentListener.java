package com.keruiyun.saike.fragment;

import android.content.Intent;

public interface OnDialogFragmentListener {
        public void onDialogFragmentRefreshThemeListener();

        public void onActivityResultListener(int requestCode, int resultCode, Intent data) ;


    }