package jp.ac.titech.itpro.sdl.camptool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class SlopeView extends View {
    private Paint paint = new Paint();
    private Bitmap bmp, bmp2;
    private Rect imagesize, imagesize2;
    private double degree = 0,length=0;
    public SlopeView(Context context) {

        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.slope);
        imagesize = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        imagesize2 = new Rect(0,0,bmp2.getWidth(),bmp2.getHeight());
        //drawsize = new
    }

    public SlopeView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.slope);
        imagesize = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        imagesize2 = new Rect(0,0,bmp2.getWidth(),bmp2.getHeight());
    }

    public SlopeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.slope);
        imagesize = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
        bmp2 = BitmapFactory.decodeResource(getResources(), R.drawable.circle);
        imagesize2 = new Rect(0,0,bmp2.getWidth(),bmp2.getHeight());
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int cx = w/2;
        int cy = h/2;
        int r = Math.min(cx,cy);
        canvas.translate(cx,cy);
        canvas.rotate((float) degree);
        if(bmp!=null){
            if(length >4.24) {
                canvas.drawBitmap(bmp, imagesize, new Rect(-r, -r, r, r), paint);
            }else{
                canvas.drawBitmap(bmp2, imagesize2, new Rect(-r, -r, r, r), paint);
            }
        }
    }

    public void setDegree(double d,double l){
        degree = d;
        length = l;
        invalidate();
    }

}
