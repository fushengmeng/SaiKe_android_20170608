package com.keruiyun.saike.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.keruiyun.saike.util.LogCus;

/**
 * Created by Administrator on 2018/3/14.
 */

public class ListScrollView extends ScrollView {
    public ListScrollView(Context context) {
        super(context);
    }

    public ListScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        LogCus.msg("scrollX:"+scrollX+":scrollY:"+scrollY);
        if (onScrolledY!=null)
            onScrolledY.onScrollY(scrollY);
    }

    OnScrolledY onScrolledY;

    public void setOnScrolledY(OnScrolledY onScrolledY) {
        this.onScrolledY = onScrolledY;
    }

    public interface OnScrolledY{
        public void onScrollY(int scrollY);
    }

}
