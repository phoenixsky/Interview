package com.example.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class PieView extends View {

    private static final float RADIUS = Utils.dp2px(120);
    private static final float MOVE_SIZE = Utils.dp2px(10);

    private float centerX;
    private float centerY;
    private RectF arcRectF;

    private Paint paint;


    int[] angles = {60, 100, 120, 80};
    int[] colors = {Color.parseColor("#FF6332"),
            Color.parseColor("#FDB32E"),
            Color.parseColor("#008577"),
            Color.MAGENTA};


    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        arcRectF = new RectF(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int floatIndex = 1;

        int currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {

            paint.setColor(colors[i]);
            canvas.save();
            if (floatIndex == i) {
                float x = (float) (Math.cos(Math.toRadians(currentAngle + angles[i] / 2)) * MOVE_SIZE);
                float y = (float) (Math.sin(Math.toRadians(currentAngle + angles[i] / 2)) * MOVE_SIZE);
                System.out.println("x:" + x + " y:" + y);
                canvas.translate(x, y);
            }
            canvas.drawArc(arcRectF, currentAngle, angles[i], true, paint);
            canvas.restore();
            currentAngle += angles[i];
        }

    }
}
