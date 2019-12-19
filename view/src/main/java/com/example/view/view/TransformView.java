package com.example.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class TransformView extends View {


    private static final float RECT_WIDTH = Utils.dp2px(120);

    private float centerX;
    private float centerY;

    private Paint paint;
    private RectF rectF;
    private Bitmap bitmap;


    public TransformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bitmap = Utils.loadAvatar(getContext(), (int) RECT_WIDTH);
        setBackgroundColor(Color.GRAY);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        rectF = new RectF(centerX - RECT_WIDTH, centerY - RECT_WIDTH, centerX + RECT_WIDTH, centerY + RECT_WIDTH);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 倒序
//        canvas.rotate(45,centerX,centerY);
//        canvas.translate(centerX, centerY);
//        canvas.scale(1.5f,1.5f);

        // 正序 两套坐标系
        canvas.translate(centerX, centerY);
        // 移动后的为canvas新的坐标系 再基于新的坐标系旋转
        canvas.rotate(45);
        canvas.scale(1.5f, 1.5f);

        // 矩形
        canvas.drawBitmap(bitmap, -RECT_WIDTH / 2, -RECT_WIDTH / 2, paint);

    }
}
