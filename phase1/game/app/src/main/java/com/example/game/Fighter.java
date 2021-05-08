package com.example.game;

import java.util.ArrayList;

public class Fighter {

  // Fighter's health points
  private int hp = 100;
  private String name;
  private int randomMove = 0;

  private ArrayList<Move> moveList = new ArrayList<>();
  /** Couldn't moveList be a primitive array of length 4? private Move[] moveList = new Move[4]; */

  // Current defence effect the fighter has
  private int currentDefence = 0;

  /*** Constructs fighter with a name, and assigns minimums and maximums for move effect limits
   *
   * @param n: Name of the fighter eg. boss/player
   * @param smallMin: smaller effect minimum value
   * @param largeMin: larger effect minimum value
   * @param smallMax: smaller effect maximum value
   * @param largeMax: larger effect minimum value
   */
  public Fighter(String n, int smallMin, int largeMin, int smallMax, int largeMax) {
    name = n;

    Move attack1 =
        new Move("a1", largeMin, smallMax); // Low risk attack, larger min but a smaller max
    Move attack2 =
        new Move("a2", smallMin, largeMax); // High risk, but greater reward if you're lucky
    Move heal = new Move("heal", smallMin, smallMax);
    Move defend = new Move("defend", smallMin, smallMax);

    moveList.add(attack1);
    moveList.add(attack2);
    moveList.add(heal);
    moveList.add(defend);
    /**
     * moveList[0] = attack1; moveList[1] = attack2; moveList[2] = attack3; moveList[3] = attack4
     */
  }

  public Fighter(String n, int smallMin, int largeMin, int smallMax, int largeMax, int health) {
    name = n;
    // TO DO: set up moves and movelist
    Move attack1 =
        new Move("a1", largeMin, smallMax); // Low risk attack, larger min but a smaller max
    Move attack2 =
        new Move("a2", smallMin, largeMax); // High risk, but greater reward if you're lucky
    Move heal = new Move("heal", smallMin, smallMax);
    Move defend = new Move("defend", smallMin, smallMax);

    moveList.add(attack1);
    moveList.add(attack2);
    moveList.add(heal);
    moveList.add(defend);
    /**
     * moveList[0] = attack1; moveList[1] = attack2; moveList[2] = attack3; moveList[3] = attack4
     */

    hp = health;
  }

  protected int getHp() {
    return hp;
  }

  /**
   * Used when the fighter is attacked, removes the attack's effect from their hp
   *
   * @param damage: Damage dealt by the attack
   */
  private void takeDamage(int damage) {
    if (damage - currentDefence <= 0) { //if defence prevents damage
      System.out.println(name + " takes no damage, hp = " + hp);
    } else if (hp - damage + currentDefence < 0) { //if damage goes past hp minimum
      int oldHp = hp;
      hp = 0;
      System.out.println(name + " takes " + oldHp + " damage, hp = 0");
    } else { //regular case
      hp = hp - damage + currentDefence;
      if (hp < 0) hp = 0;
      System.out.println(name + " takes " + (damage - currentDefence) + " damage, hp = " + hp);
    }
  }

  /**
   * Method that uses random selection to select one of 4 moves from the fighter's move list
   *
   * @param opponent: Passes in the fighter being attacked for if an attack is selected
   */
  public void useMoveRandom(Fighter opponent) {
    randomMove = (int) (Math.random() * 4);
    // complete move
    useMoveSelection(opponent, randomMove);
  }

  /**
   * @return: Last move the boss character used, allows for the textbox to update in game
   */
  public int getRandomChoice() {
    return randomMove;
  }

  /**
   * Method returns the string of the last move used by the Fighter
   *
   * @param move: which move was used by the fighter
   */
  public String getEffectString(int move) {
    return (name + " used " + moveList.get(move).getName());
  }

  /**
   * Method receives the selection by the player/useMoveRandom and uses the appropriate move
   *
   * @param opponent: Passes in the fighter being attacked
   * @param move: Passes in the integer selected by the user
   */
  public void useMoveSelection(Fighter opponent, int move) {
    //note: defence status resets each turn

    //Calculate the amount of effect the current move will do
    int effect = moveList.get(move).use();

    //Now have the move take effect. int move - 0-1: attack. 2: Heal. 3: defence for next turn
    if (move <= 1) {
      System.out.println(
          name + " used " + moveList.get(move).getName() + " -- " + effect + " damage");
      //                                   moveList[move].getName()
      opponent.takeDamage(effect);
      currentDefence = 0;
    } else if (move == 2) {
      currentDefence = 0;
      if (hp == 100) {
        System.out.println(name + " hp already full.");
      } else if (hp + effect > 100) {
        int newEffect = 100 - hp;
        hp = 100;
        System.out.println(name + " gained " + newEffect + " hp! hp = " + hp);
      } else {
        hp = hp + effect;
        System.out.println(name + " gained " + effect + " hp! hp = " + hp);
      }
    } else {
      currentDefence = effect;
      // currentDefence = defence
      System.out.println(name + " + " + currentDefence + " defence for 1 turn");
      /**
       * The defence could stack to a certain number, otherwise it seems relatively unviable if
       * (defence < i) for some int i { defence += effect; if (defence == i) { defence = i;}
       * System.out.println(name + " + " + effect + " defence, only " + (i - defence) + " defence
       * points left!"); } else { System.out.println(name + " cannot scale defence further!"); }
       */
    }
  }
}
