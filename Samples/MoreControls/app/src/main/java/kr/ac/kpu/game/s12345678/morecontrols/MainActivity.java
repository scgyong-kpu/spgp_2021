package kr.ac.kpu.game.s12345678.morecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox firewallCheckbox;
    private TextView outTextView;
    private EditText userEditText;
    private TextView editTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firewallCheckbox = findViewById(R.id.checkbox);
        outTextView = findViewById(R.id.outTextView);
        userEditText = findViewById(R.id.userEditText);
        editTextView = findViewById(R.id.editTextView);
    }

    public void onBtnApply(View view) {
        boolean checked = firewallCheckbox.isChecked();
        String text = checked ? "Using Firewall" : "Not using Firewall";
        outTextView.setText(text);

        String user = userEditText.getText().toString();
        editTextView.setText("User info = " + user);
    }

    public void onCheckFirewall(View view) {
        boolean checked = firewallCheckbox.isChecked();
        String text = checked ? "Checked Firewall" : "Unchecked Firewall";
        outTextView.setText(text);
    }
}