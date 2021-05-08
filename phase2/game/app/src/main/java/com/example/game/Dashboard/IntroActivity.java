package com.example.game.Dashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.game.Activity.AbstractAppCompatActivity;
import com.example.game.Leaderboard.LeaderboardActivity;
import com.example.game.Human.HumanCustomActivity;
import com.example.game.Login.LogInActivity;
import com.example.game.R;
import com.example.game.Trivia.Trivia;
import com.example.game.UserOperation.UserControllerManager;
import com.example.game.Fight.MainActivity;

public class IntroActivity extends AbstractAppCompatActivity {

    /**
     * Initializes the logged in user controller
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        InitializeComponents(false);

        if (getUserControllerIsEmpty()){
            setUserController(UserControllerManager.getUserController(
                    this,
                    getIntent().getStringExtra("key")));
        }

        String welcomeString = "Welcome: " + getUsername();
        ((TextView) findViewById(R.id.username)).setText(welcomeString);

        if (getIntent().getBooleanExtra("sessionExist", false)){
            existSessionDialog();
        }

    }

    /**
     * Dialog for user when they have an unfinished session
     */
    private void existSessionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Previous game session found. Do you want to restart the previous game session?")
                .setTitle("Previous game session found");

        builder.setPositiveButton("Restart",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startGameSession(getLastActiveSession());
                    }
                });

        builder.setNegativeButton("Quit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setLastActiveSession("");
                        save("");
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

    /**
     * Dialog for the user to choose the game mode
     */
    private void chooseGameModeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose a Game Mode")
                .setItems(R.array.gameMode_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (which == 0){
                            startGameSession("Trivia");
                        }else if (which == 1){
                            startGameSession("Human");
                        }else if (which == 2){
                            startGameSession("Fight");
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Start the game session based on the inputted choice
     * @param session game session's name
     */
    public void startGameSession(String session){
        if (session.equals("Trivia")){
            startActivity(new Intent(this, Trivia.class));
        } else if (session.equals("Human")){
            startActivity(new Intent(this, HumanCustomActivity.class));
        } else  if (session.equals("Fight")){
            startActivity(new Intent(this, MainActivity.class));
        } else if (session.equals("Leaderboard")){
            startActivity(new Intent(this, LeaderboardActivity.class));
        } else if (session.equals("Logout")){
            startActivity(new Intent(this, LogInActivity.class));
        }
        finish();
    }


    /**
     * Event handler for the play button
     * @param view view
     */
    public void startGameMode(View view){chooseGameModeDialog();}

    /**
     * Event handler for the leader board button
     * @param view view
     */
    public void startLeaderboard(View view){
        startGameSession("Leaderboard");
    }

    /**
     * Event handler for the log out button
     * @param view view
     */
    public void startLogout(View view){
        setUserController(null);
        startGameSession("Logout");
    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){}

}
