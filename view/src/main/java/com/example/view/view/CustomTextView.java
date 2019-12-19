package com.example.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class CustomTextView extends View {


    private TextPaint paint;
    private StaticLayout staticLayout;
    private String text;
    private float[] mesuredWidth;

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(Utils.dp2px(30));
        text = "Canvas 的变换方法多次调用的时候，由于 Canvas 的坐标系会整体被变换，因此当平移、旋转、放 缩、错切等变换多重存在的时候，Canvas 的变换参数会⾮非常难以计算，因此可以改⽤用倒序的理理解⽅式:";

        mesuredWidth = new float[1];

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        staticLayout = new StaticLayout(text, 0, text.length(), paint,
                getWidth(),//换行的宽度
                Layout.Alignment.ALIGN_NORMAL, //绘制顺序
                1, //原始行高的倍数
                0, // 原始行高额外加的高度
                false); //??


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        staticLayout.draw(canvas);
        int start = 0;
        int index = paint.breakText(text, 0, text.length(), true, getWidth(), mesuredWidth);
        int end = start + index;
        canvas.drawText(text, 0, index, 0, 100, paint);
        start = end;
        int index1 = paint.breakText(text, index, text.length(), true, getWidth() - Utils.dp2px(100), mesuredWidth);
        end = index + index1;
        canvas.drawText(text, start, end, 0, 100 + paint.getFontSpacing(), paint);
        start = end;
        int index2 = paint.breakText(text, index + index1, text.length(), true, getWidth() - Utils.dp2px(100), mesuredWidth);
        end = start + index2;
        canvas.drawText(text, start, end, 0, 100 + paint.getFontSpacing() * 2, paint);

        // 在图片换行时
        // 当文字的高度和底部只要在图片的高度返回内 都需要折行


    }
}
