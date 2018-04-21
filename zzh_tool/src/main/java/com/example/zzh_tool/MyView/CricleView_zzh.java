package com.example.zzh_tool.MyView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by zhangzihao on 2018/3/17.
 */

public class CricleView_zzh extends android.support.v7.widget.AppCompatImageView {

    private int mRadius;
    //半径
    private int Scale;
    //图片缩放比例
    private Paint mpaint;


    public CricleView_zzh(Context context) {
        super(context);
    }

    public CricleView_zzh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CricleView_zzh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(widthMeasureSpec, heightMeasureSpec);
        mRadius = size / 2;
        //设置控件的大小
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mpaint = new Paint();
        Drawable m = getDrawable();
        Bitmap bitmap = changeToBitmap(m);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Scale=mRadius*2/Math.min(bitmap.getHeight(),bitmap.getWidth());
        //能接受的大小和bitmap原大小的比值
        //相当于对大小乘以Scale然后的到屏幕适配的大小
        //Log.d("zzh","Scale"+Scale);
        Matrix matrix=new Matrix();
        matrix.setScale(Scale,Scale);
        //设置矩阵，用作对图片大小进行处理
        shader.setLocalMatrix(matrix);
        mpaint.setShader(shader);
        canvas.drawCircle(mRadius,mRadius,mRadius,mpaint);
        //回收bitmap
        bitmap.recycle();
    }

    private Bitmap changeToBitmap(Drawable m) {
        if (m instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) m;
            return bd.getBitmap();
        }else {
            int w=m.getIntrinsicWidth();
            int h=m.getIntrinsicHeight();
            //
            Bitmap bitmap=Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
            Canvas canvas=new Canvas(bitmap);
            m.setBounds(0,0,w,h);
            m.draw(canvas);
            return bitmap;
        }
    }
}
