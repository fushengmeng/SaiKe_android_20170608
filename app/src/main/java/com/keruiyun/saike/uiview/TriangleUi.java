package com.keruiyun.saike.uiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.keruiyun.saike.R;

/**
 * TODO: document your custom view class.
 */
public class TriangleUi extends View {

    private float lineSize;
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private Drawable mExampleDrawable;

    private Paint paint;
    private float width;

    private boolean up,down,left,right;


    public TriangleUi(Context context) {
        super(context);
        init(null, 0);
    }

    public TriangleUi(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TriangleUi(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TriangleUi, defStyle, 0);

        lineSize = a.getDimension(
                R.styleable.TriangleUi_triangSize,
                30f);

        mExampleColor = a.getColor(
                R.styleable.TriangleUi_triangColor,
                mExampleColor);
        if (a.hasValue(R.styleable.TriangleUi_triang_up)) {
            up = a.getBoolean(R.styleable.TriangleUi_triang_up,false);
        }
        if (a.hasValue(R.styleable.TriangleUi_triang_down)) {
            down = a.getBoolean(R.styleable.TriangleUi_triang_down,false);
        }
        if (a.hasValue(R.styleable.TriangleUi_triang_left)) {
            left = a.getBoolean(R.styleable.TriangleUi_triang_left,false);
        }
        if (a.hasValue(R.styleable.TriangleUi_triang_right)) {
            right = a.getBoolean(R.styleable.TriangleUi_triang_right,true);
        }
        if (a.hasValue(R.styleable.TriangleUi_triangSrc)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.TriangleUi_triangSrc);
            mExampleDrawable.setCallback(this);
        }

        if (a.hasValue(R.styleable.TriangleUi_triangSrc)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.TriangleUi_triangSrc);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        paint = new Paint();

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {

        paint.setColor(mExampleColor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width= (float) (lineSize*Math.sin(Math.PI/3*2));
        if (left){
            setMeasuredDimension((int)width,(int)lineSize);
        }else if (up){
            setMeasuredDimension((int)lineSize,(int)width);
        }else if (down){
            setMeasuredDimension((int)lineSize,(int)width);
        }else {
            setMeasuredDimension((int)width,(int)lineSize);
        }



    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int contentWidth = getWidth();
        int contentHeight = getHeight();

        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();

        if (left){
            path.moveTo(0, width);// 此点为多边形的起点
            path.lineTo(width, lineSize);
            path.lineTo(0,lineSize/2);
        }else if (up){
            path.moveTo(0, width);// 此点为多边形的起点
            path.lineTo(lineSize, width);
            path.lineTo(lineSize/2,width);
        }else if (down){
            path.moveTo(0, 0);// 此点为多边形的起点
            path.lineTo(lineSize, 0);
            path.lineTo(lineSize/2,width);
        }else {
            path.moveTo(0, 0);// 此点为多边形的起点
            path.lineTo(0, lineSize);
            path.lineTo(width,lineSize/2);
        }


        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(0, 0,  contentWidth, contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }



    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }



    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
