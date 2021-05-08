package com.example.game.Trivia.FileAsset;

import android.content.Context;

import java.util.LinkedList;

public class QuestionController {

    private QuestionObject questionObject;

    public QuestionController(Context context, String key){
        QuestionParser questionParser = new QuestionParser(context);

        questionObject = questionParser.parseSingle(key);
    }

    /**
     * Get the question from the QuestionObject
     * @return return the question
     */
    public String getQuestion(){
        return questionObject.getQuestion();
    }

    /**
     * Get the correct answer from the QuestionObject
     * @return return the correct answer
     */
    public String getCorrectOption(){
        return questionObject.getCorrectOption();
    }

    /**
     * Gets the list of options from the QuestionObject
     * @return returns the list of options
     */
    public LinkedList<String> getOption(){
        return questionObject.getOption();
    }

}
