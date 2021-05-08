package com.example.game.Leaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.game.Dashboard.IntroActivity;
import com.example.game.R;

public class LeaderboardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        init();
    }

    /**
     * Initialize the Spinner and other Widgets
     */
    void init(){
        textView = findViewById(R.id.placeholder);

        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.leaderboard_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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

        LeaderboardGenerator leaderboardGenerator = new LeaderboardGenerator(this, itemName);

        textView.setText(leaderboardGenerator.generateLeaderboard());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){

    }

    /**
     * It prompts the player back to the Dashboard when the button is clicked.
     * @param view ignore
     */
    public void startIntro(View view){
        startActivity(new Intent(this, IntroActivity.class));
        finish();
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){}

}
