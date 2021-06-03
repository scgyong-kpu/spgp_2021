package kr.ac.kpu.game.agp.smoothingpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PathView extends View {
    private static final String TAG = Path.class.getSimpleName();

    public interface Listener {
        public void onAdd();
    }
    public PathView(Context context) {
        super(context);
        init();
    }
    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
    public int getCount() {
        return points.size();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(Color.BLUE);
    }

    class Point {
        float x, y;
        float dx, dy;
    }
    Listener listener;
    ArrayList<Point> points = new ArrayList<>();
    Paint paint = new Paint();
    Path path;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getPointerCount() > 1) {
                points.clear();
                return false;
            }
            Point pt = new Point();
            pt.x = event.getX();
            pt.y = event.getY();
            points.add(pt);
            buildPath();
            Log.d(TAG, "Points:" + points.size());
            if (listener != null) {
                listener.onAdd();
            }

            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) { return; }
        path = new Path();
        Point first = points.get(0);
        path.moveTo(first.x, first.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.lineTo(pt.x, pt.y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int ptCount = points.size();
        if (ptCount == 0) { return; }
        Point first = points.get(0);
        if (ptCount == 1) {
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
            return;
        }
        canvas.drawPath(path, paint);
    }

    public void clear() {
        points.clear();
        invalidate();
    }
}
