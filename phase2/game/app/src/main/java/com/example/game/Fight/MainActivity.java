package com.example.game.Fight;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.Dashboard.IntroActivity;

public class MainActivity extends AppCompatActivity {

    private int characterSelection = 0;

    /** Establishes starting screen including a character selection using radio buttons */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.game.R.layout.activity_main);

        RadioButton c1 = findViewById(com.example.game.R.id.radioButton);
        RadioButton c2 = findViewById(com.example.game.R.id.radioButton2);

        //Listeners for radio buttons
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                characterSelection = 0;
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                characterSelection = 1;
            }
        });
    }

    /** Initialized when start button is pressed. Sends character selection to DisplayGame. */
    public void startGame(View view) {

        Intent intent = new Intent (this, DisplayGame.class);
        //Pass radiobutton selection to DisplayGame
        intent.putExtra("character", characterSelection);
        startActivity(intent);

        finish();
    }

    /**
     * Jumps back to the dashboard whenever the back button is pressed
     */
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, IntroActivity.class));
    }
}
