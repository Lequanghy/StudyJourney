package com.example.game.Trivia.FileAsset;

import java.util.LinkedList;

public class QuestionObject {

  private String question;
  private String correctOption;
  private LinkedList<String> option;

  QuestionObject(String question, String correctOption, LinkedList<String> option) {
    this.question = question;
    this.correctOption = correctOption;
    this.option = option;
  }

  /**
   * gets the question
   * @return returns the question
   */
  String getQuestion() {
    return question;
  }

  /**
   * Gets the correct option
   * @return returns the correct option
   */
  String getCorrectOption(){
      return correctOption;
  }

  /**
   * Gets the list of options for the question
   * @return returns the list of options
   */
  LinkedList<String> getOption(){
      return option;
  }
}
