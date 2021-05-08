package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(LogInActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView username2 = findViewById(R.id.username2);
        String full_message = "Username: " + message;
        username2.setText(full_message);
    }

    // Click the button play to start playing the game
    public void play_btn(View view) {

        Intent intent = new Intent(this, Trivia.class);
        intent.putExtra("life", 100);
        intent.putExtra("score", 0);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            int life = data.getIntExtra("life", -1);
            int score = data.getIntExtra("score", -1);

            String life_str = "Life: " + life;
            String score_str = "Score: " + score;

            ((TextView) findViewById(R.id.score)).setText(score_str);
            ((TextView) findViewById(R.id.health_point)).setText(life_str);
        }
    }

    @Override
    public void finish(){
        super.finish();
    }

}
