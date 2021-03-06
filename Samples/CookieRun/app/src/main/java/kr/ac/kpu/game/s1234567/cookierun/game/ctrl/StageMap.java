package kr.ac.kpu.game.s1234567.cookierun.game.ctrl;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kr.ac.kpu.game.s1234567.cookierun.framework.game.BaseGame;
import kr.ac.kpu.game.s1234567.cookierun.framework.iface.GameObject;
import kr.ac.kpu.game.s1234567.cookierun.framework.view.GameView;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.main.MainGame;
import kr.ac.kpu.game.s1234567.cookierun.game.scenes.main.MainScene;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Jelly;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Obstacle;
import kr.ac.kpu.game.s1234567.cookierun.game.objs.Platform;

public class StageMap implements GameObject {
    public static int UNIT_SIZE = 70;
    private static final String TAG = StageMap.class.getSimpleName();
    private final ArrayList<String> lines = new ArrayList<String>();
    private int columns;
    private int rows;
    private int current;

    public StageMap(String filename) {
        AssetManager assets = GameView.view.getContext().getAssets();
        try {
            InputStream is = assets.open(filename);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String header = reader.readLine();
            String[] comps = header.split(" ");
            columns = Integer.parseInt(comps[0]);
            rows = Integer.parseInt(comps[1]);
            UNIT_SIZE = (int) Math.ceil(GameView.view.getHeight() / rows / GameView.MULTIPLIER);
            Log.d(TAG, "Col=" + columns + " Row="  + rows + " UnitSize=" + UNIT_SIZE);
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                Log.d(TAG,  "-row=" + line);
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update() {
        MainGame game = (MainGame) BaseGame.get();
        xPos -= Platform.SPEED * GameView.MULTIPLIER * game.frameTime;
        float vw = GameView.view.getWidth();
        while (xPos < vw) {
            //Log.d(TAG, "xPos=" + xPos + " column=" + current);
            createColumn();
            xPos += UNIT_SIZE * GameView.MULTIPLIER;
        }
    }

    private float xPos;
    private void createColumn() {
        float y = 0;
        for (int row = 0; row < rows; row++) {
            char ch = getAt(current, row);
            createObject(ch, xPos, y);
            y += UNIT_SIZE * GameView.MULTIPLIER;
        }
        current++;
    }

    private void createObject(char ch, float x, float y) {
        MainGame game = (MainGame) BaseGame.get();
        MainScene scene = MainScene.scene;
        if (ch >= '1' && ch <= '9') {
            Jelly item = new Jelly(ch - '1', x, y);
            scene.add(MainScene.Layer.item, item);
        } else if (ch >= 'O' && ch <= 'Q') {
            Platform platform = new Platform(Platform.Type.values()[ch - 'O'], x, y);
            scene.add(MainScene.Layer.platform, platform);
        } else if (ch >= 'X' && ch <= 'Z') {
            Obstacle obstacle = new Obstacle(ch, x, y);
            scene.add(MainScene.Layer.obstacle, obstacle);
            //Log.d(TAG, "obstacle = " + obstacle);
        }
    }

    private char getAt(int x, int y) {
        try {
            int lineIndex = x / columns * rows + y;
            String line = lines.get(lineIndex);
            return line.charAt(x % columns);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isDone() {
        int lineIndex = current / columns * rows;
        return lines.size() <= lineIndex;
    }
//        ArrayList<GameObject> objects = game.objectsAt(MainGame.Layer.platform);
//        float rightMost = 0;
//        for (GameObject obj: objects) {
//            Platform platform = (Platform) obj;
//            float right = platform.getRight();
//            if (rightMost < right) {
//                rightMost = right;
//            }
//        }
//        float vw = GameView.view.getWidth();
//        float vh = GameView.view.getHeight();
//        if (rightMost < vw) {
//            Log.d(TAG, "create a Platform here !! @" + rightMost + " Platforms=" + objects.size());
//            float tx = rightMost, ty = vh - Platform.Type.T_2x2.height();
//            Platform platform = new Platform(Platform.Type.RANDOM, tx, ty);
//            game.add(MainGame.Layer.platform, platform);
//
//            Random r = new Random();
//            game.add(MainGame.Layer.item, new Jelly(r.nextInt(60), tx, r.nextInt((int) ty)));
//        }
//    }

    @Override
    public void draw(Canvas canvas) {
    }
}
