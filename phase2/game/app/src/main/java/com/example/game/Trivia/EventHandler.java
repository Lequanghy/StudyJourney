package com.example.game.Trivia;

import android.graphics.Color;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.LinkedList;

class EventHandler {
  private LinkedList<RadioButton> radioButtons;

  EventHandler(LinkedList<RadioButton> radioButtons){
    this.radioButtons = radioButtons;
  }

    /**
     * Displays the solution
     * @param answer the correct answer of the current question
     */
  void displaySolution(String answer){
      setAnswerColor(Color.RED);

      for (RadioButton item : radioButtons) {
          if (item.getText().toString().equals(answer)){
            item.setTextColor(Color.GREEN);
            return;
          }
      }
  }

    /**
     * Resets the selected options and formatted options to its original form
     * @param radioGroup the radioGroup that holds the options
     */
  void resetAnswer(RadioGroup radioGroup) {
    setAnswerColor(Color.BLACK);
    radioGroup.clearCheck();
  }

    /**
     * Sets every RadioButton color to the give color
     * @param color Color
     */
  private void setAnswerColor(int color) {
    for (RadioButton radioButton : radioButtons) {
      radioButton.setTextColor(color);
    }
  }


}
