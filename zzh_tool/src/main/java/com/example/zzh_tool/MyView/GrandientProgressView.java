package com.example.zzh_tool.MyView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.zzh_tool.R;


/**
 * 渐变色的圆弧
 */
public class GrandientProgressView extends View {

    //中心坐标
    private float mx;
    private float my;

    //记录绘制的大小信息
    private RectF rectF;

    //背景颜色画笔
    private Paint backgroundPaint;

    //进度条画笔
    private Paint contentPaint;

    //渐变的color,默认从白色到粉色
    private int[] colors={R.color.azure,R.color.pink};

    //当前进度
    private float mprogress=0;

    //动画时间
    private int duration=500;


    public GrandientProgressView(Context context) {
        super(context);
    }

    public GrandientProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GrandientProgressView(Context context, @Nullable AttributeSet attrs
            , int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化绘制信息等
     */
    private void inite(){
        //绘制底色使用的画笔
        backgroundPaint=new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            backgroundPaint.setColor(getResources().getColor(R.color.gray
                    ,null));
        }else{
            backgroundPaint.setColor(getResources().getColor(R.color.gray));
        }
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(mx/5);

        //进图条画笔
        //todo:渐变颜色设置
        contentPaint=new Paint();
        Shader shader=new SweepGradient(mx,my,colors,null);
        contentPaint.setShader(shader);
        //设置只绘制边框
        contentPaint.setStyle(Paint.Style.STROKE);
        //设置圆环的宽度
        //todo:尝试宽度中
        contentPaint.setStrokeWidth(mx/5);
        contentPaint.setAntiAlias(true);
        //设置圆环两端形状（此处为将其设置为圆形）
        contentPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    //初始化rect大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int left=getPaddingLeft();
        int right=w-getPaddingRight();
        int top=getPaddingTop();
        int bottom=h-getPaddingBottom();
        rectF=new RectF();
        //将view的大小放入
        rectF.set(left,top,right,bottom);

        //相对于画布坐标
        mx=w/2;
        my=h/2;

        inite();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawAll(canvas);
    }

    /**
     * 绘制的具体过程
     * @param canvas
     */
    private void drawAll(Canvas canvas) {
        //绘制底色
        canvas.drawArc(rectF,0.0f,360.0f,false
                ,backgroundPaint);
        //绘制进度
        float Angel=mprogress/100 *360;
        canvas.drawArc(rectF,0.0f,Angel,false,
                contentPaint);
    }

    /**
     * 设置颜色数组,如果不设置，那么默认颜色为白色到粉色
     * @param colors
     */
    public void setColors(int[] colors) {
        this.colors = colors;
    }

    /**
     * 设置当前进度
     * @param progress 0~100
     */
    public void setProgress(float progress) {
        this.mprogress = progress;
        invalidate();
    }


    /**
     * 设置动画时间，默认时间长度为0.5秒
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * 通过setProgress进行动画效果建立
     * @param degree
     */
    public void startAnimation(int degree){
        //会自动进行反射调用
        //会自动进行0，到degree的调用
        //注意属性名称
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(this
                ,"progress",0,degree);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
}
