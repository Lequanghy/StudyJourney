package com.example.game.Application;

import android.app.Application;

import com.example.game.UserOperation.UserController;

public class GameApplication extends Application {

    private UserController userController;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    /**
     * Sets the last active game mode's session
     * @param lastActiveSession the game mode's name
     */
    public void setLastActiveSession(String lastActiveSession){
        userController.setLastActiveSession(lastActiveSession);
    }

    /**
     * Sets the score for a given game mode
     * @param gameName game mode's name
     * @param score the score of the game mode
     */

    public void setScore(String gameName, int score){
        if (score < 0)return;

        if (gameName.equals("Trivia")){
            userController.setTriviaScore(score);
        }else if (gameName.equals("Human")){
            userController.setHumanScore(score);
        }else if (gameName.equals("Fight")){
            userController.setFightScore(score);
        }
    }

    /**
     * sets the user controller
     * @param userController user controller
     */
    public void setUserController(UserController userController){
        this.userController = userController;
    }

    /**
     * Gets the last active session game mode
     * @return the last active game mode's name
     */
    public String getLastActiveSession(){return userController.getLastActiveSession();}

    /**
     * Gets the username for the current user
     * @return the user's username
     */
    public String getUseranme(){return userController.getUsername();}

    /**
     * Update file
     */
    public void updateFile(){
        userController.updateToFile();
    }

    /**
     * Check if the user controller is empty
     * @return true if the user controller exists and vice versa
     */
    public boolean isEmpty(){return userController == null;}
}
