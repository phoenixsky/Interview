package com.example.view.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.view.Utils;

public class MeshDrawable extends Drawable {

    private static final int INTERVAL = (int) Utils.dp2px(30);

    private Paint paint;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(Utils.dp2px(1));
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();

        for (int x = 0; x < bounds.right - bounds.left; x += INTERVAL) {
            canvas.drawLine(x, 0, x, bounds.bottom, paint);
        }
        for (int y = 0; y < bounds.bottom - bounds.top; y += INTERVAL) {
            canvas.drawLine(0, y, bounds.right, y, paint);
        }

    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha() == 0 ? PixelFormat.TRANSPARENT :
                paint.getAlpha() == 0xff ? PixelFormat.OPAQUE : PixelFormat.TRANSLUCENT;
    }
}
