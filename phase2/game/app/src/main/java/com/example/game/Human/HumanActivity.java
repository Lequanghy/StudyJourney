package com.example.game.Human;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

import com.example.game.Activity.AbstractAppCompatActivity;
import com.example.game.Dashboard.IntroActivity;


public class HumanActivity extends AbstractAppCompatActivity {

    HumanEngine humanEngine;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        nickname = intent.getStringExtra(HumanCustomActivity.EXTRA_MESSAGE);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        humanEngine = new HumanEngine(this, size);


        setContentView(humanEngine);
    }

    /**
     * Resume the activity when the user come back to the app
     */
    @Override
    protected void onResume() {
        super.onResume();
        humanEngine.resume();
    }

    /**
     * Pause the activity when the user take focus away from the app
     */
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

    @Override
    public void finish() {
        save("Human");
        startActivity(new Intent(this, IntroActivity.class));
        super.finish();
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){
        setLastActiveSession("Human");
        setScore(-1, false);
        finish();
    }
}


