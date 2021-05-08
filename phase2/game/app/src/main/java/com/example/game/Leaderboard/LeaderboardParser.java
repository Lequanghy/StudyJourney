package com.example.game.Leaderboard;

import android.content.Context;

import com.example.game.UserOperation.UserAllController;
import com.example.game.UserOperation.UserController;
import com.example.game.UserOperation.UserControllerManager;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This class parses the each users' score
 */
class LeaderboardParser {
    private UserAllController userAllController;

    LeaderboardParser(Context context){
        userAllController = UserControllerManager.getUserAllController(context);
    }

    /**
     * Parses the selected game mode's score
     * @param gameName which game mode's score the parser should parse
     * @return return list of LeaderboardObject that contains the score and username for the selected game mode
     */
    LinkedList<LeaderboardObject> parse(String gameName){
        LinkedList<LeaderboardObject> result = new LinkedList<>();

        LinkedList<UserController> userControllers = userAllController.getUserControllers();

        while (!userControllers.isEmpty()){
            UserController uController = userControllers.pop();

            if (gameName.equals("Trivia")){
                result.add(new LeaderboardObject(
                        uController.getUsername(),
                        parseHighestScore(uController.getTriviaScores())
                ));
            }
            else if (gameName.equals("Human")){
                result.add(new LeaderboardObject(
                        uController.getUsername(),
                        parseHighestScore(uController.getHumanScores())
                ));
            } else if (gameName.equals("Fight")){
                result.add(new LeaderboardObject(
                        uController.getUsername(),
                        parseHighestScore(uController.getFightScores())
                ));
            }

        }

        return result;
    }

    /**
     * Helper method for the parse(), which returns the highest game score for the user
     * @param list the list of the scores of a user for a given game
     * @return return the highest score in the the given list
     */
    private int parseHighestScore(LinkedList<Integer> list){
        if (list.isEmpty()){
            return 0;
        }

        Collections.sort(list);
        return list.getLast();
    }
}
