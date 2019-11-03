package com.example.casper.data.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.casper.R;

public class Sprite {
    private double direction;
    private Context context;
    private int x, y;

    public Sprite(Context context) {
        this.context = context;
        x = (int) (Math.random() * 600);
        y = (int) (Math.random() * 1200);
        direction = Math.random() * 2 * Math.PI;
    }

    public void doSomething() {
        x += 20 * Math.cos(direction);
        y += 20 * Math.sin(direction);
        if (Math.random() < 0.05) direction = Math.random() * 2 * Math.PI;
    }

    public void draw(Canvas canvas) {
        if (x < 0) x = canvas.getWidth();
        if (y < 0) y = canvas.getHeight();
        if (x > canvas.getWidth()) x = 0;
        if (y > canvas.getHeight()) y = 0;

        Drawable drawable = context.getResources().getDrawable(R.drawable.book_icon);
        Rect drawableRect = new Rect(x, y, x + drawable.getIntrinsicWidth(), y + drawable.getIntrinsicHeight());
        drawable.setBounds(drawableRect);
        drawable.draw(canvas);
    }
}
