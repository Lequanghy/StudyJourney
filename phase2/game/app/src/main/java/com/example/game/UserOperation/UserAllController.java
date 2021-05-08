package com.example.game.UserOperation;

import android.content.Context;

import java.util.LinkedList;

public class UserAllController {
    private LinkedList<UserController> userControllers;

    UserAllController(Context context){
        UserParser userParser = new UserParser(context);

        try{
            init(context, userParser.parseKeys());
        }catch (ParserException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the list of each user's unique controller
     * @param context context
     * @param usernames every users' username
     */
    private void init(Context context, LinkedList<String> usernames){
        userControllers = new LinkedList<>();

        while (!usernames.isEmpty()){
            userControllers.add(new UserController(context, usernames.pop()));
        }
    }

    /**
     * get the list of all user's unique controller
     * @return the list of all user's unique controller
     */
    public LinkedList<UserController> getUserControllers(){return userControllers;}

}
