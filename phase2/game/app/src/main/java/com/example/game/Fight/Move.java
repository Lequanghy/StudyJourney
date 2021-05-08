package com.example.game.Fight;

public class Move {

    /***
     * min: Minimum amount of effect the move can have
     * max: Maximum amount of effect the move can have
     * moveName: The title of the move. Retrieved when text box must update
     */
    private int min;
    private int max;
    private String moveName;

    /**
     * @param n: Name of the move
     * @param min: Minimum amount of effect the move can have
     * @param max: Maximum amount of effect the move can have
     */
    public Move(String n, int min, int max){
        moveName = n;
        this.min = min;
        this.max = max;
    }

    /*** Returns name of the move.
     * @return moveName: Name of the move.
     */
    public String getName(){
        return moveName;
    }

    /***
     * Calculates the amount of effect the move will have. Called each time a move is used.
     *
     * @return int: Returns the amount of effect the move will have
     */
    int use(){
        return ((int) (Math.random() * ((max - min ) + 1)) + min);
    }
}
