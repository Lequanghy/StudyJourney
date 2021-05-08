package com.example.game.Fight;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.game.Activity.AbstractAppCompatActivity;
import com.example.game.Dashboard.IntroActivity;
import com.example.game.R;

public class DisplayGame extends AbstractAppCompatActivity {

    //Buttons
    Button attack1;
    Button attack2;
    Button heal;
    Button defence;
    Button pause;

    private int level = 1; //Current level of the game
    private int moves = 0; //Moves used by player during a level

    private MediaPlayer background;

    /**
     * Helper function used by the main game to handle win condition protocol
     * 
     * @param player: The fighter object controlled by the player
     * @param boss: The fighter object controlled by the computer
     * @param gameText: The textbox that displays events that have happened
     */
    private void onWin(Fighter player, Fighter boss, TextView gameText) {
        level++;

        //Turn off buttons if all levels are complete or player loses
        if (level == 4 || player.getHp() == 0) {
            attack1.setEnabled(false);
            attack2.setEnabled(false);
            heal.setEnabled(false);
            defence.setEnabled(false);
        }

        TextView levelDisplay = findViewById(com.example.game.R.id.textView6);
        TextView scoreDisplay = findViewById(com.example.game.R.id.textView7);

        if (player.getHp() == 0){
            String text = "Boss wins! You lose...";
            gameText.setText(text);
            finishDialog();
        }else if (boss.getHp() == 0) {
            int score = player.getHp() - moves;
            if (score < 0){
                score = 0;
            }
            moves = 0;

            if (level != 4) {
                String levelText = "Level " + level;
                levelDisplay.setText(levelText);

                String text = "You won!! Moving onto Level " + level;
                gameText.setText(text);

                setScore(score, false);
                String s = "Score: " + getScore();
                scoreDisplay.setText(s);
                runGame();
            }
            else {
                String text = "YOU WON!!";
                gameText.setText(text);

                setScore(score, false);
                String s = "Score: " + getScore();
                scoreDisplay.setText(s);
                finishDialog();
            }
            //Boss defeat sound
            MediaPlayer oof2 = MediaPlayer.create(this, com.example.game.R.raw.oof2);
            oof2.start();
        }
    }

    /**
     * Updates textboxes based on the last turn
     * @param player: The Fighter object controlled by the player
     * @param boss: The Fighter object controlled by the computer
     * @param playerHP: Textbox displaying player's HP
     * @param bossHP: Textbox displaying boss' HP
     * @param gameText: Textbox displaying events that have happened
     */
    private void updateTextBox(Fighter player, Fighter boss, TextView playerHP, TextView bossHP, TextView gameText){
        String playerHealth = "HP: " + player.getHp() + "/100";
        playerHP.setText(playerHealth);
        String bossHealth = "HP " + boss.getHp() + "/" + boss.getMaxHp();
        bossHP.setText(bossHealth);
        gameText.setText(boss.getEffectString(boss.getRandomChoice()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.game.R.layout.activity_display_game);

        //Change character image if 2nd character was selected
        int characterSelection = getIntent().getIntExtra("character", 0);
        if (characterSelection == 1){
            ImageView character = findViewById(com.example.game.R.id.imageView);
            character.setImageResource(com.example.game.R.drawable.c2);
        }

        InitializeComponents(false);
        background = MediaPlayer.create(this, R.raw.fightmusic);
        setLastActiveSession("");
        runGame();

    }

    /**
     * The game's main method
     */
    private void runGame(){
        //Media player for oof sound effect whenever the boss is attacked
        final MediaPlayer oof1 = MediaPlayer.create(this, com.example.game.R.raw.oof1);

        //Media player for background music
//        final MediaPlayer background = MediaPlayer.create(this, R.raw.fightmusic);
        background.start();

        //Setup for boss and player
        final Fighter boss = new Fighter("Boss", 10, 20, 35, 30);
        final Fighter player = new Fighter("Player", 10, 20, 25, 30,
                getIntent().getIntExtra("life", 100));

        //Change boss and update textboxes based on level
        if (level == 2){
            boss.setHp(125);
            boss.setMaxHp(125);
        } if (level == 3){
            boss.setHp(150);
            boss.setMaxHp(150);
        }

        //Textbox set up
        final TextView gameText = findViewById(com.example.game.R.id.textView2);
        final TextView playerHP = findViewById(com.example.game.R.id.textView3);
        final TextView bossHP = findViewById(com.example.game.R.id.textView4);

        //Make sure hp display is correct
        String playerHealth = "HP: " + player.getHp() + "/100";
        playerHP.setText(playerHealth);
        String bossHealth = "HP " + boss.getHp() + "/" + boss.getMaxHp();
        bossHP.setText(bossHealth);

        //Update Health
        final String newPlayerHealth = "HP: " + player.getHp() + "/100";
        playerHP.setText(newPlayerHealth);

        //Text box for moves used
        final TextView movesText = findViewById(com.example.game.R.id.textView8);
        //Default test
        final String noMoves = "Moves Used: 0";

        //Creating buttons + listeners
        attack1 = findViewById(com.example.game.R.id.button10);
        attack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 0);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                oof1.start();

                moves++;
                String m = "Moves Used: " + moves;
                movesText.setText(m);

                //Change textbox message and buttons if winner = true
                if (winner){
                    movesText.setText(noMoves);
                    background.stop();
                    onWin(player, boss, gameText);
                }
            }
        });

        attack2 = findViewById(com.example.game.R.id.button11);
        attack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 1);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                oof1.start();

                moves++;
                String m = "Moves Used: " + moves;
                movesText.setText(m);

                //Change textbox message if winner = true
                if (winner){
                    movesText.setText(noMoves);
                    background.stop();
                    onWin(player, boss, gameText);
                }
            }
        });

        heal = findViewById(com.example.game.R.id.button12);
        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 2);
                updateTextBox(player, boss, playerHP, bossHP, gameText);

                moves++;
                String m = "Moves Used: " + moves;
                movesText.setText(m);

                //Change textbox message if winner = true
                if (winner){
                    movesText.setText(noMoves);
                    background.stop();
                    onWin(player, boss, gameText);
                }
            }
        });

        defence = findViewById(com.example.game.R.id.button13);
        defence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 3);
                updateTextBox(player, boss, playerHP, bossHP, gameText);

                moves++;
                String m = "Moves Used: " + moves;
                movesText.setText(m);

                //Change text box message if winner = true
                if (winner){
                    movesText.setText(noMoves);
                    background.stop();
                    onWin(player, boss, gameText);
                }
            }
        });


        pause = findViewById(com.example.game.R.id.button2);
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                finish(player.getHp());
                pauseDialog();
                background.pause();
            }
        });
    }

    /*** Used every time a button is pressed by the player. Allows the fighters to take their turns
     *
     * @param player: the player character
     * @param boss: the boss character
     * @param selection: the move button chosen by the player
     * @return true if one of the fighters has won the battle
     */
    public boolean turn(Fighter player, Fighter boss, int selection){
        player.useMoveSelection(boss, selection);
        if (boss.getHp() != 0) {
            boss.useMoveRandom(player);
        }
        if (player.getHp() == 0){
            System.out.println("Boss wins");
            return true;
        }else if (boss.getHp() == 0) {
            System.out.println("Player wins");
            return true;
        }
        return false;
    }

    /**
     * Pause menu method
     */
    private void pauseDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to continue?").setTitle("Pause");
        builder.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                        onResume();
                    }
                });
        builder.setNegativeButton("Quit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setLastActiveSession("Fight");
                        setScore(-1, false);
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * Method containing win condition protocol
     */
    private void finishDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Game Completed").setTitle("Game completed");
        builder.setPositiveButton("Finish",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                        background.stop();
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /**
     * Helper function for finishDialogue
     */
    public void finish(){
        save("Fight");
        super.finish();
        startActivity(new Intent(this, IntroActivity.class));

    }

    /**
     * Disable back button
     */
    @Override
    public void onBackPressed(){}
}