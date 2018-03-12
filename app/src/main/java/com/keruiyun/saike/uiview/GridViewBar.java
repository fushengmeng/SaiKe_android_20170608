package com.keruiyun.saike.uiview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.keruiyun.saike.util.LogCus;

/**
 * Created by Administrator on 2018/3/12.
 */

public class GridViewBar extends GridView {

    int childHeight;
    int y;
    int viewHeight;

    public GridViewBar(Context context) {
        super(context);

    }

    public GridViewBar(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GridViewBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }




    @Override
    public void requestLayout() {
        super.requestLayout();
        if (getChildCount()>0){
            View c = getChildAt(0);
            childHeight=c.getHeight();
            if (getNumColumns()>0){
                viewHeight=getCount()/getNumColumns()*childHeight;
            }

            LogCus.msg("getCount:"+getCount()+":getChildCount:"+getChildCount()+":NumColumns:"+getNumColumns()
                    +":child_height:"+c.getHeight()+":getScrollY:"+getScrollY()+":viewHeight:"+viewHeight+
                    ":LastVisiblePosition:"+getLastVisiblePosition()+":FirstVisiblePosition:"+getFirstVisiblePosition());
        }

    }
    private SaikeScollBar saikeScollBar;

    public void setOnGridViewBarListener(SaikeScollBar saikeScollBar) {
        this.saikeScollBar = saikeScollBar;
        saikeScollBar.setOnSeekBarChangeListener(new SaikeScollBar.OnSeekBarChangeListener() {
            @Override
            public void onSeekUp() {
                if (y>=childHeight){
                    y-=childHeight;
                }else {
                    y=0;
                }
                scrollBy(0,y);
            }

            @Override
            public void onSeekDown() {
                isToBottom();
                LogCus.msg("滚动条：y"+y+":"+getScrollY());
                if (y<=viewHeight-childHeight-childHeight){
                    y+=childHeight;
                }else {
                    y=viewHeight-childHeight;
                }
                scrollBy(0,y);
            }

            @Override
            public void onSeekTop() {
                scrollTo(getScrollX(),0);
            }

            @Override
            public void onSeekBottom() {

            }

            @Override
            public void onProgressChanged(int offsetY) {
                LogCus.msg("滚动条："+offsetY+":"+getScrollY());
                scrollBy(getScrollX(),y+offsetY);
            }
        });
    }

    private boolean isToBottom(){

        return true;
    };

}
