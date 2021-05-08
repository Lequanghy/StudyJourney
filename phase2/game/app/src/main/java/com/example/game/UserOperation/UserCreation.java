package com.example.game.UserOperation;

import android.content.Context;

import com.example.game.FileOperation.FileSkeleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCreation {

    /**
     * Creates new user
     * @param context context
     * @param username inputted username
     * @param password inputted password
     * @throws Exception username is taken
     */
    public static void InitializeNewUser(Context context, String username, String password) throws Exception{
        if (username.trim().isEmpty() || password.trim().isEmpty()){
            throw new Exception("USERNAME OR PASSWORD IS EMPTY!!!");
        }

        FileSkeleton fs = new FileSkeleton(context, "USER");

        String[] keys = {"TriviaScore", "HumanScore", "FightScore"};

        JSONObject Details = new JSONObject();

        try{
            Details.put("password", password.trim());
            Details.put("last_active_session", JSONObject.NULL);
            for (String k : keys) {
                Details.put(k, new JSONArray());
            }
            fs.insert(username.trim(), Details);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


}
