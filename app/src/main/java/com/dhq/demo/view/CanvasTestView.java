package com.dhq.demo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.dhq.demo.R;

/**
 * DESC
 * Created by douhaoqiang on 2016/9/27.
 */

public class CanvasTestView extends View {

    public CanvasTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
//        canvas.drawBitmap(getBitmap(),0,0,paint);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);

            canvas.drawBitmap(makeDst(200,200),0,0,paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawBitmap(makeSrc(200,200),100,100,paint);
            paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    private Bitmap getBitmap(){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
        Bitmap bitmap  = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
        Canvas bitmapCanvas = new Canvas(bitmap);
        //绘制元
        bitmapCanvas.drawCircle(100,100,50,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        paint.setColor(Color.BLUE);

        bitmapCanvas.drawRect(new RectF(100, 100, 200, 200), paint);
        paint.setXfermode(null);
        return bitmap;
    }


    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.BLUE);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }

}
