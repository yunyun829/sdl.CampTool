package jp.ac.titech.itpro.sdl.camptool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CompassView extends View {
    private Paint paint = new Paint();
    private Bitmap bmp;
    double direction = 0;
    double degree = 0;
    private Rect imagesize;

    public CompassView(Context context) {

        super(context);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
        imagesize = new Rect(0,0,bmp.getWidth(),bmp.getHeight());
        //drawsize = new
    }

    public CompassView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.compass);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        int r = Math.min(cx, cy) - 20;
        int l = 0;
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(cx, cy, r, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(cx, cy, 20, paint);
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        float x = (float) (cx + r * Math.cos(direction));
        float y = (float) (cy + r * Math.sin(direction));
        canvas.translate(cx,cy);
        for (int i=0;i<360;i+=15){
            if(i%45==0){
                l=50;
            }else{
                l=30;
            }
            canvas.drawLine(-r+l, 0, -r, 0, paint);
            canvas.rotate(15);
        }
        canvas.rotate((float) degree);
        if(bmp != null) {
            canvas.drawBitmap(bmp, imagesize, new Rect(-r, -r, r, r), paint);
        }else {
            Log.d("","cannot draw image!");
        }
    }

    public void setDirection(double theta){
        direction = theta - Math.PI / 2;
        degree = Math.toDegrees(direction);
        invalidate();
    }

}
