package com.example.zzh_tool.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.zzh_tool.R;
import com.example.zzh_tool.tool_zzh.Tool_setting;

import java.util.ArrayList;


/**
 * 仿制tim在线文档view
 */
public class Tim_fileRect_zzh extends View {

    //文件类别代码如下
    public static final int PDF = 0;
    public static final int PPT = 1;
    public static final int WORD = 2;
    public static final int ZIP = 3;
    public static final int OTHER = 4;


    //记录当前上下文对象
    private Context mcontext;

    //记录当前文档的类别名,以下为默认值
    private String name = "Dantalion";

    //记录当前类别的文件总数量
    private int number = 0;

    //记录文件每个内容物的类别
    private ArrayList<Integer> contents = new ArrayList<>();

    //内部到左右边框的长度
    private float padding;

    //内容之间的间距
    private int padding_all = 13;

    //长方形部分的长度
    private int rect_len = 0;

    //字体大小
    private float mfont = 20;

    //类别名称的绘制位置
    private float text_begin;

    //数量字体起始绘制位置
    private float number_begin;

    //正方形的画笔
    private Paint rectPaint;

    //类别文字画笔
    private Paint textPaint;

    //数量文字画笔
    private Paint numberPiant;

    //设置文件画笔
    private Paint filePaint;

    //file宏观长度
    private int fileLenth;

    //drawable数组
    private ArrayList<Drawable> drawables;

    //正方形背景颜色
    private int color_rect=R.color.aqua;


    public Tim_fileRect_zzh(Context context) {
        super(context);
        mcontext = context;
    }

    public Tim_fileRect_zzh(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mcontext = context;
    }

    public Tim_fileRect_zzh(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mcontext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        padding = Math.min(getPaddingLeft(), getPaddingRight());

        //设置正方形部分的长度,获取当前整体view的宽度
        rect_len = (int) (getMeasuredWidth() - 2 * padding);

        //设置字体的绘制起点
        text_begin = padding * 2 + mfont + rect_len;

        //设置类别的绘制七点
        number_begin = text_begin + padding;

        //设置文件项的绘制信息(长度)
        fileLenth = (int) ((rect_len - 3 * padding) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            drawAll(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 具体的绘制过程
     *
     * @param canvas
     */
    @SuppressLint("ResourceAsColor")
    private void drawAll(Canvas canvas) throws Exception {
        rectPaint = new Paint();
        //设置颜色
        rectPaint.setColor(color_rect);
        RectF rectF = new RectF(padding, padding, padding + rect_len
                , padding + rect_len);
        //绘制正方形
        canvas.drawRect(rectF, rectPaint);

        //绘制文字
        textPaint = new Paint();
        textPaint.setTextSize(mfont * 2);
        textPaint.setColor(R.color.black);
        canvas.drawText(name, padding, text_begin, textPaint);

        //绘制数量文字
        numberPiant = new Paint();
        numberPiant.setTextSize(mfont + 4);
        numberPiant.setColor(R.color.black);
        //检查是否为空
        if (contents == null) {
            throw new Exception("please set the content");
        }
        canvas.drawText("总数为: " + contents.size(), padding, number_begin, numberPiant);

        //绘制正方形中的文件内容
        filePaint = new Paint();
        //记录每个file图形的绘制起始位置
        int fileLeft = (int) (padding * 2);
        int fileTop;
        for (int i = 0; i < 4 && i < contents.size(); i++) {
            switch (i % 2) {
                case 0:
                    fileLeft = (int) (padding * 2);
                    break;
                case 1:
                    fileLeft = (int) (fileLeft + fileLenth + padding);
                    break;
            }
            if (i <= 1) {
                fileTop = (int) (padding * 2);
            } else {
                fileTop = (int) (padding * 3 + fileLenth);
            }
            filePaint.setColor(R.color.red);
            RectF rectF1 = new RectF(fileLeft, fileTop, fileLeft + fileLenth
                    , fileTop + fileLenth);
            //获取相应的图片信息
            if (drawables==null){
                throw new Exception("don't set the drawables");
            }
            Drawable drawable= drawables.get(contents.get(i));
            Bitmap bitmap=changeToBitmap(drawable);
            canvas.drawBitmap(bitmap,null,rectF1, filePaint);
        }
    }

    /**
     * 设置文件类别，使用int代值表示具体类别
     *
     * @param contents
     */
    public void setContents(ArrayList<Integer> contents) {
        this.contents = contents;
    }

    /**
     * 添加一个文件
     *
     * @param content
     */
    public void addContents(int content) {
        this.contents.add(content);
        //重新绘制内容
        invalidate();
    }

    /**
     * 设置类别名称，默认name为 Dantalion
     *
     * @param name 传入的类别名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置数字字体大小
     *
     * @param mfont
     */
    public void setfont(float mfont) {
        this.mfont = mfont;
    }

    /**
     * drawable 为设置所需设置的图片
     * @param drawables
     */
    public void setDrawables(ArrayList<Drawable> drawables) {
        this.drawables = drawables;
    }

    /**
     * 将drawble转化为bitmap
     * @param m
     * @return
     */
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

    /**
     * 设置正方形的背景颜色
     * @param color_rect
     */
    public void setColor_rect(int color_rect) {
        this.color_rect = color_rect;
    }
}

