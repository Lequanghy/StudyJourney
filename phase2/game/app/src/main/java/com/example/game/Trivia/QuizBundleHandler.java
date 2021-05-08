package com.example.game.Trivia;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.game.Trivia.FileAsset.QuestionController;

import java.util.LinkedList;

class QuizBundleHandler {

    private QuestionOptionHandler questionOptionHandler;
    private CheckAnswerHandler checkAnswerHandler;
    private EventHandler eventHandler;

    private String answerText;
    private LinkedList<RadioButton> radioButtons;
    private RadioGroup radioGroup;

    QuizBundleHandler(LinkedList<QuestionController> objectLinkedList,
                      LinkedList<RadioButton> radioButtons,
                      RadioGroup radioGroup,
                      QuizActivity context){
        questionOptionHandler = new QuestionOptionHandler(objectLinkedList);
        checkAnswerHandler = new CheckAnswerHandler();
        eventHandler = new EventHandler(radioButtons);
        this.radioButtons = radioButtons;
        this.radioGroup = radioGroup;
    }

    /**
     * update the Question Text and the Options' Text
     * @param questionText Question's TextView
     */
    void update(TextView questionText){
        questionOptionHandler.update(questionText, radioButtons);
        answerText = questionOptionHandler.getCorrectOption();
    }

    /**
     * Check if the selected answer is right
     * @param selectedAnswer selected option/answer
     * @return return true if the selected answer is right and vice versa
     */
    boolean checkAnswer(RadioButton selectedAnswer){

        return checkAnswerHandler.checkAnswer(selectedAnswer, answerText);
    }

    /**
     * Display the solution for the current question after the user selected an answer
     */
    void displaySolution(){
        eventHandler.displaySolution(answerText);
    }

    /**
     * Reset the selected answers and formatted option text
     */
    void resetAnswer(){
        eventHandler.resetAnswer(radioGroup);
    }

    /**
     * Update level and streak
     * @param levelText level's TextView
     * @param streak player's current streak
     * @param level player's current level
     * @param wrongAnswer true if the user answered the current question wrong and vice versa
     * @return return the new level and streaks for the user
     */
    int[] updateLevel(TextView levelText, int streak, int level, boolean wrongAnswer){
        return LevelUpdateHandler.updateLevel(levelText, streak, level, wrongAnswer);
    }

    /**
     * Update the score of the game
     * @param context context
     * @param elapsedTime elapsed time
     * @param level player's current level
     */
    void updateScore(QuizActivity context, long elapsedTime, int level){
        CalculateScore.scoreCalculation(context, elapsedTime, level);
    }

    /**
     * Check if the game is done
     * @return true if there are no more questions and vice versa
     */
    boolean checkFinish(){
        return questionOptionHandler.hasNext();
    }

}
