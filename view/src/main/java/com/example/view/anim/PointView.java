package com.example.view.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.Utils;

public class PointView extends View {

    private Paint paint;
    private PointF point;
    private Path path;
    private CornerPathEffect cornerPathEffect;
    private TextPaint textPaint;


    public PointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        point = new PointF();
        path = new Path();
        cornerPathEffect = new CornerPathEffect(200);
        textPaint = new TextPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(Utils.dp2px(30));

//        canvas.drawPoint(point.x, point.y, paint);
        paint.setPathEffect(cornerPathEffect);

//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeMiter(0);
        canvas.drawPath(path, paint);

//        paint.reset();
        textPaint.setTextSize(Utils.dp2px(20));
        canvas.drawText(point.x + "," + point.y, 0, 1300, textPaint);
    }

    public PointF getPoint() {
        return point;
    }

    public void setPoint(PointF point) {
        this.point = point;
        if (path.isEmpty()) {
            path.moveTo(point.x, point.y);
        } else {
            path.lineTo(point.x, point.y);
        }
        invalidate();
    }
}
