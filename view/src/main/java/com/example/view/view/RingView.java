package com.example.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class RingView extends View {


    private static final float RADIUS = Utils.dp2px(120);
    private static final float RING_WIDTH = Utils.dp2px(16);

    private float centerX;
    private float centerY;
    private RectF arcRect;

    private Paint paint;
    private Rect textRect;
    private SweepGradient sweepGradient;


    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(RING_WIDTH);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(Utils.dp2px(80));

        textRect = new Rect();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2 + Utils.dp2px(100);
        arcRect = new RectF(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);
        sweepGradient = new SweepGradient(centerX, centerY, Color.RED, Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 圆环
        paint.setColor(Color.parseColor("#90A4AD"));
        canvas.drawCircle(centerX, centerY, RADIUS, paint);

        // 半环
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setShader(sweepGradient);
        canvas.drawArc(arcRect, 270, 180 + 45, false, paint);
        paint.setColor(Color.parseColor("#FF3E7F"));
        paint.setShader(null);

        // 居中文字 abc
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        String text = "abcg";

        // 文字固定的话 优先处理方式
        // 缺点:当文字变化时,偏移量会变
        paint.getTextBounds(text, 0, text.length(), textRect);
        System.out.println(textRect.top);
        System.out.println(textRect.bottom);
        float offset = (textRect.top + textRect.bottom) >> 1;

        // 显示文字不固定
        // 根据字体的ascent和descent
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        offset = (fontMetrics.top + fontMetrics.bottom) / 2;
        canvas.drawText(text, centerX, centerY - offset, paint);

        // view顶部draw文字 需要增加字体高度偏移量
        canvas.drawText(text, centerX, 0 - offset * 2, paint);

        // 不同大小文字的左对齐
        paint.setTextAlign(Paint.Align.LEFT);
        // 文字行间距
        float fontSpacing = paint.getFontSpacing();
        paint.setTextSize(Utils.dp2px(40));
        canvas.drawText(text, 0, fontSpacing, paint);
        fontSpacing = paint.getFontSpacing();

        paint.setTextSize(Utils.dp2px(120));
        Paint.FontMetrics metrics = paint.getFontMetrics();
        float height = metrics.bottom - metrics.top;

        // 文字左侧偏移量
        paint.getTextBounds(text, 0, text.length(), textRect);
        canvas.drawText(text, 0 - textRect.left, fontSpacing + height, paint);

    }
}
