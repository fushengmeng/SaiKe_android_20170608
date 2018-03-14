package com.keruiyun.saike.uiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;


public class SaikeScollBar extends View {

    private int mWidth=18,mTopHeight=20, mHeight=100,mCorners=4;
    private int colorBg,colorBloder,colorTheme;

    RectF rectUp,rectDown,rectSeekArea,rectSeek;

    private int seekHeight=30,seekAreaHight;
    private float curSeekTop;

    private int max=100;
    float scale;
    private float step=0.1f;

    private Paint paint;

    private OnSeekBarChangeListener mSeekBarChangeListener;


    public SaikeScollBar(Context context) {
        this(context, null);
    }

    public SaikeScollBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SaikeScollBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        rectUp=new RectF();
        rectDown=new RectF();
        rectSeekArea=new RectF();
        rectSeek=new RectF();
        rectSeek.top=mWidth+1;

        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔

        paint.setAntiAlias(true);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setStrokeCap(Paint.Cap.ROUND);

        colorBg=ContextCompat.getColor(getContext(), R.color.line);
        colorBloder=ContextCompat.getColor(getContext(), R.color.white);
        colorTheme=ContextCompat.getColor(getContext(), R.color.theme_color_primary);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight=getDefaultSize(mHeight,heightMeasureSpec);
        setMeasuredDimension(mWidth,heightMeasureSpec);

    }

    /**
     * 获取默认的宽高值
     */
    public static int getDefaultSize (int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec. getMode(measureSpec);
        int specSize = MeasureSpec. getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec. UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec. AT_MOST:
            case MeasureSpec. EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight=getWidth();
        mHeight=getHeight();
//        LogCus.msg("onLayout---getWidth:"+mHeight+":getHeight:"+mHeight);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBloder(canvas);
        drawSeek(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取当前触摸的绝对坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (scale==0){
                    rectSeek.top=rectSeekArea.top;
                    if (mSeekBarChangeListener!=null){
                        mSeekBarChangeListener.onSeekTop();
                    }
                    invalidate();
                }
                if (scale==1){
                    rectSeek.top=rectSeekArea.bottom-seekHeight;
                    if (mSeekBarChangeListener!=null){
                        mSeekBarChangeListener.onSeekBottom();
                    }
                    invalidate();
                }

                int x= (int) event.getX(),y= (int) event.getY();
                boolean isUp=x>=rectUp.left&&x<=rectUp.right&&y>=rectUp.top&&y<=rectUp.bottom;
//
                if (isUp&&mSeekBarChangeListener!=null) {
                    float avail=availableHeight();
                    int add= (int) (step*avail);

                    rectSeek.top-=add;
                    if (rectSeek.top<=rectSeekArea.top){
                        rectSeek.top=rectSeekArea.top;
                        mSeekBarChangeListener.onSeekTop();
                    }else {
                        mSeekBarChangeListener.onSeekUp();
                    }

                    if (avail!=0)
                        scale=(rectSeek.top-rectSeekArea.top)/avail;
                    invalidate();

                }

                boolean isDown=x>=rectDown.left&&x<=rectDown.right&&y>=rectDown.top&&y<=rectDown.bottom;
//
                if (isDown&&mSeekBarChangeListener!=null) {
                    float avail=availableHeight();
                    int add= (int) (step*avail);

                    rectSeek.top+=add;
                    if (rectSeek.top>=rectSeekArea.bottom-seekHeight) {
                        rectSeek.top = rectSeekArea.bottom - seekHeight;
                        mSeekBarChangeListener.onSeekBottom();
                    }else {
                        mSeekBarChangeListener.onSeekDown();
                    }

                    if (avail!=0)
                        scale=(rectSeek.top-rectSeekArea.top)/avail;
                    invalidate();

                }



                break;
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                final float available = availableHeight();
                final int yPoint = (int) event.getY();
                if (yPoint<=rectSeekArea.top){
                    scale=0;
                }else if (yPoint>=rectSeekArea.bottom-rectSeek.bottom+rectSeek.top){
                    scale=1;
                }else {
                    scale = (yPoint - rectSeekArea.top) / available;
                }
                boolean isMove= rectSeek.top>=rectSeekArea.top&&rectSeek.bottom<=rectSeekArea.bottom;
//                LogCus.msg("y:"+event.getY()+":scale:"+scale);
                if (isMove&&mSeekBarChangeListener!=null){
                    if (curSeekTop>mTopHeight&&curSeekTop<mHeight-mTopHeight-seekHeight)
                        mSeekBarChangeListener.onProgressChanged(scale);
                }
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    public void setStep(float step) {

        if (step==0)
            return;
        this.step = step;

    }

    public float availableHeight(){
        return rectSeekArea.bottom-rectSeekArea.top-rectSeek.bottom+rectSeek.top;
    }

    private void drawSeek(Canvas canvas){
        float available = rectSeekArea.bottom - rectSeekArea.top - rectSeek.bottom + rectSeek.top;
        float top=scale*available+rectSeekArea.top;
        curSeekTop = top;
        if (top<mTopHeight)
            top=mTopHeight;
        if (top>mHeight-mTopHeight-seekHeight)
            top=mHeight-mTopHeight-seekHeight;
        int padding=2;
        //
        rectSeek.left=padding;
        rectSeek.top=top;
        rectSeek.right=mWidth-padding;
        rectSeek.bottom=seekHeight+rectSeek.top;
        paint.setStyle(Paint.Style.FILL);//设置为实心
        paint.setColor(colorTheme);
        canvas.drawRoundRect(rectSeek,
                mCorners, //x轴的半径
                mCorners, //y轴的半径
                paint);

        paint.setStrokeWidth(2);
        paint.setColor(colorBg);
        float lineCenterTop=seekHeight/2+top;
        int lineM=6;//三线间隔
        int linePadding=4;//三线边距
        canvas.drawLine(padding+linePadding,lineCenterTop,mWidth-padding-linePadding,lineCenterTop,paint);
        canvas.drawLine(padding+linePadding,lineCenterTop-lineM,mWidth-padding-linePadding,lineCenterTop-lineM,paint);
        canvas.drawLine(padding+linePadding,lineCenterTop+lineM,mWidth-padding-linePadding,lineCenterTop+lineM,paint);
    }

    private void drawBloder(Canvas canvas){
        seekAreaHight=mHeight-mWidth-mWidth;

        //背景
        RectF rect = new RectF(1, 1, mWidth-1, mHeight-1);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.FILL);//设置为实心
        paint.setColor(colorBg);

        canvas.drawRoundRect(rect,
                mCorners, //x轴的半径
                mCorners, //y轴的半径
                paint);
        //边框线
        rect = new RectF(1, 1, mWidth-1, mHeight-1);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);//设置为空心
//        paint.setColor(colorBloder);
        paint.setColor(Color.parseColor("#90ffffff"));
        canvas.drawRoundRect(rect,
                mCorners, //x轴的半径
                mCorners, //y轴的半径
                paint);
        int top = mHeight-mTopHeight;
        rectUp.left=0;          rectUp.top=0;               rectUp.right=mWidth;        rectUp.bottom=mTopHeight;
        rectSeekArea.left=0;    rectSeekArea.top=mTopHeight;    rectSeekArea.right=mWidth;  rectSeekArea.bottom=top;
        rectDown.left=0;        rectDown.top=top;           rectDown.right=mWidth;      rectDown.bottom=mHeight;
        int padding=2;
        canvas.drawLine(padding,mTopHeight,mWidth-padding,mTopHeight,paint);
        canvas.drawLine(padding,top,mWidth-padding,top,paint);

        paint.setColor(colorTheme);
        paint.setStrokeWidth(2);

        Path path = new Path(); //定义一条路径
        int centerX=mWidth/2;
        int topPath=mTopHeight/2;
        path.moveTo(centerX-4, topPath+2);
        path.lineTo(centerX, topPath-2);
        path.lineTo(centerX+4,topPath+2);
        canvas.drawPath(path, paint);


        path = new Path(); //定义一条路径
        path.moveTo(centerX-4, mHeight-topPath-2);
        path.lineTo(centerX, mHeight-topPath+2);
        path.lineTo(centerX+4,mHeight-topPath-2);
        canvas.drawPath(path, paint);

    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        mSeekBarChangeListener = listener;
    }

    public interface OnSeekBarChangeListener {
        public void onSeekUp();
        public void onSeekDown();

        public void onSeekTop();
        public void onSeekBottom();

        public void onProgressChanged(float scale);


    }


}