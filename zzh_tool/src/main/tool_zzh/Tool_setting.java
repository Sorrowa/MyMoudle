package com.example.zzh_tool.tool_zzh;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 存储需要的工具方法
 */
public class Tool_setting {

    /**
     * 将浮点数转化为px类型的数字，在类似于new LayoutParams(dp_to_px(1.0f,context),
     * dp_to_px(1.0f,context))中使用
     * @param value 需要的像素值，在方法总进行转换
     * @param context 传入一个上下文对象，为了在方法中使用类型转换
     * @return 对应传入的value的像素值
     */
    public int dp_to_px(float value, Context context){
        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
        //传入的第一个参数为需要转换的单位
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value
                ,displayMetrics);
    }
}
