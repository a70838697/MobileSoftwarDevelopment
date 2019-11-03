package com.example.casper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.casper.data.model.Sprite;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private DrawThread drawThread;
    private ArrayList<Sprite> sprites = new ArrayList<>();

    public GameView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        sprites.add(new Sprite(context));
        sprites.add(new Sprite(context));
        sprites.add(new Sprite(context));
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (null == drawThread) {
            drawThread = new DrawThread();
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (null != drawThread) {
            drawThread.stopThread();
        }
    }

    private class DrawThread extends Thread {
        private boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            while (true) {
                try {
                    this.join();// 保证run方法执行完毕
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void run() {
            while (isRunning) {
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();
                    canvas.drawColor(Color.WHITE);

                    Paint paint = new Paint();
                    paint.setTextSize(50);
                    paint.setColor(Color.BLACK);
                    canvas.drawText("Hello", 40, 40, paint);

                    for (int i = 0; i < sprites.size(); ++i) {
                        sprites.get(i).doSomething();
                        sprites.get(i).draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != canvas) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
