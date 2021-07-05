package jp.ac.titech.itpro.sdl.camptool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CompassView extends View {
    private Paint paint = new Paint();

    double direction = 0;

    public CompassView(Context context) {
        super(context);
    }

    public CompassView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        int cx = w / 2;
        int cy = h / 2;
        int r = Math.min(cx, cy) - 20;
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(cx, cy, r, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(cx, cy, 20, paint);
        paint.setStrokeWidth(10);
        float x = (float) (cx + r * Math.cos(direction));
        float y = (float) (cy + r * Math.sin(direction));
        canvas.drawLine(cx, cy, x, y, paint);
    }

    public void setDirection(double theta){
        direction = theta;
    }

}
