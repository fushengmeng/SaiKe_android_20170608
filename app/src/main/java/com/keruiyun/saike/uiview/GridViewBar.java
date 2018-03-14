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

public class GridViewBar extends GridView implements ListScrollView.OnScrolledY {

    int childHeight;
    int y=8;
    int viewHeight;
    float availableScrollHeight;


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
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    /**
     * downY：手指按下时距离View顶部的距离
     * moveY：手指在屏幕上滑动的距离（不停变化）     *
     */
    private int downY,moveY;



    /*@Override
    public boolean onTouchEvent(MotionEvent event) {

        LogCus.msg("getAction:"+event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指按下时距离View上面的距离
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) event.getY();
                //deY是滑动的距离，向上滑时deY<0 ，向下滑时deY>0
                float deY =moveY-downY;

                y= (int) (y-deY);
                LogCus.msg("y:"+y+":deY:"+deY+":moveY:"+moveY+":downY:"+downY);
                if (y<0)
                    y=0;
                if (availableScrollHeight!=0&&y>availableScrollHeight)
                    y= (int) availableScrollHeight;

                 scrollTo(0,y);

                break;

        }
        return super.onTouchEvent(event);
    }*/



    @Override
    public void requestLayout() {
        super.requestLayout();
        if (getChildCount()>0){
            View c = getChildAt(0);
            childHeight=c.getHeight();

            if (getNumColumns()>0){
                int row;
                if (getCount()%getNumColumns()==0)
                    row=getCount()/getNumColumns();
                else
                    row=getCount()/getNumColumns()+1;
                viewHeight=(childHeight+8)*row;


                if (row>6){
                    availableScrollHeight=(childHeight+8)*(row-6);
                }else {
                    availableScrollHeight=0;
                }


            }
            if (saikeScollBar!=null)
                saikeScollBar.setStep(getBarStep());
            LogCus.msg("getCount:"+getCount()+":getChildCount:"+getChildCount()+":NumColumns:"+getNumColumns()
                    +":getHeight:"+getHeight()+":viewHeight:"+viewHeight+":child_height:"+c.getHeight()+":getScrollY:"+getScrollY()
                    +":LastVisiblePosition:"+getLastVisiblePosition()+":FirstVisiblePosition:"+getFirstVisiblePosition());
        }

    }
    private SaikeScollBar saikeScollBar;


    public void setOnGridViewBarListener(SaikeScollBar saikeScollBar) {
        this.saikeScollBar = saikeScollBar;
        saikeScollBar.setStep(getBarStep());
        saikeScollBar.setOnSeekBarChangeListener(new SaikeScollBar.OnSeekBarChangeListener() {
            @Override
            public void onSeekUp() {
                if (y<=childHeight+8)
                    y=0;
                else
                    y=y-childHeight-8;
                scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekDown() {
                if (y>availableScrollHeight)
                    y= (int) availableScrollHeight;
                else
                    y=y+childHeight+8;
                scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekTop() {
                y=0;

                scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekBottom() {
                y=(int) availableScrollHeight;
                scrollTo(getScrollX(), y);

            }

            @Override
            public void onProgressChanged(float scale) {
                y= (int) (scale*availableScrollHeight);
                scrollTo(getScrollX(),y);

            }
        });
    }

    private float getBarStep(){
        float step=0;
        if (saikeScollBar!=null&&availableScrollHeight!=0){
            step= (childHeight+8)/availableScrollHeight;
        }
        return step;
    }

    @Override
    public void onScrollY(int scrollY) {
        float scale = scrollY / availableScrollHeight;
        if (saikeScollBar!=null){
            saikeScollBar.scale=scale;
            saikeScollBar.invalidate();
        }

    }
}
