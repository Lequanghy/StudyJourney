package com.example.game.Leaderboard;

import android.content.Context;
import android.os.UserManager;

import com.example.game.UserOperation.UserAllController;
import com.example.game.UserOperation.UserControllerManager;

/**
 * Stores the username and their highest selected game mode score
 */
class LeaderboardObject {
    private String username;

    private int highestGameScore;

    /**
     * Initializes the object
     * @param username username of the player
     * @param highestGameScore player's highest game score in a selected game mode
     */
    LeaderboardObject(String username, int highestGameScore){
        this.username = username;
        this.highestGameScore = highestGameScore;

    }

    /**
     * Returns the username
     * @return return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the highest game score for a selected game mode
     * @return return highest games score
     */
    int getHighestGameScore(){
        return highestGameScore;
    }
}
