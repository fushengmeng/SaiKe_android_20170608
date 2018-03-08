package com.keruiyun.saike.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;

import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2017/12/26.
 */

public class DrawableUtil {
    Activity mContext;
    View view;

    public DrawableUtil(Activity mContext) {
        this.mContext = mContext;

    }

    //    对应drawable 中的selector
    private StateListDrawable getStateListDrawable(int radius,boolean isOval,boolean hasStorke,boolean hasSolid){
        StateListDrawable stateListDrawable=new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},getGradientDrawable(radius,isOval,true,hasStorke,hasSolid));
        stateListDrawable.addState(new int[]{android.R.attr.state_selected},getGradientDrawable(radius,isOval,true,hasStorke,hasSolid));
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed},getGradientDrawable(radius,isOval,false,hasStorke,hasSolid));
        stateListDrawable.addState(new int[]{},getGradientDrawable(radius,isOval,false,hasStorke,hasSolid));
        return stateListDrawable;
    }

    //    对应drawable 中的selector

    public StateListDrawable getStateListDrawable(View view,int radius,boolean isOval,boolean hasStorke,boolean hasSolid){
        this.view = view;
        return getStateListDrawable(radius,isOval,hasStorke,hasSolid);
    }


    public StateListDrawable getStateListDrawable(View view){
        this.view = view;
        return getStateListDrawable(5,false,false,true);
    }

    //    对应drawable 中的selector
    public StateListDrawable getStateListDrawableOval(View view){
        this.view = view;
        return getStateListDrawable(5,true,false,true);
    }


    public StateListDrawable addStateListBgDrawable(int idNormal, int idPressed) {
        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[] { android.R.attr.state_selected }, mContext
                .getResources().getDrawable(idPressed));
        drawable.addState(new int[] { android.R.attr.state_pressed }, mContext
                .getResources().getDrawable(idPressed));
        drawable.addState(new int[] { android.R.attr.state_enabled }, mContext
                .getResources().getDrawable(idNormal));
        drawable.addState(new int[] {},
                mContext.getResources().getDrawable(idNormal));
        return drawable;
    }

    //    对应drawable 中的 shape
    private GradientDrawable getGradientDrawable(int radius,boolean isOval,boolean isPressed,boolean hasStorke,boolean hasSolid){
        GradientDrawable gradientDrawable=new GradientDrawable();
//        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        if (isOval)
            gradientDrawable.setShape(GradientDrawable.OVAL);
        else
            gradientDrawable.setCornerRadius(radius);
        // 获取颜色
        TypedValue primaryValue=new TypedValue();
        TypedValue primaryDarkValue=new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.custom_attr_color_primary,primaryValue,true);
        mContext.getTheme().resolveAttribute(R.attr.custom_attr_color_primaryDark,primaryDarkValue,true);

        if (hasStorke){
            if(isPressed){
                gradientDrawable.setStroke(1,primaryValue.data);
            } else {
                gradientDrawable.setStroke(1,primaryDarkValue.data);
            }
        }

//        背景颜色
        if (hasSolid){
            if(isPressed){
                gradientDrawable.setColor(primaryValue.data);
            } else {
                gradientDrawable.setColor(primaryDarkValue.data);
//                gradientDrawable.setColor(primaryValue.data);
            }
        }else {
            gradientDrawable.setColor(Color.TRANSPARENT);
        }

        gradientDrawable.setBounds(0,0,view.getWidth(),view.getHeight());
        return gradientDrawable;
    }



}
