package com.example.view.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabLayout extends ViewGroup {


    List<Rect> childrenBounds = new ArrayList<>();


    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    {
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);


        // 当前行已使用的宽度
        int lineWidthUsed = 0;
        // view的宽度
        int lineMaxWidth = 0;
        // 已使用的高度
        int heightUsed = 0;
        // 当前行最高的view的高度
        int lineMaxHeight = 0;


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);

            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            // 宽度不够需要换行
            if (specMode != MeasureSpec.UNSPECIFIED && specSize - lineWidthUsed < child.getMeasuredWidth()) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            getBounds(i).set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());

            // 行内已使用的宽度
            lineWidthUsed += child.getMeasuredWidth();
            lineMaxWidth = Math.max(lineWidthUsed, lineMaxWidth);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }
        // 宽度为所有行中最宽一个
        // 高度为已使用的高度加上最后一行的高度
        setMeasuredDimension(lineMaxWidth, heightUsed + lineMaxHeight);
    }

    Rect getBounds(int i) {
        if (childrenBounds.size() <= i) {
            childrenBounds.add(new Rect());
        }
        return childrenBounds.get(i);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childrenBounds.get(i).left,
                    childrenBounds.get(i).top,
                    childrenBounds.get(i).right,
                    childrenBounds.get(i).bottom
            );
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        super.onDraw(canvas);
    }


}
