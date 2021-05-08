package com.example.game;

import android.provider.BaseColumns;

/**
 * Table that contains questions, answers and answer key
 */
public class QuizContract {

    //private QuizContract(){}

    /** Constructs a new table with question column, answers columns and answer key column*/
    public static class QuizEntry implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMNS_QUESTIONS = "questions";
        public static final String COLUMNS_OPTION1 = "option1";
        public static final String COLUMNS_OPTION2 = "option2";
        public static final String COLUMNS_OPTION3 = "option3";
        public static final String COLUMNS_ANSWERS = "answer";
    }
}
