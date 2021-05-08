package com.example.game;

public class Move {

    /***
     * min: Minimum amount of effect the move can have
     * max: Maximum amount of effect the move can have
     * moveName: The title of the move. Retrieved when textbox must update
     */
    protected int min;
    protected int max;
    private String moveName;

    public Move(String n, int min, int max){
        moveName = n;
        this.min = min;
        this.max = max;
    }

    /*** Returns name of the move.
     * @return: Name of the move.
     */
    public String getName(){
        return moveName;
    }

    /***
     * Calculates the amount of effect the move will have. Called each time a move is used.
     *
     * @return: Returns the amount of effect the move will have
     */
    public int use(){
        return ((int) (Math.random() * ((max - min ) + 1)) + min);
    }
}
