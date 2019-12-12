package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DashBoard extends View {


    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("super ...");
    }

    {
        System.out.println("...");

        System.out.println("fix 1");
        System.out.println("fix 2");
        System.out.println("fix 3");

        System.out.println("fix 11");
        System.out.println("fix 22");
        System.out.println("fix 33");

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



}
