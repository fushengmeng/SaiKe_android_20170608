package com.keruiyun.saike.uiview;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.keruiyun.saike.util.PreferencesUtil;
import com.keruiyun.saike.util.Util;

import java.io.UnsupportedEncodingException;
import java.util.Date;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * @author xiaanming
 *
 */
public  class BaseProgressBar extends View  {


	/**
	 * 画笔对象的引用
	 */
	protected Paint paint;

	/**
	 * 进度画笔对象的引用
	 */
	protected Paint fillArcPaint;
    protected Paint progressCirclePaint;
    float sweepAngle=360;

	protected BlurMaskFilter mBlur;
	protected RectF oval ;
	// 环形颜色填充
	SweepGradient sweepGradient;

	/**
	 * 圆环的颜色
	 */
    protected int roundColor;
    protected int progressEndColor;

    /**
     * 圆半径
     */
    protected int radius;//外圆
    protected int radius_within;//内圆
    protected int  centerX,centerY;

	
	/**
	 * 圆环的宽度
	 */
	protected float roundWidth;
	
	/**
	 * 最大进度
	 */
	private int max=60;
	
	/**
	 * 当前进度(秒)
	 */
	protected int progress=0;

    /**
     * 一圈是否是一秒
     */
    private boolean circleIsSecond=true;

    /**
     * 一分钟内当前旋转角度
     */
    public int startRotate=-90;
    public float currRotate=startRotate;



    private int width;


    /**
     * 字体
     */
    private Typeface typeFace;
    protected Paint textPaint;

	//渐变数组
    protected int[] arcColors;
    protected float[] positions;

    private OnClickTimerListener onClickTimerListener;

    private TimerStartPauseListener timerStartPauseListener;
	
	public BaseProgressBar(Context context) {
		this(context, null);
	}

	public BaseProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public BaseProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

        typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/ziti.TTF");
		mBlur = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);
		oval = new RectF();

		paint = new Paint();
        // 设置光源的方向
        float[] direction = new float[]{ 1, 1, 1 };
        //设置环境光亮度
        float light = 0.4f;
        // 选择要应用的反射等级
        float specular = 6;
        // 向mask应用一定级别的模糊
        float blur = 3.5f;
        EmbossMaskFilter emboss=new EmbossMaskFilter(direction,light,specular,blur);
        // 应用mask
        paint.setMaskFilter(emboss);
		// 设置是否抗锯齿
		paint.setAntiAlias(true);
		// 帮助消除锯齿
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// 设置中空的样式
		paint.setStyle(Paint.Style.STROKE);
		paint.setDither(true);
		paint.setStrokeJoin(Paint.Join.ROUND);

		fillArcPaint = new Paint();
		// 设置是否抗锯齿
		fillArcPaint.setAntiAlias(true);
		// 帮助消除锯齿
		fillArcPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
		// 设置中空的样式
		fillArcPaint.setStyle(Paint.Style.STROKE);
		fillArcPaint.setDither(true);
		fillArcPaint.setStrokeJoin(Paint.Join.ROUND);

        progressCirclePaint = new Paint();
        // 设置是否抗锯齿
        progressCirclePaint.setAntiAlias(true);
        // 帮助消除锯齿
        progressCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // 设置中空的样式
        progressCirclePaint.setStyle(Paint.Style.FILL);
        progressCirclePaint.setDither(true);
        progressCirclePaint.setStrokeJoin(Paint.Join.ROUND);

        textPaint = new Paint();
        // 设置是否抗锯齿
        textPaint.setAntiAlias(true);
//         帮助消除锯齿
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(typeFace);
        textPaint.setTextSize(80);
		
		TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.BaseProgressBar);
        //获取自定义属性和默认值
        circleIsSecond=mTypedArray.getBoolean(R.styleable.BaseProgressBar_CircleIsSecond,true);

        mTypedArray.recycle();

		if (circleIsSecond)
		    max=1000;

		int defaultColor = 0xFF000000;
		int[] attrsArray = { R.attr.custom_attr_app_color_illuminant };
		TypedArray typedArray = context.obtainStyledAttributes(attrsArray);
		roundColor = typedArray.getColor(0, defaultColor);
		// don't forget the resource recycling
        typedArray.recycle();

        attrsArray = new int[]{android.R.attr.textColor};
        typedArray = context.obtainStyledAttributes(attrsArray);
        int txtColor = typedArray.getColor(0, defaultColor);
        // don't forget the resource recycling
        typedArray.recycle();
        textPaint.setColor(txtColor);


        progressEndColor=Color.parseColor("#ffc11b");
        progressCirclePaint.setColor(progressEndColor);
        setArcColors();
		roundWidth=6;
        initData();
        setClickable(true);

	}

	public void  setArcColors(int roundColor,int progressEndColor){
         positions =  new float[]{0,0.9f,1f};
         arcColors =  new int[] {roundColor,roundColor,progressEndColor};

    }
    public void setArcColors(){
        setArcColors(roundColor,progressEndColor);
    }

    public void setOnClickTimerListener(OnClickTimerListener onClickTimerListener) {
        this.onClickTimerListener = onClickTimerListener;
    }

    @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Resources resources = this.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		float density = dm.density;
		int swidth = dm.widthPixels;
		int sheight = dm.heightPixels;

		width= (int) ((swidth<sheight?swidth:sheight)/3);
		if (width<=0)
			width= (int) (1080/3);
		setMeasuredDimension(width,width);
	}

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int padding= 4;
        radius=width/2-padding;
        radius_within=radius-Util.dip2px(getContext(),16); //内圆环的半径
        centerX=width/2;
        centerY=width/2;
    }


    @Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

        drawCircle(canvas);

		drawProgress(canvas);

        drawTextTime(canvas);

	}

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	    boolean result=super.onTouchEvent(event);

        int eventaction = event.getAction();

        switch (eventaction) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE: {

                break;
            }
            case MotionEvent.ACTION_UP: {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                if (onClickTimerListener!=null){
                    onClickTimerListener.onClickTimer(this);
                }
//                int offset=50;
//                boolean isClick=x > timerTxtxy[0] && x < timerTxtxy[2] && y > timerTxtxy[1]-offset && y < timerTxtxy[3]+offset;
//                LogCus.msg("("+x+","+y+")----"+isClick+":"+timerTxtxy[0]+":"+timerTxtxy[1]+":"+timerTxtxy[2]+":"+timerTxtxy[3]);
//                if (isClick) {
//                    if (onClickTimerListener!=null){
//                        onClickTimerListener.onClickTimer(this);
//                    }
//                }
//                if (hasDate){
//                    if (x > dateTxtxy[0] && x < dateTxtxy[2] && y > dateTxtxy[1] && y < dateTxtxy[3]) {
//                        if (onClickTimerListener!=null){
//                            onClickTimerListener.onClickDate(this);
//                        }
//                    }
//                }
                result=true;
                break;
            }
        }

        return result;
    }

    public void setTimerStartPauseListener(TimerStartPauseListener timerStartPauseListener) {
        this.timerStartPauseListener = timerStartPauseListener;
        if (timerThread!=null&&timerThread.isStart){
            timerStartPauseListener.onStartListener();
        }else {
            timerStartPauseListener.onPauseListener();
        }
    }

    protected void drawCircle(Canvas canvas){
        /**
         * 画最外层的大圆环
         */
//        LogCus.msg("radius:"+radius+":centerX:"+centerX+":centerY:"+centerY);
//        paint.setColor(roundColor); //设置圆环的颜色
        paint.setColor(Color.parseColor("#154e57"));
        paint.setStrokeWidth(3); //设置圆环的宽度
        canvas.drawCircle(centerX, centerX, radius, paint); //画出圆环边框

        paint.setColor(roundColor); //设置圆环的颜色
//        paint.setColor(Color.parseColor("#217885"));
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        canvas.drawCircle(centerX, centerX, radius_within, paint); //画出圆环
    }

    protected void drawProgress(Canvas canvas){

		// 环形颜色填充
		sweepGradient =new SweepGradient(centerX, centerY, arcColors, positions);

        fillArcPaint.setShader(sweepGradient);
		// 设置画笔为白色

		// 模糊效果
		fillArcPaint.setMaskFilter(mBlur);
		// 设置线的类型,边是圆的
//		fillArcPaint.setStrokeCap(Paint.Cap.ROUND);

		//设置圆弧的宽度
		fillArcPaint.setStrokeWidth(roundWidth);
		// 确定圆弧的绘制位置，也就是里面圆弧坐标和外面圆弧坐标
		oval.set(centerX-radius_within, centerY-radius_within, centerX+radius_within, centerY+radius_within);
        canvas.save();
        canvas.rotate(currRotate,centerX,centerY);
		// 画圆弧，第二个参数为：起始角度，第三个为跨的角度，第四个为true的时候是实心，false的时候为空心
		canvas.drawArc(oval,
				0,
                sweepAngle,
				false,fillArcPaint);
        float[] centerProgress=getCenter(centerX,centerY,radius_within,0);
        float progressCircleRadius=roundWidth;
        canvas.drawCircle(centerProgress[0],centerProgress[1],progressCircleRadius,progressCirclePaint);
        canvas.restore();
	}


	protected void drawTextTime(Canvas canvas){

        // 以下这行是实现水平居中。drawText相应改为传入targetRect.centerX()
        textPaint.setTextAlign(Paint.Align.CENTER);
        String testString=setCurTimerTime(progress);
        textPaint.setTextSize(60);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = setTimeLine(centerY-fontMetrics.top/2 - fontMetrics.bottom/2);

        float widthTxt = textPaint.measureText("8");
//        float widthsplit = textPaint.measureText(":");

        float txtw=textPaint.measureText("88:88:88");
        float txtX=(width-txtw)/2;
        canvas.drawText(Character.toString(testString.charAt(0)), txtX, baseline, textPaint);
        txtX+=widthTxt;
        canvas.drawText(Character.toString(testString.charAt(1)), txtX, baseline, textPaint);

        txtX+=widthTxt;
        canvas.drawText(":", txtX, baseline, textPaint);

        txtX+=widthTxt;
        canvas.drawText(Character.toString(testString.charAt(3)), txtX, baseline, textPaint);
        txtX+=widthTxt;
        canvas.drawText(Character.toString(testString.charAt(4)), txtX, baseline, textPaint);

        txtX+=widthTxt;
        canvas.drawText(":", txtX, baseline, textPaint);

        txtX+=widthTxt;
        canvas.drawText(Character.toString(testString.charAt(6)), txtX, baseline, textPaint);
        txtX+=widthTxt;
        canvas.drawText(Character.toString(testString.charAt(7)), txtX, baseline, textPaint);

    }

    /**时间基线*/
    protected int setTimeLine(int baseLine){
	    return baseLine;
    }

    public void initData(){ };


    /**
     * 时分秒
     * */
    public String setCurTimerTime(int progress){
        String testString="01:02:08";
        return testString;
    }

    /**
     * 年月日
     * */
    public String setCurSystime(){
        String  testString="2017-02-08";
        return testString;
    }


	private TimerThread timerThread;
	public synchronized  void start(){
	    if (timerStartPauseListener!=null)
            timerStartPauseListener.onStartListener();

	   if (timerThread!=null&&timerThread.isStart){
	       return;
       }
        timerThread=new TimerThread();
        timerThread.start();

    }
    public synchronized void stop(){
        if (timerStartPauseListener!=null)
            timerStartPauseListener.onPauseListener();
        if (timerThread!=null&&timerThread.isStart){
            timerThread.setStop();
        }
    }

    public synchronized boolean isStart(){
        if (timerThread!=null&&timerThread.isStart){
            return true;
        }
        return false;
    }

	protected float[] getCenter(int x0,int y0,int r,int a0){
       /* 圆点坐标：(x0,y0)
        半径：r
        角度：a0

        则圆上任一点为：（x1,y1）
        x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
        y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )*/
       float[] xy=new float[2];
        xy[0] = (float) (x0 + r * Math.cos(a0 * Math.PI / 180));
        xy[1]   = (float) (y0   +   r   *   Math.sin(a0   *   Math.PI   /180   ));

       return xy;
    }
	
	
	public synchronized int getMax() {
		return max;
	}

	/**
	 * 设置进度的最大值
	 * @param max
	 */
	public synchronized void setMax(int max) {
		if(max < 0){
			throw new IllegalArgumentException("max not less than 0");
		}
		this.max = max;
	}

	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress < 0){
			throw new IllegalArgumentException("progress not less than 0");
		}
		if(progress > max){
			progress = max;
		}
		if(progress <= max){
			this.progress = progress;
			postInvalidate();
		}
		
	}



    public synchronized boolean isInvisibleHour(){
	    return false;
    }
	protected String getTimeStr(int timelength){
	    return getTimeStr(timelength,false);
    }


    /**
     * @param isSeviseSecond 是否校验秒位置
     * */
    protected String getTimeStr(int timelength,boolean isSeviseSecond){
        String str = "";

        int sec = timelength % 60;
        int minute = (timelength / 60) % 60;
        int hour = timelength / 3600;
        progress=sec+1;
        if (isSeviseSecond){
            currRotate=progress*6+startRotate;
        }

        if (hour>=99&&minute>=59&&sec>=59){
            hour=99;
            minute=59;
            sec=59;
            stop();
        }

        if (hour > 9)
        {
            str += String.valueOf(hour);
        }
        else
        {

            str += ("0" + String.valueOf(hour));
        }

        if (isInvisibleHour()){
            str ="  ";
        }

        if (minute > 9)
        {
            str += (":" + String.valueOf(minute));
        }
        else
        {
            str += (":0" + String.valueOf(minute));
        }

        if (sec > 9)
        {
            str += (":" + String.valueOf(sec));
        }
        else
        {
            str += (":0" + String.valueOf(sec));
        }

        return str;
    }



    public interface OnClickTimerListener{
        public void onClickTimer(BaseProgressBar baseProgressBar);
        public void onClickDate(BaseProgressBar baseProgressBar);
    }

    private class TimerThread extends Thread{
        private boolean isStart=true;

        public synchronized void setStop(){
            this.isStart=false;
        }

        @Override
        public void run() {
            super.run();

            int count = 0;
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                mHandler.sendEmptyMessage(2);
                if (isStart)
                    mHandler.sendEmptyMessage(1);
                else
                    return;
            }
        }
    }


    public Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (msg.what==1){
                currRotate = secondRotate(progress);
                postInvalidate();
                return false;
            }
            return BaseProgressBar.this.handleMessage(msg);
        }
    });

    protected float secondRotate(int progress){
        currRotate=progress*6+startRotate;
        if (currRotate>=270)
            currRotate=startRotate;
       return currRotate;
    }

	public boolean handleMessage(Message msg){
	    return false;
    }

}
