package kr.ac.kpu.game.agp.tiledsample;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.RequiresApi;

import kr.ac.kpu.game.agp.tiledsample.tile.TiledMap;

public class TileView extends View {

    private static final String TAG = TileView.class.getSimpleName();
    private int tilesetResource;
    private int mapResource;
    private int sizeFactor;
    private TiledMap map;
    private Point screenSize = new Point();
    private int yDiff;

    private void postFrameCallback() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                update(frameTimeNanos);
                invalidate();
                postFrameCallback();
            }
        });
    }

    public TileView(Context context) {
        super(context);
        init();
    }
    public TileView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attributeSet,
                R.styleable.TileView,
                0, 0);

        try {
            mapResource = a.getResourceId(R.styleable.TileView_map, 0);
            tilesetResource = a.getResourceId(R.styleable.TileView_tileset, 0);
            sizeFactor = a.getInt(R.styleable.TileView_sizeFactor, 16);
        } finally {
            a.recycle();
        }
        init();
    }
    protected void init() {
//        Point screenSize = new Point();
        Display display = getContext().getDisplay();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(screenSize);
        } else {
            display.getSize(screenSize);
        }
        int tileSize = screenSize.x / sizeFactor;
        Log.d(TAG, "dstTileWith=" + tileSize);
        if (screenSize.x % sizeFactor > 0) {
            tileSize++;
            Log.d(TAG, "dstTileWith=" + tileSize);
        }
        map = TiledMap.load(getResources(), mapResource, true);
        map.loadImage(getResources(), new int[] { tilesetResource }, tileSize, tileSize);
        Log.d(TAG, "Done?");

        postFrameCallback();
    }

    public static final int FPS_DURATION = 10;
    private int count;
    private IndexTimer timer = new IndexTimer(FPS_DURATION, 1);
    public void update(long frameTimeNanos) {
//        gameWorld.update(frameTimeNanos);
        IndexTimer.currentTimeNanos = frameTimeNanos;

        count++;
        if (timer.done()) {
            Log.d(TAG, "Frame Count = " + (count / (float)FPS_DURATION));
            count = 0;
            timer.reset();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        map.draw(canvas);
    }

    PointF ptDown = new PointF();
    ObjectAnimator objectAnimator;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int endScroll = map.getFullHeight();
//        Log.d(TAG, "EndScroll = " + endScroll);
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            // y dy
            ptDown.x = event.getX();
            ptDown.y = event.getY();
            yDiff = (int) (map.getY() + ptDown.y);
//            Log.d(TAG, "D yDiff=" + yDiff + " eventY=" + event.getY() + " mapY=" + map.getY());
            objectAnimator = ObjectAnimator.ofInt(map, "y", map.getY(), endScroll);
            objectAnimator.setDuration(3000);
            objectAnimator.setInterpolator(new AccelerateInterpolator());
            objectAnimator.start();
        } else if (action == MotionEvent.ACTION_MOVE) {
            // -ey + dy + y
            objectAnimator.cancel();
//            Log.d(TAG, "M yDiff=" + yDiff + " eventY=" + event.getY() + " mapY=" + map.getY());
            map.setY((int) (yDiff - event.getY()));
        }
        return true;
    }
}
