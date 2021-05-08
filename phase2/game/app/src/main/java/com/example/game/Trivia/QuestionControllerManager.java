package com.example.game.Trivia;

import android.content.Context;

import com.example.game.Trivia.FileAsset.QuestionAllController;
import com.example.game.Trivia.FileAsset.QuestionController;

public class QuestionControllerManager {
    public QuestionControllerManager(){}

    /**
     * Gets a specific question's controller
     * @param context context
     * @param question the question that wants to return its controller
     * @return return the given question's controller
     */
    public static QuestionController getQuestionController(Context context, String question){
        return new QuestionController(context, question);
    }

    /**
     * Returns all of the questions' unique controller
     * @param context context
     * @return return a list of all questions' controller
     */
    static QuestionAllController getQuestionAllController(Context context){
        return new QuestionAllController(context);
    }
}
