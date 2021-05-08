package com.example.game.UserOperation;

import android.content.Context;

public class UserControllerManager {

    public UserControllerManager(){}

    /**
     * Return specific user's unique controller
     * @param context context
     * @param username username
     * @return given username's user unique controller
     */
    public static UserController getUserController(Context context, String username){
        return new UserController(context, username);
    }

    /**
     * Return a list of all users' unique controller
     * @param context context
     * @return list of all users' unique controller
     */
    public static UserAllController getUserAllController(Context context){
        return new UserAllController(context);
    }

}
