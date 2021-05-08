package com.example.game.UserOperation;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.util.Iterator;
import java.util.LinkedList;

class UserParser {
  private final String PASSWORD = "password";
  private final String LAST_ACTIVE_SESSION = "last_active_session";
  private final String TRIVIA_SCORE = "TriviaScore";
  private final String HUMAN_SCORE = "HumanScore";
  private final String FIGHT_SCORE = "FightScore";

  private UserFile rawFile;

  UserParser(Context context) {
    rawFile = new UserFile(context);
  }

  /**
   * Parse all of the user's keys
   * @return list of user's keys
   * @throws ParserException file doesn't exist or the key is invalid
   */
  LinkedList<String> parseKeys() throws ParserException{
    if (rawFile.isEmpty())throw new ParserException("file doesn't exist or key is invalid");

    LinkedList<String> result = new LinkedList<>();

    Iterator<String> keys = rawFile.getFile().keys();

    while (keys.hasNext()){
      result.add(keys.next());
    }

    return result;
  }

  /**
   * Parse all of the user's information
   * @return list of user's information in the representation of UserObject
   * @throws ParserException file not found
   */
  LinkedList<UserObject> parseAll() throws ParserException {
    if (rawFile.isEmpty()) {
      throw new ParserException("file not found");
    }

    JSONObject userFile = rawFile.getFile();

    LinkedList<UserObject> userObjects = new LinkedList<>();

    Iterator<String> keys = userFile.keys();

    while (keys.hasNext()) {
      try {
        String key = keys.next();
        JSONObject details = userFile.getJSONObject(key);

        userObjects.add(
            new UserObject(
                key,
                parseUserDetail(PASSWORD, details),
                parseUserDetail(LAST_ACTIVE_SESSION, details),
                parseScore(details)));

      } catch (JSONException ex) {
        throw new ParserException(ex.getMessage());
      }
    }

    return userObjects;
  }

  /**
   * Parse a single user's information
   * @param key user's specific key
   * @return parsed user information in the representation of UserObject
   * @throws ParserException file doesn't exist or key is invalid
   */
  UserObject parseSingle(String key) throws ParserException {
    if (rawFile.isEmpty() || !rawFile.getFile().has(key)) {
      throw new ParserException("file doesn't exist or key is invalid");
    }

    try {
      JSONObject userFile = rawFile.getFile();

      JSONObject innerObject = userFile.getJSONObject(key);

      return new UserObject(
          key,
          parseUserDetail(PASSWORD, innerObject),
          parseUserDetail(LAST_ACTIVE_SESSION, innerObject),
          parseScore(innerObject));

    } catch (JSONException ex) {
      ex.printStackTrace();
    }
    throw new ParserException("unknown error occurred");
  }

  /**
   * Parse user's stored information
   * @param keyword the key of user's stored information
   * @param object the JSon Object that stores the user's information
   * @return the details of a specific user information
   */
  private String parseUserDetail(String keyword, JSONObject object) {
    if (object.has(keyword)) {
      try {
        return object.getString(keyword);
      } catch (JSONException ex) {
        ex.printStackTrace();
      }
    }
    return null;
  }

  /**
   * parse the scores of an user
   * @param object the Json object that stores the user's scores for each game
   * @return a ScoreObject that stores the the users's scores for each game
   */
  private ScoreObject parseScore(JSONObject object) {
    try {
      return new ScoreObject(
          jsonArrayToScoreObject(object.getJSONArray(TRIVIA_SCORE)),
          jsonArrayToScoreObject(object.getJSONArray(HUMAN_SCORE)),
          jsonArrayToScoreObject(object.getJSONArray(FIGHT_SCORE)));
    } catch (JSONException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Convert JsonArray to ScoreObject
   * @param jsonArray jsonArray that stores the record of user's scores
   * @return List of scores in a LinkedList representation
   */
  private LinkedList<Integer> jsonArrayToScoreObject(JSONArray jsonArray) {
    LinkedList<Integer> result = new LinkedList<>();

    try {
      for (int index = 0; index < jsonArray.length(); index++) {
        result.add(jsonArray.getInt(index));
      }
    } catch (JSONException ex) {
      ex.printStackTrace();
    }
    return result;
  }
}
