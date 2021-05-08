package com.example.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayGame extends AppCompatActivity {

    //Buttons
    Button attack1;
    Button attack2;
    Button heal;
    Button defence;
    Button pause;
    private Boolean win = false; //Notes if a fighter has won yet or not

    private void onWin(Fighter player, Fighter boss, TextView gameText, String bossWin, String playerWin) {
        attack1.setEnabled(false);
        attack2.setEnabled(false);
        heal.setEnabled(false);
        defence.setEnabled(false);
        if (player.getHp() == 0) {
            gameText.setText(bossWin);
            pause.setText("<");
        } else if (boss.getHp() == 0) {
            gameText.setText(playerWin);
            pause.setText(">");
            win = true;
        }
    }

    //Update textboxes based on last turn
    private void updateTextBox(Fighter player, Fighter boss, TextView playerHP, TextView bossHP, TextView gameText) {
        String playerHealth = "HP: " + player.getHp() + "/100";
        playerHP.setText(playerHealth);
        String bossHealth = "HP " + boss.getHp() + "/100";
        bossHP.setText(bossHealth);
        gameText.setText(boss.getEffectString(boss.getRandomChoice()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game);

        //Setup for boss and player
        final Fighter boss = new Fighter("Boss", 10, 20, 35, 30);
        final Fighter player = new Fighter("Player", 10, 20, 25, 30,
                getIntent().getIntExtra("life", -1));

        //Textbox set up
        final TextView gameText = findViewById(R.id.textView2);
        final TextView playerHP = findViewById(R.id.textView3);
        final TextView bossHP = findViewById(R.id.textView4);

        //Strings that can display when game ends
        final String bossWin = "Boss wins! You lose...";
        final String playerWin = "You won!!";

        //Update Health
        String newPlayerHealth = "HP: " + player.getHp() + "/100";
        playerHP.setText(newPlayerHealth);

        //Creating buttons + listeners
        attack1 = findViewById(R.id.button10);
        attack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 0);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                //Change textbox message and buttons if winner = true
                if (winner) {
                    onWin(player, boss, gameText, bossWin, playerWin);
                }
            }
        });

        attack2 = findViewById(R.id.button11);
        attack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 1);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                //Change textbox message if winner = true
                if (winner) {
                    onWin(player, boss, gameText, bossWin, playerWin);
                }
            }
        });

        heal = findViewById(R.id.button12);
        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 2);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                //Change textbox message if winner = true
                if (winner) {
                    onWin(player, boss, gameText, bossWin, playerWin);
                }
            }
        });

        defence = findViewById(R.id.button13);
        defence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean winner = turn(player, boss, 3);
                updateTextBox(player, boss, playerHP, bossHP, gameText);
                //Change textbox message if winner = true
                if (winner) {
                    onWin(player, boss, gameText, bossWin, playerWin);
                }
            }
        });


        pause = findViewById(R.id.button2);
        pause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish(player.getHp());
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
    public boolean turn(Fighter player, Fighter boss, int selection) {
        player.useMoveSelection(boss, selection);
        if (boss.getHp() != 0) {
            boss.useMoveRandom(player);
        }

        if (player.getHp() == 0) {
            System.out.println("Boss wins");
            return true;
        } else if (boss.getHp() == 0) {
            System.out.println("Player wins");
            return true;
        }
        return false;
    }

    public void finish(int health) {
        Intent intent = new Intent();
        intent.putExtra("life", health);
        intent.putExtra("score", getIntent().getIntExtra("score", 0));
        setResult(Activity.RESULT_OK, intent);
        super.finish();
    }
}
