package com.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/3.
 */

public class ToastUtil {
    private static Context mContext;

    public static void init(Context mContext) {
        ToastUtil.mContext = mContext;
    }


    public static void showToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }
}
