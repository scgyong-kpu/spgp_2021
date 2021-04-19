package kr.ac.kpu.game.s1234567.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import kr.ac.kpu.game.s1234567.dragonflight.framework.GameObject;
import kr.ac.kpu.game.s1234567.dragonflight.ui.view.GameView;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 5.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float time;
    private float spawnInterval;

    public EnemyGenerator() {
        time = 0.0f;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        time += game.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        //Log.d(TAG, "Generate now !!");
        MainGame game = MainGame.get();
        int sixth = GameView.view.getWidth() / 6;
        for (int i = 1; i <= 5; i++) {
            int x = sixth * i;
            int y = 0;
            Enemy enemy = new Enemy(x, y, 700);
            game.add(enemy);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}
