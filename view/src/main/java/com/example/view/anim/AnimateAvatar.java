package com.example.view.anim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class AnimateAvatar extends View {


    private Bitmap bitmap;
    private float bitmapHeight;
    private float bitmapWidth;

    private Paint paint;
    private Camera camera;

    private int centerX;
    private int centerY;
    private float transX;
    private float transY;


    private float flipRotation = 0;
    private float topFlip = 0;
    private float bottomFlip = 0;



    public AnimateAvatar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Utils.loadAvatar(getContext(), (int) Utils.dp2px(200));
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

        camera = new Camera();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        // bitmap距离中心点的偏移
        transX = centerX - bitmapWidth / 2;
        transY = centerY - bitmapHeight / 2;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.GRAY);
//        canvas.drawBitmap(bitmap,0,0,paint);


        // 左半边
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-flipRotation);

        camera.save();
        camera.rotateY(-topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(-bitmapWidth, -bitmapHeight , 0, bitmapHeight );
        canvas.rotate(flipRotation);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, centerX - bitmapWidth / 2, centerY - bitmapHeight / 2, paint);
        canvas.restore();

        // 右半边
        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-flipRotation);

        // 3D旋转
        camera.save();
        camera.rotateY(-bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        // 2D旋转
        canvas.clipRect(0, -bitmapHeight , bitmapWidth , bitmapHeight);
        canvas.rotate(flipRotation);
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, centerX - bitmapWidth / 2, centerY - bitmapHeight / 2, paint);
        canvas.restore();


    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }
}
