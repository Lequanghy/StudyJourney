package com.example.game.Human;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.CountDownTimer;


import java.util.ArrayList;
import java.util.Locale;

/**
 * This game is based on the well known game Snake, but we adapted it to Human for a story
 * Game rules are in the HumanNotes.txt file
 * Code inspired by http://gamecodeschool.com/android/coding-a-snake-game-for-android/
 * Countdown timer code inspired by https://codinginflow.com/tutorials/android/countdowntimer/part-1-countdown-timer
 */


class HumanEngine extends SurfaceView implements Runnable {

    static ArrayList<Coffee> CoffeeList;
    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    // Update the game 10 times per second
    final long FPS = 10;
    final long MILLIS_PER_SECOND = 1000;
    private final long START_TIME_IN_MILLIS = 90000; // 1.5 minute
    Thread thread = null;
    // To hold a reference to the Activity
    HumanActivity context;
    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}
    // Start by heading to the right
    private Heading heading = Heading.RIGHT;
    private int screenX;
    int screenY;
    private int humanLength = 1;
    // The size in pixels of a human segment
    private int blockSize;
    private int numBlocksHigh;
    // Control pausing between updates
    private long nextFrameTime;
    private int score;
    private int health;
    // The location in the grid of all the segments
    private int[] humanX;
    private int[] humanY;
    private volatile boolean isPlaying;
    Canvas canvas;
    // Required to use canvas
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    CountDownTimer mCountDownTimer;
    boolean mTimerRunning;
    long mTimeLeftInMillis;
    String mTextViewCountDown;
    int level;
    String nickname;


    public HumanEngine(Context context, Point size) {
        super(context);

        this.context = (HumanActivity) context;
        this.context.InitializeComponents(false);
        //get score from the user's database
        score = this.context.getScore();
        //get health from the user's database
        health = this.context.getHealth();

        screenX = size.x;
        screenY = size.y;

        // Work out how many pixels each block is
        blockSize = screenX / NUM_BLOCKS_WIDE;
        numBlocksHigh = screenY / blockSize;

        // Initialize the drawing objects
        surfaceHolder = getHolder();

        paint = new Paint();

        humanX = new int[50];
        humanY = new int[50];

        //Get the nickname from the player
        nickname = this.context.nickname;

        //create a list of coffees, each coffee has a random location
        CoffeeList = new ArrayList<>(50);

        //Start the countdown timer when start the game
        createTimer(START_TIME_IN_MILLIS);

        newGame();
    }

    @Override
    public void run() {
        while (isPlaying) {
            if (updateRequired()) {
                update();
                draw();
            }
        }
    }

    public void pause() {
        isPlaying = false;
        pauseTimer();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        createTimer(mTimeLeftInMillis);
        thread = new Thread(this);
        thread.start();
    }

    /**
     * This method is used to create each new game of Human
     */
    public void newGame() {
        humanLength = 1; // Start with a single human segment
        humanX[0] = NUM_BLOCKS_WIDE / 2;
        humanY[0] = numBlocksHigh / 2;
        CoffeeList.clear(); // clear the list of coffees every time
        if (level == 1) {
            makeCoffee(15);
        } else if (level == 2) {
            makeCoffee(8);
        } else {   // if level == 3
            makeCoffee(2);
        }
        nextFrameTime = System.currentTimeMillis();
    }

    /**
     * This method helps create coffees for each new game of Human
     * @param numTimes this is the number of coffees to be created
     */
    public void makeCoffee(int numTimes) {
        int i = 1;
        while (i <= numTimes) {
            Coffee coffee1 = new Coffee(blockSize, numBlocksHigh);
            CoffeeList.add(coffee1);
            i++;
        }
    }

    /**
     * This method moves the human depending on what the user clicks
     */
    private void moveHuman() {
        // Move the body
        for (int i = humanLength; i > 0; i--) {
            // Start at the back and move it
            // to the position of the segment in front of it
            humanX[i] = humanX[i - 1];
            humanY[i] = humanY[i - 1];
        }
        // Move the head in correct direction
        switch (heading) {
            case RIGHT:
                humanX[0]++;
                break;
            case LEFT:
                humanX[0]--;
                break;
            case DOWN:
                humanY[0]++;
                break;
            case UP:
                humanY[0]--;
                break;
        }
    }

    /**
     * This method detects if a Human block hits the edge of the screen which is a death
     * @return boolean This returns whether or not the human hit the edge of the screen
     */

    private boolean detectDeath() {
        boolean dead = false;

        // Hit the edge
        if ((humanX[0] == -1) || (humanY[0] == -1) || (humanX[0] == NUM_BLOCKS_WIDE) ||
                (humanY[0] == numBlocksHigh)) {
            dead = true;
        }

        // Eats itself
        for (int i = humanLength - 1; i > 0; i--) {
            if ((i > 4) && (humanX[0] == humanX[i]) && (humanY[0] == humanY[i])) {
                dead = true;
            }
        }

        return dead;
    }

    /**
     * This method updates what is displayed and what has happened so far
     */
    public void update() {
        // Human eats coffee
        for (int i = 0; i < CoffeeList.size(); i++) {
            Coffee coffee = CoffeeList.get(i);
            if (humanX[0] == coffee.coffeeX && humanY[0] == coffee.coffeeY) {
                if (level == 1) {
                    score += 1;
                } else if (level == 2) {
                    score += 2;
                } else {  // level == 3
                    score += 3;
                }
                humanLength++;
                this.context.setScore(score, false);
                Coffee newCoffee = new Coffee(blockSize, numBlocksHigh);
                CoffeeList.set(i, newCoffee);
            }
        }

        moveHuman();

        // Hit the wall
        if (detectDeath() && level < 4) {
            // Lose one health pt whenever human hits the wall & update the health points
            health--;
            this.context.setHealth(health);
            newGame();
        }

        // Get to level 4 -> end the game
        else if (level == 4) {
            cancelTimer();
            thread.interrupt();
            context.finish();
        }
    }


    public void updateLevel() {
        level++;
        createTimer(START_TIME_IN_MILLIS);
        newGame();
    }

    /**
     * this method draws all the items displayed including the screen, timer, score, level, health
     *      instructions, human graphics and coffee graphics
     */

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Screen
            canvas.drawColor(Color.argb(255, 180, 255, 168));

            // Timer
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Time left: " + mTextViewCountDown, 25, 150, paint);

            // Score
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Score: " + score, 25, 90, paint);

            // Level
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Level: " + level, 400, 90, paint);

            // Health
            paint.setColor(Color.BLACK);
            paint.setTextSize(60);
            canvas.drawText("Health: " + health, 700, 90, paint);

            // Instructions
            paint.setColor(Color.BLACK);
            paint.setTextSize(50);
            canvas.drawText("Go " + nickname.toUpperCase() + " !!!", 25, 225, paint);

            // Human Colour
            paint.setColor(Color.argb(255, 245, 66, 99));


            // Human Creating
            for (int i = 0; i < humanLength; i++) {
                canvas.drawRect(humanX[i] * blockSize,
                        (humanY[i] * blockSize),
                        (humanX[i] * blockSize) + blockSize,
                        (humanY[i] * blockSize) + blockSize,
                        paint);
            }

            // Coffee Creating; draw each coffee from the list of coffees
            for (int i = 0; i < CoffeeList.size(); i++) {
                Coffee coffee = CoffeeList.get(i);
                paint.setColor(Color.argb(255, 144, 112, 65));
                canvas.drawRect(coffee.coffeeX * blockSize,
                        (coffee.coffeeY * blockSize),
                        (coffee.coffeeX * blockSize) + blockSize,
                        (coffee.coffeeY * blockSize) + blockSize,
                        paint);
            }

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            // Return true so that the update and draw methods executed
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch (heading) {
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch (heading) {
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }

    public void createTimer(long START_TIME_IN_MILLIS) {
        // Cancel the existing timer before create a new one
        cancelTimer();
        this.mCountDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                finishCountDown();

            }
        };

        mTimerRunning = true;

        startTimer();
    }


    // ========== TIMER CODE ========== //

    public void finishCountDown() {
        if (level < 3) {
            updateLevel();
        } else { // end the game when it gets to level 4
            thread.interrupt();
            context.finish();
        }
    }

    private void startTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.start();
        }
    }

    public void pauseTimer() {
        cancelTimer();
        mTimerRunning = false;

    }

    private void cancelTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    public void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / MILLIS_PER_SECOND) / 60;
        int seconds = (int) (mTimeLeftInMillis / MILLIS_PER_SECOND) % 60;

        mTextViewCountDown = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

    }


}

