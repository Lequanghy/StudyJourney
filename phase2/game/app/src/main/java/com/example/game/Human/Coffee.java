package com.example.game.Human;

import java.util.Random;

class Coffee {
    // The size in segments of the playable area
    final int NUM_BLOCKS_WIDE = 40;
    int coffeeX;
    int coffeeY;
    // The size in pixels of a human segment
    int blockSize;
    int numBlocksHigh;

    /**
     * Constructor to create new Coffee
     */

    Coffee(int blockSize, int numBlocksHigh) {
        Random random = new Random();
        this.blockSize = blockSize;
        this.numBlocksHigh = numBlocksHigh;
        coffeeX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        coffeeY = getRandomNumberInRange(10, numBlocksHigh);
    }


    private static int getRandomNumberInRange(int min, int max) {

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
