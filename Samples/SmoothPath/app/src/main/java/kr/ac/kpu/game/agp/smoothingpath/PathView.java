package kr.ac.kpu.game.agp.smoothingpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathView extends View {
    private static final String TAG = Path.class.getSimpleName();

    public PathView(Context context) {
        super(context);
        init();
    }
    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(Color.BLUE);
    }

    ArrayList<PointF> points = new ArrayList<>();
    Paint paint = new Paint();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            PointF pt = new PointF();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            Log.d(TAG, "Points:" + points.size());

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int ptCount = points.size();
        if (ptCount < 2) {
            return;
        }
        Path path = new Path();
        path.moveTo(points.get(0).x, points.get(0).y);
        for (int i = 1; i < ptCount; i++) {
            PointF pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
        canvas.drawPath(path, paint);
    }

    Point x;

    public void clear() {
        points.clear();
        invalidate();
    }
}
