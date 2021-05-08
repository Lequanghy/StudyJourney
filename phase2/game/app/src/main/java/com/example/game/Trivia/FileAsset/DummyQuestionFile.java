package com.example.game.Trivia.FileAsset;

import org.json.JSONException;
import org.json.JSONObject;

abstract class DummyQuestionFile {

    /**
     * Generates the questions for the Trivia Game
     * @return returns a JsonObject that contains all of the questions, answers and options
     */
    static JSONObject GenerateDefaultQuestion(){
        JSONObject Question = new JSONObject();
        try{
            Question.put(
                    "What is the computer language that we are learning in CSC 207?", new JSONObject()
                            .put("Java", true)
                            .put("Python", false)
                            .put("C++", false)
            )
            .put(
                    "What is this game?", new JSONObject()
                            .put("Snake", false)
                            .put("RPG", false)
                            .put("Trivia", true)
            )
            .put(
                    "What is the current year?", new JSONObject()
                            .put("2018", false)
                            .put("2019", true)
                            .put("2020", false)
            )
            .put(
                    "A is correct", new JSONObject()
                            .put("A", true)
                            .put("B", false)
                            .put("C", false)
            )
            .put(
                    "How many campuses does UofT have?", new JSONObject()
                            .put("1", false)
                            .put("2", false)
                            .put("3", true)
            ).put(
                    "What is another word for lexicon?", new JSONObject()
                            .put("Lexus", false)
                            .put("Dictionary", true)
                            .put("Bitcoin", false)
            ).put(
                    "What is the name of the seventh planet from the sun?", new JSONObject()
                            .put("Earth", false)
                            .put("Neptune", false)
                            .put("Uranus", true)
            ).put(
                    "What is the biggest lake in Canada?", new JSONObject()
                            .put("Lake Superior", true)
                            .put("Lake Ontario", false)
                            .put("Lake Winnipeg", false)
            ).put(
                    "What is the longest river in the world?", new JSONObject()
                            .put("Amazon", false)
                            .put("Nile", true)
                            .put("Yellow River", false)
            ).put(
                    "What is the biggest island in the world?", new JSONObject()
                            .put("Iceland", false)
                            .put("Greenland", true)
                            .put("Japan", false)
            ).put(
                    "Name of the three primary colors?", new JSONObject()
                            .put("Red, yellow and blue", true)
                            .put("Black, white and grey", false)
                            .put("Purple, pink and red", false)
            ).put(
                    "Which chess piece can only move diagonally?", new JSONObject()
                            .put("Rook", false)
                            .put("Bishop", true)
                            .put("Pawn", false)
            ).put(
                    "What is the meaning of the word “Noel” in Latin?", new JSONObject()
                            .put("Christmas", false)
                            .put("Death", false)
                            .put("Birth", true)
            ).put(
                    "In what country did the custom of putting up a Christmas tree originate", new JSONObject()
                            .put("England", false)
                            .put("Canada", false)
                            .put("Germany", true)
            );
        } catch (JSONException ex){
            ex.printStackTrace();
        }
        return Question;
    }

}
