package com.keruiyun.saike.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.keruiyun.saike.util.LogCus;

public abstract class BasePopupWindow extends PopupWindow {

    private Context mContext;

    private Drawable mBackgroundDrawable;
    private View view;

    public BasePopupWindow(Context context) {
        this.mContext = context;
        view=LayoutInflater.from(context).inflate(loadContentView(),null);
        setContentView(view);
        initBasePopupWindow();
        initView();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if(touchable) {
            if(mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        setOutsideTouchable(isOutsideTouchable());
    }

    /**
     * 初始化BasePopupWindow的一些信息
     * */
    private void initBasePopupWindow() {
        setAnimationStyle(android.R.style.Animation_Dialog);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
    }

    @Override
    public void setContentView(View contentView) {
        if(contentView != null) {
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            super.setContentView(contentView);
            addKeyListener(contentView);
        }
    }

    public abstract int loadContentView();

    public void initView(){};

    public <T extends View>T findById(int id){
        return (T)view.findViewById(id);
    };

    public Context getContext() {
        return mContext;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);

    }


    public void showAsRightCenter(View anchor,int left,int top) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        showAtLocation(anchor, Gravity.NO_GRAVITY
                ,location[0]+anchor.getWidth()-left
                ,anchor.getHeight()+location[1]-(getContentView().getMeasuredHeight()/2)-5-top);

    }
    public void showAsTopCenter(View anchor) {
        showAsTopCenter(anchor,0,0);
    }
    public void showAsTopCenter(View anchor,int left,int top) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        LogCus.msg("anchor:"+location[0]+":"+location[1]);
        LogCus.msg("pop:"+getContentView().getMeasuredWidth()+":"+getContentView().getMeasuredHeight());
        showAtLocation(anchor, Gravity.NO_GRAVITY
                ,location[0]-left
                ,location[1]-getContentView().getMeasuredHeight()-10-top);


    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);

    }

    @Override
    public void dismiss() {
        super.dismiss();

    }



    /**
     * 为窗体添加outside点击事件
     * */
    private void addKeyListener(View contentView) {
        if(contentView != null) {
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View view, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            dismiss();
                            return true;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 控制窗口背景的不透明度
     * */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity)getContext()).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}

