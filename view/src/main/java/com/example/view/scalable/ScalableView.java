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
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class ScalableView extends View implements Runnable {

    private static final float BIG_SCALE_OFFSET = 1.5F;

    private Bitmap bitmap;
    private Paint paint;
    private float originOffsetX;
    private float originOffsetY;

    private float smallScale;
    private float bigScale;
    private boolean isBigScale;

    private float scaleFraction;

    private GestureDetector gestureDetector;
    private ObjectAnimator objectAnimator;


    private float offsetX;
    private float offsetY;
    private OverScroller scroller;


    public ScalableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Utils.loadAvatar(getContext(), (int) Utils.dp2px(200));
        objectAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        scroller = new OverScroller(getContext());
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }


            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isBigScale = !isBigScale;
                if (isBigScale) {
                    float offset = e.getX() - getWidth() / 2f;
                    offsetX = offset - offset * bigScale / smallScale;
                    offset = e.getY() - getHeight()/2f;
                    offsetY = offset - offset * bigScale / smallScale;

                    objectAnimator.start();
                } else {
                    // 重置偏移量
                    objectAnimator.reverse();
                }

                return super.onDoubleTap(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // 拖动 distance 为旧点减去新点的距离
                if (isBigScale) {
                    offsetX -= distanceX;
                    float maxOffset = (bitmapWidth() - getWidth()) / 2;
                    offsetX = Math.max(offsetX, -maxOffset);
                    offsetX = Math.min(offsetX, maxOffset);
                    offsetY -= distanceY;
                    float maxOffsetY = (bitmapHeight() - getHeight()) / 2;
                    offsetY = Math.max(offsetY, -maxOffsetY);
                    offsetY = Math.min(offsetY, maxOffsetY);
                    invalidate();
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) -(bitmapWidth() - getWidth()) / 2,
                        (int) (bitmapWidth() - getWidth()) / 2,
                        (int) -(bitmapHeight() - getHeight()) / 2,
                        (int) (bitmapHeight() - getHeight()) / 2
                );
                postOnAnimation(ScalableView.this);
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    @Override
    public void run() {
        if (scroller.computeScrollOffset()) {
            offsetX = scroller.getCurrX();
            offsetY = scroller.getCurrY();
            invalidate();
            postOnAnimation(this);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        originOffsetX = (float) ((getWidth() - bitmap.getWidth()) / 2);
        originOffsetY = (float) ((getHeight() - bitmap.getHeight()) / 2);
//        originOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            // 图片宽高比大于屏幕宽高比
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * BIG_SCALE_OFFSET;
        } else {
            bigScale = (float) getWidth() / bitmap.getWidth();
            smallScale = (float) getHeight() / bitmap.getHeight() * BIG_SCALE_OFFSET;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX, offsetY);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.drawBitmap(bitmap, originOffsetX, originOffsetY, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private float bitmapWidth() {
//        return bitmap.getWidth() * (smallScale + (bigScale - smallScale) * scaleFraction);
        return bitmap.getWidth() * bigScale;
    }

    private float bitmapHeight() {
//        return bitmap.getHeight() * (smallScale + (bigScale - smallScale) * scaleFraction);
        return bitmap.getHeight() * bigScale;
    }


    public float getScaleFraction() {
        return scaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }


}


