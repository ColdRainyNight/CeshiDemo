package com.ceshidemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @date:
 * @author: 
 * @effect:
 * @thinking:
 */

public class ItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private int with;

    public ItemDecoration(Context context) {
        this.context = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        with = displayMetrics.widthPixels;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int bottom = view.getBottom();
            c.drawLine(0, bottom, with, bottom, paint);
        }
    }
}
