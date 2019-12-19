package com.example.view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class Dashboard extends View {

    private static final float RADIUS = Utils.dp2px(150);
    private static final float ANGLE = 90f;


    private static final float MARK_LEN = 15;
    private static final float MARK_WIDTH = Utils.dp2px(3);
    private static final float MARK_HEIGHT = Utils.dp2px(30);

    private static final float POINT = RADIUS - MARK_HEIGHT - Utils.dp2px(20);

    private Path path;
    private Paint paint;
    private PathEffect markEffect;
    private float centerX;
    private float centerY;
    private RectF arcRectF;
    private DiscretePathEffect arcEffect;
    private SweepGradient sweepGradient;


    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("super ...");
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        arcRectF = new RectF(centerX - RADIUS, centerY - RADIUS, centerX + RADIUS, centerY + RADIUS);

        path.addArc(arcRectF, 90f + ANGLE / 2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(path, false);

        // 总长度减去最后一个刻度 除以 分段的个数
        float dashWidth = (pathMeasure.getLength() - MARK_WIDTH) / (MARK_LEN);

        // 刻度形状
        Path dash = new Path();
        dash.addRect(0, 0, MARK_WIDTH, MARK_HEIGHT, Path.Direction.CCW);
        markEffect = new PathDashPathEffect(dash, dashWidth, 0, PathDashPathEffect.Style.MORPH);

        // 弧形
        arcEffect = new DiscretePathEffect(Utils.dp2px(20), Utils.dp2px(5));

        int color1 = Color.parseColor("#FF6332");
        int color2 = Color.parseColor("#FDB32E");
        int color3 = Color.parseColor("#008577");

        sweepGradient = new SweepGradient(centerX, centerY, new int[]{color1, color2, color3}, null);
        paint.setShader(sweepGradient);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//         画刻度
        paint.setPathEffect(markEffect);
        canvas.drawPath(path, paint);
        // 画圆盘
//        paint.setPathEffect(arcEffect);
        paint.setPathEffect(null);
        paint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawPath(path, paint);
        // 画指针

        paint.setPathEffect(null);
        float angle = markIndex2angle(6);
        System.out.println("angle:" + angle);
        canvas.drawLine(centerX, centerY,
                centerX - (float) Math.cos(Math.toRadians(180f - angle)) * POINT,
                centerY + (float) Math.sin(Math.toRadians(180f - angle)) * POINT,
                paint);


//        paint.setPathEffect(null);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(centerX,centerY,RADIUS,paint);

    }


    float markIndex2angle(int markIndex) {
        return 90f + ANGLE / 2 + (360f - ANGLE) / MARK_LEN * markIndex;
    }


}
