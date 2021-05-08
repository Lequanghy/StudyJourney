package com.example.game.UserOperation;

import java.util.LinkedList;

class UserObject {
    private String username;
    private String password;
    private String lastActiveSession;

    private ScoreObject scoreObject;

    UserObject(String username, String password, String lastActiveSession, ScoreObject scoreObject){

        this.username = username;
        this.password = password;
        this.lastActiveSession = lastActiveSession;
        this.scoreObject = scoreObject;
    }

    /**
     * set new password for the user
     * @param password new password
     */
    void setPassword(String password){
        this.password = password;
    }

    /**
     * set the last active session game mode
     * @param lastActiveSession last active game mode's name
     */
    void setLastActiveSession(String lastActiveSession){
        this.lastActiveSession = lastActiveSession;
    }

    /**
     * add new score to Trivia
     * @param score new score
     */
    void setTriviaScore(int score){
        scoreObject.setTriviaScore(score);
    }

    /**
     * add new score to Human
     * @param score new score
     */
    void setHumanScore(int score){
        scoreObject.setHumanScore(score);
    }

    /**
     * add new score to Human
     * @param score new score
     */
    void setFightScore(int score){
        scoreObject.setFightScore(score);
    }

    /**
     * replace all of Trivia's existed scores
     * @param score new list of scores
     */
    void setAllTriviaScore(LinkedList<Integer> score){scoreObject.setAllTriviaScore(score);}

    /**
     * replace all of Human's existed scores
     * @param score new list of scores
     */
    void setAllHumanScore(LinkedList<Integer> score){scoreObject.setAllHumanScore(score);}

    /**
     * replace all of Fight's existed scores
     * @param score new list of scores
     */
    void setAllFightScore(LinkedList<Integer> score){scoreObject.setAllFightScore(score);}

    /**
     * Get current user's username
     * @return current user's username
     */
    String getUsername(){return username;}

    /**
     * Get current user's password
     * @return current user's password
     */
    String getPassword(){return password;}

    /**
     * Get lest active session id
     * @return last active game mode's name
     */
    String getLastActiveSession(){return lastActiveSession;}

    /**
     * get a list of recorded Trivia scores
     * @return list of recorded Trivia scores
     */
    LinkedList<Integer> getTriviaScores(){return scoreObject.getTriviaScores();}

    /**
     * get a list of recorded Human scores
     * @return list of recorded Human scores
     */
    LinkedList<Integer> getHumanScores(){return scoreObject.getHumanScores();}

    /**
     * get a list of recorded Fight scores
     * @return list of recorded Fight scores
     */
    LinkedList<Integer> getFightScores(){return scoreObject.getFightScores();}
}
