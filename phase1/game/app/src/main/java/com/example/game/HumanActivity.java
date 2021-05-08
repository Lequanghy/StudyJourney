package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

/**
 * Code inspired by http://gamecodeschool.com/android/coding-a-snake-game-for-android/
 */


class HumanActivity extends Activity {

    HumanEngine humanEngine;
    private int health;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.health = getIntent().getIntExtra("life", -1);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        humanEngine = new HumanEngine(this, size);

        setContentView(humanEngine);
    }


    @Override
    protected void onResume() {
        super.onResume();
        humanEngine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        humanEngine.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            setResult(Activity.RESULT_OK, data);

        }
        super.finish();
    }

    public void finish(int score){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("life", health);
        startActivityForResult(intent, 1);
    }
}


