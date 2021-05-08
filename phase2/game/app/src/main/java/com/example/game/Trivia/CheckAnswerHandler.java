package com.example.game.Trivia;

import android.widget.RadioButton;

class CheckAnswerHandler{

    /**
     * Check if the selected answer is right
     * @param selectedAnswer the selected answer
     * @param correctAnswer the correct answer
     */
    boolean checkAnswer(RadioButton selectedAnswer, String correctAnswer){
        return selectedAnswer.getText().toString().equals(correctAnswer);
    }

}
