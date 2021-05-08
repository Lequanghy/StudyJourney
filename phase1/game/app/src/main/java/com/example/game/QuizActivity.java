package com.example.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * The screen where the player plays the game.
 */
public class QuizActivity extends AppCompatActivity {

  /**
   * Indicates the text that are displayed on the screen
   */
  //Questions text
  private TextView textViewQuestion;
  //Life text
  private TextView textViewLife;
  //Score text
  private TextView textViewScore;
  //Question number text
  private TextView textViewQuestionNumber;
  //Countdown time text
  private TextView textViewCountDown;

    /**
     * Time limit
     */
    private final long TIME_LIMIT = 30000;
    /**
     * Indicates the key for life and score
     */
    private final String life_key = "life";
    /**
     * Music player
     */
    MediaPlayer player;
    /**
     * Buttons that are on the screen
     */
    private RadioGroup rbGroup;
    // First answer option
    private RadioButton rb1;
    // Second answer option
  private RadioButton rb2;
    // Third answer option
  private RadioButton rb3;
  // Confirm buttion
  private Button buttonConfirmNext;
  /** Default color*/
  private ColorStateList textColorDefaultRb;
    /** Indicates if the answer correct*/
    private boolean correct;
  /** List of questions*/
  private List<Question> questionList;
    /** The number of the current question*/
    private int questionCurrent;
  /** The amount of the question*/
  private int questionAmountTotal = 10;
    /**
     * Countdown Timer
     */
    private CountDownTimer cdt;
    /**
     * Current time
     */
    private long timeCurrent;
    private final String score_key = "score";
    /** Question*/
  private Question questionLabel;
    private int score;
    /** The variables*/
  private int life;
    /**
     * Indicates the name of the song
     */
    private String music;

  @Override
  /**
   * Create the playing screen
   */
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);

    // Update Layout Variables
    textViewQuestion = findViewById(R.id.text_view_question);
    textViewLife = findViewById(R.id.text_view_life);
    textViewScore = findViewById(R.id.text_view_score);
    textViewCountDown = findViewById(R.id.text_view_countdown);
    textViewQuestionNumber = findViewById(R.id.text_view_question_num);
    rbGroup = findViewById(R.id.radio_group);
    rb1 = findViewById(R.id.radio_button_1);
    rb2 = findViewById(R.id.radio_button_2);
    rb3 = findViewById(R.id.radio_button_3);
    buttonConfirmNext = findViewById(R.id.button_confirm);

    // Update Color Variables
    textColorDefaultRb = rb1.getTextColors();

    // Update Question List Variables
    QuizDbHelper dbHelper = new QuizDbHelper(this);
    questionList = dbHelper.getQuestionList();
    questionAmountTotal = questionList.size();
    Collections.shuffle(questionList);

    init();

    // Running the Quiz
    update_question_label();

    // Play music
    if (player == null) {
      if (this.music.equals("miimusic")) {
        player = MediaPlayer.create(this, R.raw.miimusic);
      } else if (this.music.equals("wiishopmusic")) {
        player = MediaPlayer.create(this, R.raw.wiishopmusic);
      } else {
        player = MediaPlayer.create(this, R.raw.wiishopmusic);
      }
    }
    player.start();

    createTimer(TIME_LIMIT);

    ImageView iv = findViewById(R.id.pause_screen);
    System.out.println(iv.isClickable());
    System.out.println(iv.getVisibility());
  }

  public void init() {
    Intent intent = getIntent();
    this.life = intent.getIntExtra(life_key, -1);
    this.score = intent.getIntExtra(score_key, -1);
    this.music = intent.getStringExtra("music");
  }

  /**
   * Button Handler for the 'Confirm Button'
   *
   * @param view ignore it
   */
  public void btn_confirm(View view) {
    if (!correct) {
      if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
        checkAnswer();
      } else {
        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
      }
    } else {
      update_question_label();
      createTimer(TIME_LIMIT);
    }
  }

  /** The helper function for the btn_confirm method Displaying the question and answer options */
  public void update_question_label() {
    // Setup color for answer buttons
    rb1.setTextColor(textColorDefaultRb);
    rb2.setTextColor(textColorDefaultRb);
    rb3.setTextColor(textColorDefaultRb);
    rbGroup.clearCheck(); // Exactly like what it says "clear check"

    if (questionCurrent < questionAmountTotal) {
      questionLabel = questionList.get(questionCurrent);

      textViewQuestion.setText(questionLabel.getQuestion());
      rb1.setText(questionLabel.getOption1());
      rb2.setText(questionLabel.getOption2());
      rb3.setText(questionLabel.getOption3());

      questionCurrent++;

      String life_new = "Life: " + this.life;
      String score_new = "Score: " + this.score;
      String questionNum_new = "Question: " + questionCurrent + "/" + questionAmountTotal;

      textViewLife.setText(life_new);
      textViewScore.setText(score_new);
      textViewQuestionNumber.setText(questionNum_new);

      correct = false;
      buttonConfirmNext.setText(getString(R.string.confirm));

    } else {
      finish();
    }
  }

  /** check if the chosen answer is correct */
  private void checkAnswer() {
    correct = true;
    RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
    int answerNR = rbGroup.indexOfChild(rbSelected) + 1;

    HealthOperation(answerNR != questionLabel.getAnswerNR());

    displaySolution(); // Display the solution
  }

    /** Manages the health*/
  private void HealthOperation(boolean incorrect) {
      //Check if the answer is not correct
    if (incorrect) {
      this.life--;
      String life_new = "Life: " + this.life;
      textViewLife.setText(life_new);
    } else {
      this.score++;
      String score_new = "Score: " + this.score;
      textViewScore.setText(score_new);
    }
  }

  /** Displaying solution */
  private void displaySolution() {
    rb1.setTextColor(Color.RED);
    rb2.setTextColor(Color.RED);
    rb3.setTextColor(Color.RED);

    // Mapping each options to its specific
    SparseArray<TextView> map = new SparseArray<>();
    map.append(1, rb1);
    map.append(2, rb2);
    map.append(3, rb3);

    // Check if the option is as same as the answer
    map.get(questionLabel.getAnswerNR()).setTextColor(Color.GREEN);

    if (questionCurrent < questionAmountTotal) {
      buttonConfirmNext.setText(getString(R.string.next));
      return;
    }

    buttonConfirmNext.setText(getString(R.string.finish));
  }

  //    @Override
  //    protected void aonStop() {
  //        super.onStop();
  //        stopPlayer();
  //    }

  /** Stop music helper */
  public void stopPlayer() {
    if (player != null) {
      player.release();
      player = null;
    }
  }

  /**
   * Initialize a count down timer that starts counting down with user input time
   *
   * @param max_time the time to start counting down with. Time is in milliseconds.
   */
  private void createTimer(long max_time) {
    cancelTimer();
    this.cdt =
            new CountDownTimer(max_time, 1000) {
              @Override
              public void onTick(long millisUntilFinished) {
                timeCurrent = millisUntilFinished;
                ((TextView) findViewById(R.id.text_view_countdown))
                        .setText(time_converter(timeCurrent));
              }

              @Override
              public void onFinish() {
                initialize_AlertDialog();
              }
            };
    startTimer();
  }

  /** Initialize AlertDialog that is used when the timer reached its limit */
  private void initialize_AlertDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);

    builder.setPositiveButton(
            "ok",
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                createTimer(TIME_LIMIT);
                update_question_label();
                HealthOperation(true);
              }
            });

    AlertDialog dialog = builder.create();
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
  }

    /**
     * Start the timer.
     */
    private void startTimer() {
        if (cdt != null) {
            cdt.start();
        }
    }

  /** Pause the Timer */
  private void pauseTimer() {
    cancelTimer();
  }

  /** Resume the Timer */
  private void unpauseTimer() {
    createTimer(this.timeCurrent);
  }

  /** Cancel the timer. Notice that this method resets the Timer, by setting the obj to null */
  private void cancelTimer() {
    if (cdt != null) {
      cdt.cancel();
    }
  }

  /**
   * Returns milliseconds to string with the format 00:00
   *
   * @param time milliseconds
   * @return string with the format 00:00
   */
  private String time_converter(long time) {
    long second = (time / 1000) % 60;
    long minute = (time / (1000 * 60)) % 60;

    return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
  }

    @Override
    /**
     * Pause the game
     */
  public void onPause() {
    System.out.println(100);
    pauseTimer();
    ImageView iv = findViewById(R.id.pause_screen);
    iv.setClickable(true);
    iv.setVisibility(View.VISIBLE);
    super.onPause();
    }

    @Override
    /**
     * Resume the game
     */
  public void onResume() {
    System.out.println(200);
    super.onResume();
    }

    @Override
    /**
     * Stop the game
     */
  public void onStop() {
    System.out.println(100);
    super.onStop();
    }

    /**
     * Pauses the game when exist to the main screen of the andorid
     */
    public void pause_screen(View v) {
        ImageView iv = findViewById(R.id.pause_screen);
        // If click detected then the game resume and the timer resumes.
        iv.setClickable(false);
        iv.setVisibility(View.GONE);
        unpauseTimer();
    }

  /** Restart the quiz game */
  @Override
  public void finish() {
    cancelTimer();
      stopPlayer();

      //Transfers life and score to other classes.
    Intent returnIntent = new Intent(this, HumanActivity.class);
    returnIntent.putExtra(life_key, life);
    returnIntent.putExtra(score_key, score);
    startActivityForResult(returnIntent, 1);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

      setResult(Activity.RESULT_OK, data);
        // Print life and score to the system
      System.out.println(data.getIntExtra("life", -1));
      System.out.println(data.getIntExtra("score", -1));
    }
    super.finish();
  }
}
