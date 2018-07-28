package com.example.zzh_tool.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;


/**
 * 柱状图view，
 */
public class BarChartView_zzh extends View {

    //设置文本和柱状图之间的距离
    private int mGap=20;
    //存储所有的下标
    private String[] mname={"长一点呢"};
    //存储所有的bar长度数据
    private float[]  mDataList=null;
    //得到最长的长度信息
    private float mMax;
    //    设置最长的height
//    private float maxBarHeight;
    private Boolean mChanged=false;
    //存储所有的bar信息
    private ArrayList<Bar> bars=new ArrayList<>();
    //柱状图宽度变量
    private float mBarWidth= 10;

    private int mRadius=0;

    //绘制文字的画笔
    private Paint mTextPaint=new Paint();
    private float mfont=40;
    //绘制文字的区域
    private Rect mTextRect=new Rect();
    //绘制柱状图的画笔
    private Paint mBarPaint=new Paint();

    //存储柱状图信息
    ArrayList<Bar> Bars=new ArrayList<>();



    public BarChartView_zzh(Context context) {
        this(context,null);
    }

    public BarChartView_zzh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BarChartView_zzh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 当整个view的大小发生变化的时候会调用此方法
     * @param w 整个view的宽度
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //清空之前的bars信息
        bars.clear();
        //padding为内边框,获得所有bar所占的大小
        int width=w-getPaddingLeft()-getPaddingRight();
        int height=h-getPaddingTop()-getPaddingBottom();
        //每一个柱状图所占的全部的大小（包含了padding部分），所以也可以看做每个柱状图之间的距离
        int step=width/mDataList.length;
        //绘制的起始点相对于每个bar的中线的距离
        mRadius= (int) (mBarWidth/2);
        //绘制的起始点,也用作记录每一个柱状条的起始绘制位置
        int barleft=getPaddingLeft()+step/2-mRadius;
        //将计算的长度返回给mTextRect,设置正中心
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.getTextBounds(mname[0],0,mname[0].length(),mTextRect);
        mTextPaint.setTextSize(mfont);
        //设置柱状图的长度
        //todo::设置最长长度
        int maxBarHeight=height-mTextRect.height()-mGap;

        //计算长度比率
        float heightRate=maxBarHeight/mMax;

        //遍历所有所有的高度信息，初始化柱状条
        for(float data:mDataList){
            Bar bar=new Bar();
            bar.value=data;
            //得到实际绘制的长度
            bar.transformealudValue=bar.value*heightRate;

            //得到绘制的四个位置
            bar.left=barleft;
            //右边的位置为左边的位置加上宽度
            bar.right= (int) (barleft+mBarWidth);
            bar.top= (int) (getPaddingTop()+maxBarHeight-bar.transformealudValue);
            bar.bottom=getPaddingTop()+maxBarHeight;

            barleft+=step;

            bar.currentTop=bar.bottom;

            Bars.add(bar);

        }


    }

    /**
     * 主要绘制方法
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBars(canvas);
    }

    /**
     * 绘制直方图
     */
    private void drawBars(Canvas canvas) {
        for (int i=0;i<Bars.size();i++){
            Bar bar=Bars.get(i);
            //获取目标柱状图的文本
            String text=mname[i];
            //设置文本的位置
            float textX=bar.left+mRadius;
            float textY=getHeight()-getPaddingBottom();

            canvas.drawText(text,textX,textY,mTextPaint);

            mBarPaint.setColor(getResources().getColor(com.example.zzh_tool.R.color.blue));
            //记录每一个
            RectF temp=new RectF(bar.left,bar.top,bar.right,bar.bottom);

            canvas.drawRoundRect(temp,mRadius,mRadius,mBarPaint);
        }
    }

    /**
     * 设置长度信息
     * @param mDataList
     * @param mMax DateLsit中最长的长度,方便之后进行单位像素的转换
     */
    public void setMlenth(float[] mDataList,float mMax) {
        this.mDataList = mDataList;
        this.mMax=mMax;
    }

    /**
     * 设置文字和柱状图之间的距离,默认为20dp
     * @param Gap 距离
     */
    public void setmGap(int Gap){
        mGap=Gap;
    }

    /**
     * 设置下标信息
     * @param mname
     */
    public void setMname(String[] mname) {
        this.mname = mname;
    }
    /**
     * 设置柱状图宽度，默认为10dp
     * @param mBarWidth
     */
    public void setmBarWidth(float mBarWidth) {
        this.mBarWidth = mBarWidth;
    }

    /**
     * 设置字体大小，默认为40
     * @param font
     */
    public void setTextSize(float font){
        this.mfont=font;
    }

    /**
     * 绘制的每一个直方图
     */
    private class Bar{
        int left;
        int top;
        int bottom;
        int right;
        //当前的top数值，用于动画
        int currentTop;

        float value;//原始数据的大小
        float transformealudValue;//转换成的对应像素的大小
    }
}




