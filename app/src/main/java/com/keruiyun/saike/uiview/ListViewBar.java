package com.keruiyun.saike.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.keruiyun.saike.util.LogCus;

/**
 * Created by Administrator on 2018/3/15.
 */

public class ListViewBar extends ListView implements ListScrollView.OnScrolledY{

    int childHeight;
    int y=8;
    int viewHeight;
    float availableScrollHeight;

    public ListViewBar(Context context) {
        super(context);
    }

    public ListViewBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        if (getChildCount()>0){
            View c = getChildAt(0);
            childHeight=c.getHeight()+1;

            int row=getCount();

            viewHeight=childHeight*row;

            if (row>8){
                availableScrollHeight=childHeight*(row-8);
            }else {
                availableScrollHeight=0;
            }
            if (saikeScollBar!=null)
                saikeScollBar.setStep(getBarStep());
            LogCus.msg("getCount:"+getCount()+":getChildCount:"+getChildCount()
                    +":getHeight:"+getHeight()+":viewHeight:"+viewHeight+":child_height:"+c.getHeight()
                    +":LastVisiblePosition:"+getLastVisiblePosition()+":FirstVisiblePosition:"+getFirstVisiblePosition());
        }

    }
    private SaikeScollBar saikeScollBar;

    private ListScrollView listScrollView;
    public void setOnGridViewBarListener(SaikeScollBar saikeScollBar,ListScrollView listScrollViewT) {
        this.saikeScollBar = saikeScollBar;
        this.listScrollView = listScrollViewT;
        saikeScollBar.setStep(getBarStep());
        saikeScollBar.setOnSeekBarChangeListener(new SaikeScollBar.OnSeekBarChangeListener() {
            @Override
            public void onSeekUp() {
                if (y<=childHeight)
                    y=0;
                else
                    y=y-childHeight;
                listScrollView.scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekDown() {
                if (y>availableScrollHeight)
                    y= (int) availableScrollHeight;
                else
                    y=y+childHeight;
                listScrollView.scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekTop() {
                y=0;
                listScrollView. scrollTo(getScrollX(),y);

            }

            @Override
            public void onSeekBottom() {
                y=(int) availableScrollHeight;
                listScrollView.scrollTo(getScrollX(), y);

            }

            @Override
            public void onProgressChanged(float scale) {
                y= (int) (scale*availableScrollHeight);
                listScrollView.scrollTo(getScrollX(),y);

            }
        });
    }

    private float getBarStep(){
        float step=0;
        if (saikeScollBar!=null&&availableScrollHeight!=0){
            step= (childHeight)/availableScrollHeight;
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
