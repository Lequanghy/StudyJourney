package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The Main Operation of the Trivia Game
 */
public class Trivia extends AppCompatActivity {
    /**
     * The amount of life that the player has.
     */
    public int life = 100;

    /** The amount of score that the player has. */
    public int score = 0;

    /** The name of the song. */
    public String music;

    @Override
    /**
     * Override the onCreate method
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Display the playing screen*/
        setContentView(R.layout.activity_trivia);
    }

    /**
     * Display all the information of the player on the screen.
     * Display the type of music to be chosen.
     */
    public void startQuiz(View view) {

        /** Check if the music button has selected*/
        if (music == null) {
            Toast.makeText(this, "No Music Has Been Chosen", Toast.LENGTH_SHORT).show();
            return;
        }
        /** Create intent object to transfer the display from one screen to another*/
        Intent intent = new Intent(this, QuizActivity.class);

        /** Transfer all the variables to the playing screen*/
        intent.putExtra("life", life);
        intent.putExtra("score", score);
        intent.putExtra("music", music);
        startActivityForResult(intent, 1);
    }

    /** Play Mii Music*/
    public void musicMii(View view) {
        music = "miimusic";
    }

    /** Play Wii Music*/
    public void musicWii(View view) {
        music = "wiishopmusic";
    }

    @Override
    /**
     *
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            setResult(RESULT_OK, data);
        }
        super.finish();
    }

    @Override
    /**
     * End the screen
     */
    public void finish(){
        Intent intent = new Intent();

        /** Transfers the variables*/
        intent.putExtra("life", life);
        intent.putExtra("score", score);
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }

    @Override
    /**
     * The game ends when the back button is clicked.
     */
    public void onBackPressed(){finish();}
}
