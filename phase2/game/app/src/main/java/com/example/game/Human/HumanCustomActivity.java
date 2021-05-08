package com.example.game.Human;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.game.Dashboard.IntroActivity;
import com.example.game.R;

public class HumanCustomActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.game.HUMAN.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_human_custom);
    }
    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, HumanActivity.class);
        EditText editText = (EditText) findViewById(R.id.name);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /**
     * Jumps back to the dashboard whenever the back button is pressed
     */
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, IntroActivity.class));
    }

}
