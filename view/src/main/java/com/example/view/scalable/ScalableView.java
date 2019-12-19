package com.example.view.scalable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class ScalableView extends View {

    private static final float BIG_SCALE_OFFSET = 1.5F;

    private Bitmap bitmap;
    private float offsetX;
    private float offsetY;
    private Paint paint;

    private float smallScale;
    private float bigScale;
    boolean isBigScale;

    private float scaleFraction;


    private GestureDetector gestureDetector;
    private ObjectAnimator objectAnimator;


    public ScalableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Utils.loadAvatar(getContext(), (int) Utils.dp2px(200));
        objectAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isBigScale = !isBigScale;
                if (isBigScale) {
                    objectAnimator.start();
                } else {
                    objectAnimator.reverse();
                }
                return super.onDoubleTap(e);
            }
        });

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        offsetX = (float) ((getWidth() - bitmap.getWidth()) / 2);
        offsetY = (float) ((getHeight() - bitmap.getHeight()) / 2);
//        offsetY = (getHeight() - bitmap.getHeight()) / 2;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            // 图片宽高比大于屏幕宽高比
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight();
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight();
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scale = smallScale + (bigScale * BIG_SCALE_OFFSET - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, (float) getHeight() / 2);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }


    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }
}


