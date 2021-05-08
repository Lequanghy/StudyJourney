package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.game.QuizContract.QuizEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the list of questions in SQLite.
 */
public class QuizDbHelper extends SQLiteOpenHelper {

    /**
     * Indicates the name of the database
     */
    private static final String DATABASE_NAME = "QUIZ.db";
    /** Indicates the version of the database*/
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + QuizEntry.TABLE_NAME;

    /** Constructs the database*/
    QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    /** Creates the tables for the questions, answers and the answer key*/
    public void onCreate(SQLiteDatabase db){
        System.out.println(true);
        final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
                QuizEntry.TABLE_NAME + " ( " +
                QuizEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                QuizEntry.COLUMNS_QUESTIONS + " TEXT," +
                QuizEntry.COLUMNS_OPTION1 + " TEXT," +
                QuizEntry.COLUMNS_OPTION2 + " TEXT," +
                QuizEntry.COLUMNS_OPTION3 + " TEXT," +
                QuizEntry.COLUMNS_ANSWERS + " INTEGER" +
                " ) ";
        db.execSQL(SQL_CREATE_ENTRIES);

        updateQuestionList(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Hard code questions.
     */
    private void updateQuestionList(SQLiteDatabase db) {
        Question q1 = new Question(" What is the computer language that we have learned so far in CSC207?",
                "Java", "Python", "C++", 1);
        insert(q1, db);

        Question q2 = new Question(" What is the this month?",
                "September", "October", "November", 2);
        insert(q2, db);

        Question q3 = new Question(" What is this game?",
                "Snake", "RPG", "Trivia", 3);
        insert(q3, db);

        Question q4 = new Question(" What is the year?",
                "2018", "2019", "2020", 2);
        insert(q4, db);

        Question q5 = new Question(" A is correct",
                "A", "B", "C", 1);
        insert(q5, db);

    }

    /** Inserts the question into the table*/
    private void insert(Question question, SQLiteDatabase db) {
        // Gets the data repository in write mode
        //SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(QuizContract.QuizEntry.COLUMNS_QUESTIONS, question.getQuestion());
        values.put(QuizContract.QuizEntry.COLUMNS_OPTION1, question.getOption1());
        values.put(QuizContract.QuizEntry.COLUMNS_OPTION2, question.getOption2());
        values.put(QuizContract.QuizEntry.COLUMNS_OPTION3, question.getOption3());
        values.put(QuizContract.QuizEntry.COLUMNS_ANSWERS, question.getAnswerNR());

        // Insert the new row, returning the primary key value of the new row
        db.insert(QuizEntry.TABLE_NAME, null, values);
    }

    /** Get the list of questions*/
    List<Question> getQuestionList() {
        List<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuizEntry.TABLE_NAME, null);
        // Check if there is the first item in the table
        if (c.moveToFirst()) {

            // Get the information of the question
            do {
                Question question = new Question(
                        c.getString(c.getColumnIndex(QuizEntry.COLUMNS_QUESTIONS)),
                        c.getString(c.getColumnIndex(QuizEntry.COLUMNS_OPTION1)),
                        c.getString(c.getColumnIndex(QuizEntry.COLUMNS_OPTION2)),
                        c.getString(c.getColumnIndex(QuizEntry.COLUMNS_OPTION3)),
                        c.getInt(c.getColumnIndex(QuizEntry.COLUMNS_ANSWERS)));
                // Adds the question into the list
                questionList.add(question);
            }
            // Moves to the next questions in the table.
            while (c.moveToNext());

        }
        c.close();
        return questionList;
    }
}
