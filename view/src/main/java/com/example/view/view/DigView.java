package com.example.view.view;

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

import com.example.view.Utils;


/**
 * 对view的重叠部分进行镂空的几种方式
 * <p>
 * <ol>
 * <li>利用path的绘制方向 CW/CCW </li>
 * <li>利用path的FillType的EVEN_ODD</li>
 * <li>利用xfermode的DST_OUT</li>
 * </ol>
 */
public class DigView extends View {

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

    public DigView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
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
//        path.addRect(rectF, Path.Direction.CW);
//        path.setFillType(Path.FillType.EVEN_ODD);
//        path.addArc(arcRectF, 90, 180);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 1 通过path的绘制方向
//        path.addRect(rectF, Path.Direction.CCW);// 逆时针
//        path.addArc(arcRectF, 90, 180); // 默认顺时针
//        path.setFillType(Path.FillType.WINDING);// 默认是winding方式
//        canvas.drawPath(path,paint);

        // 2 通过绘制的交并集
//        path.addRect(rectF, Path.Direction.CW);// 逆时针
//        path.addArc(arcRectF, 90, 180); //顺时针
//        path.setFillType(Path.FillType.EVEN_ODD);
//        canvas.drawPath(path,paint);

        // 3 通过xfermode
//        int saveCount = canvas.saveLayer(rectF, paint, Canvas.ALL_SAVE_FLAG);
//        canvas.drawRect(rectF,paint);
//        paint.setColor(Color.RED);
//        paint.setXfermode(xfermode);
//        canvas.drawArc(arcRectF, 90, 185, false, paint);
//        canvas.restoreToCount(saveCount);

        // 4 clipPath 无法抗锯齿
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            path.addArc(arcRectF, 90, 180);
            canvas.clipOutPath(path);
            canvas.drawRect(rectF,paint);
        }

    }
}
