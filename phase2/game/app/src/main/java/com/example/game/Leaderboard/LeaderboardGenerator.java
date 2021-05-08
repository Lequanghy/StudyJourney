package com.example.game.Leaderboard;

import android.content.Context;

import java.util.LinkedList;

/**
 * This class generates the leader board for the 3 different games.
 */
class LeaderboardGenerator {

    private LinkedList<LeaderboardObject> leaderboardObjects;

    /**
     * This constructor gets the list of the users with their highest score in a given game mode
     * @param context context
     * @param gameName the selected game mode's name
     */
    LeaderboardGenerator(Context context, String gameName){
        LeaderboardParser leaderboardParser = new LeaderboardParser(context);
        leaderboardObjects = leaderboardParser.parse(gameName);
    }

    /**
     * Using bubble sort to sort the users' score
     * @return returns a sorted array of LeaderboardObjects from the lowest to the highest score
     */
    private LeaderboardObject[] sorter(){
        LeaderboardObject[] arr = toArray();
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j].getHighestGameScore() > arr[j + 1].getHighestGameScore()) {
                    LeaderboardObject temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * It transforms the LinkedList of LeaderboardObjects to an array of LeaderboardObjects
     * @return returns an array of LeaderboardObjects
     */
    private LeaderboardObject[] toArray(){
        LeaderboardObject[] result = new LeaderboardObject[leaderboardObjects.size()];

        int index = 0;

        while (!leaderboardObjects.isEmpty()){
            result[index] = leaderboardObjects.pop();
            index++;
        }

        return result;
    }

    /**
     * It generates a string of the leader board score for the given game mode
     * @return returns formatted string.
     */
    String generateLeaderboard(){
        LeaderboardObject[] sorted = sorter();

        StringBuilder result = new StringBuilder();

        for (int i = sorted.length - 1; i >= 0; i--){
            String temp = "";

            if (sorted[i].getHighestGameScore() >= 0){
                temp = "Username: " + sorted[i].getUsername() + "\n" +
                        "Score: " + sorted[i].getHighestGameScore() + "\n\n";
            }
            result.append(temp);
        }

        return result.toString();
    }

}
