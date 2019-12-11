package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * 对view的重叠部分进行镂空的几种方式
 * <p>
 * <ol>
 * <li>利用path的绘制方向 CW/CCW </li>
 * <li>利用path的FillType的EVEN_ODD</li>
 * <li>利用xfermode的DST_OUT</li>
 * </ol>
 */
public class CustomView extends View {

    public static final float RECT_WIDTH = Utils.dp2px(120);
    public static final float RECT_HEIGHT = Utils.dp2px(80);
    public static final float ARC_RADIUS = Utils.dp2px(35);

    Paint paint;
    Path path;
    private float centerX;
    private float centerY;
    private RectF rectF;
    private RectF arcRectF;

    Xfermode xfermode;

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        rectF = new RectF(centerX - RECT_WIDTH, centerY - RECT_HEIGHT, centerX + RECT_WIDTH, centerY + RECT_HEIGHT);
        arcRectF = new RectF(centerX + RECT_WIDTH - ARC_RADIUS, centerY - RECT_HEIGHT, centerX + RECT_WIDTH + ARC_RADIUS, centerY + RECT_HEIGHT);

        path.reset();
        path.addRect(rectF, Path.Direction.CW);
        path.setFillType(Path.FillType.EVEN_ODD);
        path.addArc(arcRectF, 90, 180);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPath(path, paint);
//        int saveLayer = canvas.saveLayer(rectF,paint);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(xfermode);
        paint.setColor(Color.RED);
        canvas.drawArc(arcRectF, 90, 180, false, paint);
//        canvas.restoreToCount(saveLayer);
    }
}
