package com.example.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

//Code inspired by http://gamecodeschool.com/android/coding-a-snake-game-for-android/



/**
 * Whenever the player earns a point, add it to their overall score even if they die after
 * The snake game ends and moves onto the fighting game when the score reaches 7
 */


public class HumanEngine extends SurfaceView implements Runnable {

    // The size in segments of the playable area
    private final int NUM_BLOCKS_WIDE = 40;
    // Update the game 10 times per second
    private final long FPS = 10;
    // There are 1000 milliseconds in a second
    private final long MILLIS_PER_SECOND = 1000;
    private Thread thread = null;
    // To hold a reference to the Activity
    private HumanActivity context;
    // Start by heading to the right
    private Heading heading = Heading.RIGHT;
    private int screenX;
    private int screenY;
    private int humanLength;
    private int coffeeX;
    private int coffeeY;
    // The size in pixels of a human segment
    private int blockSize;
    private int numBlocksHigh;
    // Control pausing between updates
    private long nextFrameTime;
    private int score;
    // We will draw the frame much more often
    // The location in the grid of all the segments
    private int[] humanX;
    private int[] humanY;
    // Everything we need for drawing
    // Is the game currently playing?
    private volatile boolean isPlaying;
    private Canvas canvas;
    // Required to use canvas
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public HumanEngine(Context context, Point size) {
        super(context);

        this.context = (HumanActivity) context;

        score = this.context.getIntent().getIntExtra("score", -1);

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
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame() {
        // Start with a single human segment
        humanLength = 1;
        humanX[0] = NUM_BLOCKS_WIDE / 2;
        humanY[0] = numBlocksHigh / 2;
        spawnCoffee();
        // Setup nextFrameTime so an update is triggered
        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnCoffee() {
        Random random = new Random();
        coffeeX = random.nextInt(NUM_BLOCKS_WIDE - 1) + 1;
        coffeeY = random.nextInt(numBlocksHigh - 1) + 1;
    }

    private void eatCoffee() {
        humanLength++;
        spawnCoffee();
        score += 1;
    }

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

    private boolean detectDeath() {
        boolean dead = false;

        // Hit the edge
        if ((humanX[0] == -1) || (humanY[0] == -1) || (humanX[0] == NUM_BLOCKS_WIDE) ||
                (humanY[0] == numBlocksHigh)) {
            dead = true;
        }


        // Eat itself
        for (int i = humanLength - 1; i > 0; i--) {
            if ((i > 4) && (humanX[0] == humanX[i]) && (humanY[0] == humanY[i])) {
                dead = true;
            }
        }

        return dead;
    }

    public void update() {
        // Human eats coffee
        if (humanX[0] == coffeeX && humanY[0] == coffeeY) {
            eatCoffee();
        }

        moveHuman();

        if (detectDeath() && score < 8) {
            newGame();
//            healthPoint -= 1;
        }

        else if (score == 8) {
            context.finish(score);
        }
    }

    public void draw() {
        // Get a lock on the canvas
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Screen
            canvas.drawColor(Color.argb(255, 180, 255, 168));

            // Score
            paint.setColor(Color.BLACK);
            paint.setTextSize(70);
            canvas.drawText("Score: " + score, 25, 90, paint);

            // Instructions
            paint.setColor(Color.BLACK);
            paint.setTextSize(40);
            canvas.drawText("Click to change directions", 25, 145, paint);

            // Human Colour
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Human Creating
            for (int i = 0; i < humanLength; i++) {
                canvas.drawRect(humanX[i] * blockSize,
                        (humanY[i] * blockSize),
                        (humanX[i] * blockSize) + blockSize,
                        (humanY[i] * blockSize) + blockSize,
                        paint);
            }

            // Coffee Colour
            paint.setColor(Color.argb(255, 144, 112, 65));


            // Coffee Creating
            canvas.drawRect(coffeeX * blockSize,
                    (coffeeY * blockSize),
                    (coffeeX * blockSize) + blockSize,
                    (coffeeY * blockSize) + blockSize,
                    paint);

            // Unlock the canvas and reveal the graphics for this frame
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public boolean updateRequired() {
        if (nextFrameTime <= System.currentTimeMillis()) {
            // Tenth of a second has passed
            // Setup when the next update will be triggered
            nextFrameTime = System.currentTimeMillis() + MILLIS_PER_SECOND / FPS;
            // Return true so that the update and draw
            // functions are executed
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

    // For tracking movement Heading
    public enum Heading {UP, RIGHT, DOWN, LEFT}
}

