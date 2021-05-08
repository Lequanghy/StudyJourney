package com.example.game.Trivia.FileAsset;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

class QuestionParser {
  private QuestionFile rawFile;

  QuestionParser(Context context) {
    rawFile = new QuestionFile(context);
  }

  /**
   * parse the keys for the QuestionFile
   * @return returns the keys of the QuestionFile
   */
  LinkedList<String> parseKeys(){
    if (rawFile.isEmpty())return null;

    LinkedList<String> result = new LinkedList<>();

    Iterator<String> keys = rawFile.getFile().keys();

    while (keys.hasNext()){
      result.add(keys.next());
    }

    return result;
  }

  /**
   * parse the entire QuestionFile
   * @return returns a list of QuestionObjects which contains each question, solution
   * and  list of options
   */
  LinkedList<QuestionObject> parseAll() {
    if (rawFile.isEmpty()) return null;

    JSONObject questionFile = rawFile.getFile();
    LinkedList<QuestionObject> questionObjects = new LinkedList<>();

    Iterator<String> keys = questionFile.keys();

    while (keys.hasNext()) {
      try {
        String key = keys.next();
        LinkedList<String> optionList = new LinkedList<>();
        String correctOption = getOption(questionFile.getJSONObject(key), optionList);

        questionObjects.add(new QuestionObject(key, correctOption, optionList));

      } catch (JSONException ex) {
        ex.printStackTrace();
      }
    }

    return questionObjects;
  }

  /**
   * Parse a single question from the QuestionFile
   * @param key the question that wants to be parsed
   * @return return the QuestionObject of the give question
   */
  QuestionObject parseSingle(String key) {
    if (rawFile.isEmpty()) return null;

    try{
      JSONObject questionFile = rawFile.getFile();
      JSONObject innerObject = questionFile.getJSONObject(key);

      LinkedList<String> optionList = new LinkedList<>();

      String correctOption = getOption(innerObject, optionList);

      return new QuestionObject(key, correctOption, optionList);

    } catch (JSONException ex){
      ex.printStackTrace();
    }
      return null;
  }

  /**
   * Helper method for the parsers to parse through the options of each question and correct option
   * @param innerObj the innerObject of the given key
   * @param optionList the empty list that's going to be populated with the options for each question
   * @return the correct option for the given question
   */
  private String getOption(JSONObject innerObj, LinkedList<String> optionList) {
    Iterator<String> keys = innerObj.keys();
    String correctOption = "";
    try {
      while (keys.hasNext()) {
        String innerKey = keys.next();
        optionList.add(innerKey);
        if (innerObj.getBoolean(innerKey)) correctOption = innerKey;
      }
    } catch (JSONException ex) {
      ex.printStackTrace();
    }

    return correctOption;
  }


}
