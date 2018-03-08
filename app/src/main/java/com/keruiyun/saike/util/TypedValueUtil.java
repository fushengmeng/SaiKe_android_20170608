package com.keruiyun.saike.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2017/12/28.
 */

public class TypedValueUtil {

    public static int getColorValue(Context mContext,int resid){

        TypedArray array =getTypedArray(mContext,resid);
        int value = array.getColor(0 /* index */, -1 /* default size */);
        array.recycle();
        return value;

    }
    public static TypedArray getTypedArray(Context mContext,int resid){
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(resid, typedValue, true);
        int[] attribute = new int[] { resid };
        return mContext.obtainStyledAttributes(typedValue.resourceId, attribute);
//        int textSize = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
//        array.recycle();


    }
}
