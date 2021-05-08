package com.example.game.Trivia;

import android.widget.TextView;

class LevelUpdateHandler {

    /**
     * Updates the streak and the level for the Trivia game
     * @param levelText the TextView that shows the level
     * @param streak the current user's streak of answering the right answers
     * @param level the current user's level in the game
     * @param wrongAnswer true if the user answered the current question wrong and vice versa
     * @return return the new level and streaks for the user
     */
    static int[] updateLevel(TextView levelText, int streak, int level, boolean wrongAnswer) {
        String newLevelText = "Level: ";

        if(!wrongAnswer){
            newLevelText += "1";
            levelText.setText(newLevelText);
            return new int[]{1, 0};
        }

        if (streak + 1 == level && level < 3){
            newLevelText += (level + 1);
            levelText.setText(newLevelText);
            return new int[]{level + 1, 0};
        }

        return new int[]{level, streak + 1};
    }
}
