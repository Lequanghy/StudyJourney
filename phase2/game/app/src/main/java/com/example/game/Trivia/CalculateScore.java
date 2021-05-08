package com.example.game.Trivia;

import android.util.SparseLongArray;

class CalculateScore {

    /**
     * Calculate the score
     * @param context context
     * @param elapsedTime elapsed time
     * @param level player's current level
     */
    static void scoreCalculation(QuizActivity context, long elapsedTime, int level){
        SparseLongArray levelBonus = new SparseLongArray();
        levelBonus.put(1, 1);
        levelBonus.put(2, (long) 1.5);
        levelBonus.put(3, (long) 2);

        int score = (int) (1000 - elapsedTime / 30 * 0.5);
        score *= levelBonus.get(level);
        context.setScore(context.getScore() + score);

    }
}
