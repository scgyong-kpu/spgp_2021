package kr.ac.kpu.game.s12345678.pairgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName(); // "MainActivity";
    private static final int[] buttonIds = {
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33,
    };

    private int[] cards = {
            R.mipmap.card_2c, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_3d,
            R.mipmap.card_4h, R.mipmap.card_4h, R.mipmap.card_5s, R.mipmap.card_5s,
            R.mipmap.card_as, R.mipmap.card_as, R.mipmap.card_jc, R.mipmap.card_jc,
            R.mipmap.card_qh, R.mipmap.card_qh, R.mipmap.card_kd, R.mipmap.card_kd,
    };

    private ImageButton prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Random random = new Random();
        for (int i = 0; i < cards.length; i++) {
            int ri = random.nextInt(cards.length);
            int t = cards[i];
            cards[i] = cards[ri];
            cards[ri] = t;
        }

        for (int i = 0; i < buttonIds.length; i++) {
            ImageButton b = findViewById(buttonIds[i]);
            b.setTag(cards[i]);
        }
    }

    public void onBtnCard(View view) {
        if (view == prevButton) {
            return;
        }

        int prevCard = 0;
        if (prevButton != null) {
            prevButton.setImageResource(R.mipmap.card_blue_back);
            prevCard = (Integer) prevButton.getTag();
        }

        int buttonIndex = getButtonIndex(view.getId());
        Log.d(TAG, "onBtnCard() has been called. ID=" + view.getId() + " buttonIndex=" + buttonIndex);

        int card = cards[buttonIndex];
        ImageButton imageButton = (ImageButton)view;
        imageButton.setImageResource(card);

        if (card == prevCard) {
            imageButton.setVisibility(View.INVISIBLE);
            prevButton.setVisibility(View.INVISIBLE);
            prevButton = null;
            return;
        }
        prevButton = imageButton;
    }

    private int getButtonIndex(int resId) {
        for (int i = 0; i < buttonIds.length; i++) {
            if (buttonIds[i] == resId) {
                return i;
            }
        }
        return -1;
    }
}