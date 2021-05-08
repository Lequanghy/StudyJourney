package com.example.game.UserOperation;

import java.util.LinkedList;

class ScoreObject {
    private LinkedList<Integer> triviaScores;
    private LinkedList<Integer> humanScores;
    private LinkedList<Integer> fightScores;

    ScoreObject(LinkedList<Integer> triviaScores, LinkedList<Integer> humanScores, LinkedList<Integer> fightScores){
        this.triviaScores = triviaScores;
        this.humanScores = humanScores;
        this.fightScores = fightScores;
    }

    /**
     * add new score to the Trivia Game
     * @param score score of the game
     */
    void setTriviaScore(int score){
        triviaScores.add(score);
    }

    /**
     * add new score to the Human Game
     * @param score score of the game
     */
    void setHumanScore(int score){
        humanScores.add(score);
    }

    /**
     * add new score to the Fight Game
     * @param score score of the game
     */
    void setFightScore(int score){
        fightScores.add(score);
    }

    /**
     * Replace all of Trivia's score
     * @param score new list of Trivia's score
     */
    void setAllTriviaScore(LinkedList<Integer> score){triviaScores = score;}

    /**
     * Replace all of Human's score
     * @param score new list of Human's score
     */
    void setAllHumanScore(LinkedList<Integer> score){humanScores = score;}

    /**
     * Replace all of Fight's score
     * @param score new list of Fight's score
     */
    void setAllFightScore(LinkedList<Integer> score){fightScores = score;}

    /**
     * get the list of existed records of scores of the Trivia game
     * @return list of scored scored in the Trivia game
     */
    LinkedList<Integer> getTriviaScores(){return triviaScores;}

    /**
     * get the list of existed records of scores of the Human game
     * @return list of scored scored in the Human game
     */
    LinkedList<Integer> getHumanScores(){return humanScores;}

    /**
     * get the list of existed records of scores of the Fight game
     * @return list of scored scored in the Fight game
     */
    LinkedList<Integer> getFightScores(){return fightScores;}
}
