package com.example.game.UserOperation;

import android.content.Context;

import java.util.LinkedList;

public class UserController {

    private UserObject userObject;
    private Context context;

    UserController(Context context, String username){
        this.context = context;
        UserParser userParser = new UserParser(context);
        try{
            userObject = userParser.parseSingle(username);
        } catch (ParserException ex){
            ex.printStackTrace();
        }

    }

    /**
     * update current user's record to the file
     */
    public void updateToFile(){
        new UserFileUpdater(context).informationUpdate(userObject);
    }

    /**
     * set new password for the user
     * @param password new password
     */
    public void setPassword(String password){
        userObject.setPassword(password);
    }

    /**
     * set the last active session game mode
     * @param lastActiveSession last active game mode's name
     */
    public void setLastActiveSession(String lastActiveSession){
        userObject.setLastActiveSession(lastActiveSession);
    }

    /**
     * add new score to Trivia
     * @param score new score
     */
    public void setTriviaScore(int score){userObject.setTriviaScore(score);}

    /**
     * add new score to Human
     * @param score new score
     */
    public void setHumanScore(int score){userObject.setHumanScore(score);}

    /**
     * replace all of Trivia's existed scores
     * @param score new list of scores
     */
    public void setAllTriviaScore(LinkedList<Integer> score){userObject.setAllTriviaScore(score);}

    /**
     * replace all of Human's existed scores
     * @param score new list of scores
     */
    public void setAllHumanScore(LinkedList<Integer> score){userObject.setAllHumanScore(score);}

    /**
     * replace all of Fight's existed scores
     * @param score new list of scores
     */
    public void setAllFightScore(LinkedList<Integer> score){userObject.setAllFightScore(score);}

    /**
     * add new score to Human
     * @param score new score
     */
    public void setFightScore(int score){userObject.setFightScore(score);}

    /**
     * Get current user's username
     * @return current user's username
     */
    public String getUsername(){return userObject.getUsername();}

    /**
     * Get current user's password
     * @return current user's password
     */
    public String getPassword(){return userObject.getPassword();}

    /**
     * Get lest active session id
     * @return last active game mode's name
     */
    public String getLastActiveSession(){return userObject.getLastActiveSession();}

    /**
     * get a list of recorded Trivia scores
     * @return list of recorded Trivia scores
     */
    public LinkedList<Integer> getTriviaScores(){return userObject.getTriviaScores();}

    /**
     * get a list of recorded Human scores
     * @return list of recorded Human scores
     */
    public LinkedList<Integer> getHumanScores(){return userObject.getHumanScores();}

    /**
     * get a list of recorded Fight scores
     * @return list of recorded Fight scores
     */
    public LinkedList<Integer> getFightScores(){return userObject.getFightScores();}

}
