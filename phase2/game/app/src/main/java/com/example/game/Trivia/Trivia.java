package com.example.game.Trivia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.game.Activity.AbstractAppCompatActivity;
import com.example.game.Dashboard.IntroActivity;
import com.example.game.R;

/**
 * The Main Operation of the Trivia Game
 */
public class Trivia extends AbstractAppCompatActivity implements AdapterView.OnItemSelectedListener{

    /** The name of the song. */
    public String music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trivia);
        InitializeComponents(false);
        init();
    }

    void init(){
        Spinner spinner = findViewById(R.id.musicSelection);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.musicSelectionTrivia_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    /**
     * Display all the information of the player on the screen.
     * Display the type of music to be chosen.
     */
    public void startQuiz(View view) {

        if (music == null) {
            Toast.makeText(this, "No Music Has Been Chosen", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("music", music);
        startActivity(intent);

        finish();
    }

    public void startQuit(View view){
        startActivity(new Intent(this, IntroActivity.class));
        finish();
    }

    /**
     * It's called whenever an item from the drop down menu is selected
     * @param parent the spinner object
     * @param view ignore
     * @param pos the position of the selected item
     * @param id the id of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        String itemName = parent.getItemAtPosition(pos).toString();
        if (itemName.equals("Relax Music")){
            music = "miimusic";
            return;
        }
        music = "wiishopmusic";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }
    /**
     * Finish this Activity
     */
    @Override
    public void finish(){
        super.finish();
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){}
}
