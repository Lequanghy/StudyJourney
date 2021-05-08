package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int characterSelection = 1;
    RadioButton c1;
    RadioButton c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = findViewById(R.id.radioButton);
        c2 = findViewById(R.id.radioButton2);
    }

    public void startGame(View view) {

        int score = getIntent().getIntExtra("score", -1);
        int health = getIntent().getIntExtra("life", -1);

        Intent intent = new Intent(this, DisplayGame.class);
        intent.putExtra("score", score);
        intent.putExtra("life", health);
        startActivityForResult(intent, 1);
    }

    //Change characterSelection if a button is clicked, used each time a button is clicked
    public void onRadioButtonClicked(View view) {

        // Check which radio button was clicked
        if (view.getId() == R.id.radioButton){
            characterSelection = 0;
            System.out.println("Character set to 1");
        } else {
            characterSelection = 1;
            System.out.println("Character set to 2");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data);
        }
        super.finish();
    }

}
