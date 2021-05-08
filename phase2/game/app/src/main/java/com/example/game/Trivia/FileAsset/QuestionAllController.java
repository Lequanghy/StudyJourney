package com.example.game.Trivia.FileAsset;

import android.content.Context;

import java.util.LinkedList;

public class QuestionAllController {
  private LinkedList<QuestionController> questionControllers;

  public QuestionAllController(Context context) {
    QuestionParser questionParser = new QuestionParser(context);

    init(context, questionParser.parseKeys());
  }

    /**
     * Retrieves the list of the controllers for each question
     * @param context context
     * @param questions List of questions for the Trivia
     */
  private void init(Context context, LinkedList<String> questions){
      questionControllers = new LinkedList<>();

      while (!questions.isEmpty()){
          questionControllers.add(new QuestionController(context, questions.pop()));
      }
  }

    /**
     * Return the list of every questions' controller
     * @return list of every questions' controller
     */
  public LinkedList<QuestionController> getQuestionControllers(){return questionControllers;}
}
