package com.example.game.Fight;

import java.util.ArrayList;

public class Fighter {

  // Fighter's health points
  private int hp = 100;
  private int maxHp = 100; //maximum amount of health the fighter can have
  private String name;
  private int randomMove = 0;

  private ArrayList<Move> moveList = new ArrayList<>();

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
  }

    /*** Constructs fighter with a name and custom health value, and assigns minimums and maximums for move effect limits
     *
     * @param n: Name of the fighter eg. boss/player
     * @param smallMin: smaller effect minimum value
     * @param largeMin: larger effect minimum value
     * @param smallMax: smaller effect maximum value
     * @param largeMax: larger effect minimum value
     * @param health: The custom starting value for the fighter's health
     */
  public Fighter(String n, int smallMin, int largeMin, int smallMax, int largeMax, int health) {
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

    hp = health;
  }

  //Used by the game to access the fighter's HP & HP limit
  int getHp() {
    return hp;
  }
  int getMaxHp() {return maxHp;}

  //Allow current health points and maximum amount of health to be changed
  void setHp(int h) {hp = h;}
  void setMaxHp(int h) {maxHp = h;}

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
  void useMoveRandom(Fighter opponent) {
    randomMove = (int) (Math.random() * 4);
    // complete move
    useMoveSelection(opponent, randomMove);
  }

  /**
   * Returns last move the boss character used, allows for the textbox to update in game
   */
  int getRandomChoice() {
    return randomMove;
  }

  /**
   * Method returns the string of the last move used by the Fighter
   *
   * @param move: which move was used by the fighter
   */
  String getEffectString(int move) {
    return (name + " used " + moveList.get(move).getName());
  }

  /**
   * Method receives the selection by the player/useMoveRandom and uses the appropriate move
   *
   * @param opponent: Passes in the fighter being attacked
   * @param move: Passes in the integer selected by the user
   */
  void useMoveSelection(Fighter opponent, int move) {
    //note: defence status resets each turn

    //Calculate the amount of effect the current move will do
    int effect = moveList.get(move).use();

    //Now have the move take effect. int move - 0-1: attack. 2: Heal. 3: defence for next turn
    if (move <= 1) {
      System.out.println(
          name + " used " + moveList.get(move).getName() + " -- " + effect + " damage");
      opponent.takeDamage(effect);
      currentDefence = 0;
    } else if (move == 2) {
      currentDefence = 0;
      if (hp == maxHp) {
        System.out.println(name + " hp already full.");
      } else if (hp + effect > 100) {
        int newEffect = maxHp - hp;
        hp = maxHp;
        System.out.println(name + " gained " + newEffect + " hp! hp = " + hp);
      } else {
        hp = hp + effect;
        System.out.println(name + " gained " + effect + " hp! hp = " + hp);
      }
    } else {
      currentDefence = effect;
      System.out.println(name + " + " + currentDefence + " defence for 1 turn");
    }
  }
}
