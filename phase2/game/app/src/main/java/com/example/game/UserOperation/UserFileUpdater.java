package com.example.game.UserOperation;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

class UserFileUpdater {
    private final String PASSWORD = "password";
    private final String LAST_ACTIVE_SESSION = "last_active_session";
    private final String TRIVIA = "TriviaScore";
    private final String HUMAN = "HumanScore";
    private final String FIGHT = "FightScore";

    private LinkedList<UserObject> userObjects;
    private UserFile file;

    UserFileUpdater(Context context){
        file = new UserFile(context);
        try{
            userObjects = new UserParser(context).parseAll();
        } catch(ParserException ex){
            ex.printStackTrace();
        }
    }

    /**
     * update the UserObject's information to file
     * @param userObject user's UserObject
     */
    void informationUpdate(UserObject userObject){
        for (UserObject object : userObjects){
            if (object.getUsername().equals(userObject.getUsername())){
                object.setPassword(userObject.getPassword());
                object.setLastActiveSession(userObject.getLastActiveSession());
                object.setAllTriviaScore(userObject.getTriviaScores());
                object.setAllHumanScore(userObject.getHumanScores());
                object.setAllFightScore(userObject.getFightScores());
                writeToFile();
                return;
            }
        }
    }

    /**
     * Write the information to the file
     */
    private void writeToFile(){
        JSONObject result = new JSONObject();
        for (UserObject object : userObjects){
            try{
                result.put(object.getUsername(), toJSONObject(object));
            } catch (JSONException ex){
                ex.printStackTrace();
            }
        }

        file.insertAll(result);
    }

    /**
     * convert UserObject to JSONObject
     * @param userObject inputted UserObject
     * @return user information in a Json Object representation
     */
    private JSONObject toJSONObject(UserObject userObject){
        JSONObject innerObject = new JSONObject();

        try{
            innerObject.put(PASSWORD, userObject.getPassword());;
            innerObject.put(LAST_ACTIVE_SESSION, userObject.getLastActiveSession());
            innerObject.put(TRIVIA, new JSONArray(userObject.getTriviaScores()));
            innerObject.put(HUMAN, new JSONArray(userObject.getHumanScores()));
            innerObject.put(FIGHT, new JSONArray(userObject.getFightScores()));

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return innerObject;
    }
}
