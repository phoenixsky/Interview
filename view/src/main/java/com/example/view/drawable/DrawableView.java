package com.example.view.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawableView extends View {


    private ColorDrawable colorDrawable;
    private MeshDrawable meshDrawable;

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        colorDrawable = new ColorDrawable(Color.RED);
        meshDrawable = new MeshDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        colorDrawable.setBounds(0, 0, w, h);
        meshDrawable.setBounds(0,0,w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        colorDrawable.draw(canvas);
        meshDrawable.draw(canvas);
    }
}
