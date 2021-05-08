package com.example.game.Trivia;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.game.Activity.AbstractAppCompatActivity;
import com.example.game.Dashboard.IntroActivity;
import com.example.game.PlayerManager.QuizPlayer;
import com.example.game.R;

import java.util.LinkedList;

/** The screen where the player plays the game. */

public class QuizActivity extends AbstractAppCompatActivity implements QuizInterface{
  private TextView questionText;
  private TextView countDownText;
  private TextView levelText;
  private LinkedList<RadioButton> radioButtons;
  private RadioGroup radioGroup;
  private Button button;
  private QuizBundleHandler quizBundleHandler;
  private QuizPlayer quizPlayer;
  private Timer timer;

  private int level = 1;
  private int streak = 0;

  private boolean isFinished;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    InitializeComponents(true);
    setLastActiveSession("");

    isFinished = false;
    radioButtons = new LinkedList<>();
    InitializeWidgets();

    quizBundleHandler = new QuizBundleHandler(
            QuestionControllerManager.getQuestionAllController(this).getQuestionControllers(),
            radioButtons,
            radioGroup,
            this
    );

    quizPlayer = new QuizPlayer(this, getIntent().getStringExtra("music"));
    quizPlayer.startPlayer();

    timer = new Timer(30000, this);

    quizBundleHandler.update(questionText);
  }

  /**
   * Initialize the GUI widgets
   */
  private void InitializeWidgets() {
    questionText = findViewById(R.id.text_view_question);
    countDownText = findViewById(R.id.text_view_countdown);
    levelText = findViewById(R.id.LevelText);

    radioGroup = findViewById(R.id.radio_group);

    radioButtons.add((RadioButton) findViewById(R.id.radio_button_1));
    radioButtons.add((RadioButton) findViewById(R.id.radio_button_2));
    radioButtons.add((RadioButton) findViewById(R.id.radio_button_3));

    button = findViewById(R.id.button_confirm);
  }

  /**
   * Pause button's event handler
   * @param view view
   */
  public void pauseEvent(View view){
    onPause();
  }

  /**
   * Event handler for the button that checks when the user chooses an option
   * @param view view
   */
  public void buttonEvent(View view) {

    if (button.getText().toString().toLowerCase().equals("confirm")) {
      if (radioGroup.getCheckedRadioButtonId() != -1) {
        RadioButton selectedAnswer = findViewById(radioGroup.getCheckedRadioButtonId());
        boolean correct = quizBundleHandler.checkAnswer(selectedAnswer);

        if (correct){
          quizBundleHandler.updateScore(this, timer.elapsedTime(), level);
        }
          int[] levelStreak = quizBundleHandler.updateLevel(levelText, streak, level, correct);
          level = levelStreak[0];
          streak = levelStreak[1];

          System.out.println(levelText.getText().toString());
          System.out.println("Level, Streak: " + level + " " + streak);

        if (quizBundleHandler.checkFinish()){
          button.setText(R.string.next);
        }
        else{
          button.setText(R.string.finish);
        }

        quizBundleHandler.displaySolution();

      } else {
        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
      }
      return;
    }
    else if (button.getText().toString().toLowerCase().equals("next")){
      quizBundleHandler.update(questionText);
      timer.resetTimer();
      button.setText(R.string.confirm);
      quizBundleHandler.resetAnswer();
      return;
    }
    finish();
  }

  /**
   * Sets the timer's text
   * @param time text format
   */
  @Override
  public void updateTimer(String time) {

    if (time == null){
      quizBundleHandler.displaySolution();
      if (quizBundleHandler.checkFinish()){
        button.setText(R.string.next);
      }
      else{
        button.setText(R.string.finish);
      }
      return;
    }
    countDownText.setText(time);
  }

  /**
   * Event Handler when the game is paused
   */
  @Override
  public void onPause(){
    quizPlayer.pausePlayer();
    timer.cancelTimer();

    if (!isFinished){
      pauseDialog();
    }
    super.onPause();
  }

  /**
   * Pause Dialog for the onPause event
   */
  private void pauseDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage("Do you wish to continue?").setTitle("Pause");
    builder.setPositiveButton("Continue",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                timer.resumeTimer();
                quizPlayer.startPlayer();
                onResume();
              }
            });
    builder.setNegativeButton("Quit",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setLastActiveSession("Trivia");
                setScore(-1);
                finish();
              }
            });
    AlertDialog dialog = builder.create();
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
  }

  /**
   * Finish the game
   */
  @Override
  public void finish(){
    isFinished = true;
    timer.cancelTimer();
    quizPlayer.stopPlayer();
    save("Trivia");
    super.finish();
    startActivity(new Intent(this, IntroActivity.class));
  }

  /**
   * Disable back button
   */
  @Override
  public void onBackPressed(){}

}
