package com.example.game.Trivia;

import android.widget.RadioButton;
import android.widget.TextView;

import com.example.game.Trivia.FileAsset.QuestionController;
import com.example.game.Trivia.FileAsset.QuestionObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class QuestionOptionHandler {
private LinkedList<QuestionController> questionList;
  private QuestionController currentQuestion;

  QuestionOptionHandler(LinkedList<QuestionController> questionList) {
    this.questionList = questionList;
  }

  /**
   * Update the question's TextView and options's RadiiButtons
   * @param questionText TextView for the question
   * @param radioButtons RadioButton for the options
   */
  void update(TextView questionText, LinkedList<RadioButton> radioButtons) {
    if (!questionList.isEmpty()) {
      shuffle(questionList);
      currentQuestion = questionList.pop();
      updateQuestion(questionText);
      updateOption(radioButtons);
    }
  }

  /**
   * Helper for the update(). shuffles the questions then set a question to the TextView
   * @param questionText question's TextView
   */
  private void updateQuestion(TextView questionText) {
    shuffle(questionList);
    setQuestionText(currentQuestion.getQuestion(), questionText);
  }

  /**
   * Helper for the update(). Shuffle the options the set the options to the RadioButtons
   * @param radioButtons the options' RadioButtons
   */
  private void updateOption(LinkedList<RadioButton> radioButtons) {
    LinkedList<String> option = currentQuestion.getOption();
    int index = 0;
    shuffle(option);
    while (!option.isEmpty()) {
      setOptionText(radioButtons.get(index), option.pop());
      index += 1;
    }
  }

  /**
   * Get the correct option
   * @return Return the correct option
   */
  String getCorrectOption() {
    return currentQuestion.getCorrectOption();
  }

  /**
   * Set the option TextView
   * @param radioButton option's RadioButton
   * @param option the option Text that the RadioButton's text wants to set to
   */
  private void setOptionText(RadioButton radioButton, String option) {
    radioButton.setText(option);
  }

  /**
   * Set the question TextView
   * @param question The question text that the TextView's text wants to set to
   * @param questionText Question's TextView
   */
  private void setQuestionText(String question, TextView questionText) {
    questionText.setText(question);
  }

  /**
   * Shuffles the given list
   * @param list list of items
   */
  private void shuffle(List list) {
    Collections.shuffle(list);
  }

  /**
   * Check if the are more questions left
   * @return true if there are more questions and vice versa
   */
  boolean hasNext() {
    return questionList.size() > 0;
  }
}
