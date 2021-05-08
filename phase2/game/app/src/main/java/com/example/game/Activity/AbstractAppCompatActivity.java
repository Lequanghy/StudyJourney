package com.example.game.Activity;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.Application.GameApplication;
import com.example.game.R;
import com.example.game.UserOperation.UserController;

public abstract class AbstractAppCompatActivity extends AppCompatActivity {

  private GameApplication app;
  private TextView Score;

  private int score = 0;
  private int health = 100;

  /**
   * Initialize the instances
   * @param initData check if the Activity class wants to initialize the textView with the similar id
   */
  public void InitializeComponents(boolean initData) {
    app = (GameApplication) getApplication();
    Score = findViewById(R.id.ScoreText);

    if (initData)InitializeData();

  }

  /**
   * Initializes the textView's text, where the textView's id is similar in different Activity class
   */
  private void InitializeData(){
    updateScore();
  }

  /**
   * Set the new score
   * @param score the new score that the instance should refer to instead.
   */
  public void setScore(int score) {
    setScore(score, true);
  }

  /**
   * Set the new score with the option if they want to update the score's textView as well
   * @param score the new score that the instance should refer to instead.
   * @param textUpdate the option if they want to update the textView as well.
   */
  public void setScore(int score, boolean textUpdate){
    this.score = score;
    if(textUpdate){
      updateScore();
    }
  }

  /**
   * Sets the new health
   * @param health he new health that the instance should refer to instead.
   */
  public void setHealth(int health){
    this.health = health;
  }

  /**
   * Gets the score
   * @return return the score
   */
  public int getScore(){return score;}

  /**
   * Gets the health
   * @return return the health
   */
  public int getHealth(){return health;}

  /**
   * Update the score's textView
   */
  private void updateScore(){
    String newScore = "Score: " + score;
    Score.setText(newScore);
  }

  /**
   * save's the current game's stats
   */
  public void save(String gameName){
    app.setScore(gameName, score);
    app.updateFile();
  }

  /**
   * set the current game mode name
   * @param session game mode name
   */
  public void setLastActiveSession(String session){
    app.setLastActiveSession(session);
  }

  /**
   * Sets the user controller that contains the user operations
   * @param userController the user controller
   */
  public void setUserController(UserController userController){
    app.setUserController(userController);
  }

  /**
   * Gets the last active game mode's name
   * @return the game mode's name
   */
  public String getLastActiveSession(){return app.getLastActiveSession();}

  /**
   * Checks if there is a user controller
   * @return true if there is a user controller and vice versa
   */
  public boolean getUserControllerIsEmpty(){return app.isEmpty();}

  /**
   * Gets the current user's username
   * @return user's username
   */
  public String getUsername(){return app.getUseranme();}

}
