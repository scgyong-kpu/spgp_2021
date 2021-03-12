package kr.ac.kpu.game.s1234567.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.mainTextView);
        tv.setText("Program started");

        Button helloButton = findViewById(R.id.helloButton);
        helloButton.setOnClickListener(helloButtonListener);
    }

    View.OnClickListener helloButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv = findViewById(R.id.mainTextView);
            tv.setText("Hello");
        }
    };
}