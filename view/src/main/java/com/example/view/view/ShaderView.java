package com.example.view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.view.R;
import com.example.view.Utils;

public class ShaderView extends View {

    public static final float WIDTH = Utils.dp2px(100);

    private float centerX;
    private float centerY;
    private RectF rectF;

    private Paint paint;
    private SweepGradient sweepGradient;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;

    private BitmapShader bitmapShader;
    private Bitmap bitmap;
    private ShapeDrawable shapeDrawable;

    private PorterDuffColorFilter colorFilter;

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = Utils.loadAvatar(getContext(), (int) Utils.dp2px(100));
        bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.MIRROR);
        shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setShader(bitmapShader);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        rectF = new RectF(centerX - WIDTH/2, centerY - WIDTH/2, centerX + WIDTH/2, centerY + WIDTH/2);


        linearGradient = new LinearGradient(rectF.left, rectF.top, centerX, centerY, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
        sweepGradient = new SweepGradient(centerX, centerY, Color.RED, Color.BLUE);
        radialGradient = new RadialGradient(centerX, centerY, WIDTH, Color.RED, Color.GREEN, Shader.TileMode.REPEAT);


        System.out.println("width:"+ bitmap.getWidth());
        System.out.println("height:"+ bitmap.getHeight());
        System.out.println("width:"+WIDTH);


        colorFilter = new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        // 渐变 gradient
//        paint.setShader(linearGradient);
//        paint.setShader(sweepGradient);
//        paint.setShader(radialGradient);
//        canvas.drawRect(rectF, paint);



        // bitmap Shader

        // ShapeDraw形式
//        shapeDrawable.setBounds((int)rectF.left,(int)rectF.top,(int)rectF.right,(int)rectF.bottom);
//        shapeDrawable.setBounds(0,0,(int)WIDTH,(int)WIDTH);
//        shapeDrawable.draw(canvas);

        // todo 传统bitmapShader 把图形画在中间

        // MaskFilter
        // BlurMaskFilter 模糊
//        paint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(10), BlurMaskFilter.Blur.SOLID));
        // EmbossMaskFilter 浮雕
        // 光源方向/环境光强度[0,1]/炫光系数/光线返回
        paint.setMaskFilter(new EmbossMaskFilter(new float[]{0,1,1},0.8f,10,Utils.dp2px(10)));
//        canvas.drawRect(rectF,paint);
        canvas.drawBitmap(bitmap,centerX - WIDTH/2,centerY-WIDTH/2,paint);


    }
}
