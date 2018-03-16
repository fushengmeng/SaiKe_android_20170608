package com.keruiyun.saike.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2017/12/26.
 */

public class DrawableUtil {
    Context mContext;
    View view;
    int theme,gradientRadius;
    int radius;

    public DrawableUtil(Context mContext) {
        this.mContext = mContext;
        gradientRadius=72;
        radius=5;
        theme= ThemeUtils.replaceColorById(mContext,R.color.theme_color_primary);

    }

    public DrawableUtil setGradientRadius(int gradientRadius) {
        this.gradientRadius = gradientRadius;
        return this;
    }

    public DrawableUtil setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    //    对应drawable 中的selector
    private StateListDrawable getStateListDrawable(int radius,boolean isOval,boolean hasStorke,boolean hasSolid,boolean isGradient){
        StateListDrawable stateListDrawable=new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed},getGradientDrawable(radius,isOval,true,hasStorke,hasSolid,isGradient));
        stateListDrawable.addState(new int[]{android.R.attr.state_selected},getGradientDrawable(radius,isOval,true,hasStorke,hasSolid,isGradient));
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed},getGradientDrawable(radius,isOval,false,hasStorke,hasSolid,isGradient));
        stateListDrawable.addState(new int[]{},getGradientDrawable(radius,isOval,false,hasStorke,hasSolid,isGradient));
        return stateListDrawable;
    }

    //    对应drawable 中的selector

    public StateListDrawable getStateListDrawable(View view,int radius,boolean isOval,boolean hasStorke,boolean hasSolid){
        this.view = view;
        return getStateListDrawable(radius,isOval,hasStorke,hasSolid,false);
    }

    /**
     * 设置按钮
     */
    public StateListDrawable getDrawableSetting(View view){
        this.view = view;
        this.gradientRadius = 40;
        return getStateListDrawable(2,false,false,true,true);
    }


    public StateListDrawable getStateListDrawable(View view){
        this.view = view;
        return getStateListDrawable(radius,false,false,true,false);
    }



    //    对应drawable 中的selector
    public StateListDrawable getStateListDrawableOval(View view){
        this.view = view;
        return getStateListDrawable(radius,true,false,true,false);
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

    public GradientDrawable getDrawable(View view){
        this.view = view;
        return getGradientDrawable(radius,false,true,true,true,false);
    }
    public GradientDrawable getDrawable(View view,boolean isPressed){
        this.view = view;
        return getGradientDrawable(radius,false,isPressed,true,true,false);
    }

    //    对应drawable 中的 shape
    private GradientDrawable getGradientDrawable(int radius,
                                                 boolean isOval,boolean isPressed,
                                                 boolean hasStorke,boolean hasSolid,boolean isGradient){
        GradientDrawable gradientDrawable=new GradientDrawable();
//        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        if (isOval)
            gradientDrawable.setShape(GradientDrawable.OVAL);
        else
            gradientDrawable.setCornerRadius(radius);



        // 获取颜色
//        TypedValue primaryValue=new TypedValue();
        TypedValue primaryDarkValue=new TypedValue();
//        mContext.getTheme().resolveAttribute(R.attr.custom_attr_color_primary,primaryValue,true);
        mContext.getTheme().resolveAttribute(R.attr.custom_attr_color_primaryDark,primaryDarkValue,true);


        if (hasStorke){
            if(isPressed){
                gradientDrawable.setStroke(1,theme);
            } else {
                gradientDrawable.setStroke(1,primaryDarkValue.data);
            }
        }

//        背景颜色
        if (hasSolid){
            if(isPressed){
                if (isGradient){
//                    gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
                    gradientDrawable.setGradientRadius(gradientRadius);
                    gradientDrawable.setColors(new int[]{theme,theme,primaryDarkValue.data});
                }else {
                    gradientDrawable.setColor(theme);
                }

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
