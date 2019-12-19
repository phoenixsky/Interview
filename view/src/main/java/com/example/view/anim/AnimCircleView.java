package com.example.view.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AnimCircleView extends View {

    private float radius = 100;
    private Paint paint;

    public AnimCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(200,200,radius,paint);
    }


    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }
}
