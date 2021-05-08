package com.example.game.Trivia;

import android.os.CountDownTimer;

import java.util.Locale;

class Timer{
    private CountDownTimer countDownTimer;

    private long currentTimeMilli;
    private long maxTimerMilli;

    private QuizActivity context;

    Timer(long maxTimeMilli, QuizActivity context){
        this.context = context;
        this.maxTimerMilli = maxTimeMilli;
        createTimer(maxTimeMilli);
        startTimer();
    }


    /**
     * Creates a timer that decreases per second
     * @param maxTimeMilli the time it starts counting down with
     */
    private void createTimer(long maxTimeMilli){
        countDownTimer = new CountDownTimer(maxTimeMilli, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTimeMilli = millisUntilFinished;
                context.updateTimer(Timer.this.toString());
            }

            @Override
            public void onFinish() {
                context.updateTimer(null);
            }
        };
    }


    /**
     * Start the timer
     */
    private void startTimer(){
        if (countDownTimer != null){
            countDownTimer.start();
        }
    }

    /**
     * Cancel the timer
     */
    void cancelTimer(){
        if (countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    /**
     * Resume the timer
     */
    void resumeTimer(){
        createTimer(currentTimeMilli);
        startTimer();
    }

    /**
     * Reset the timer
     */
    void resetTimer(){
        cancelTimer();
        createTimer(maxTimerMilli);
        startTimer();
    }

    long elapsedTime(){
        return currentTimeMilli;
    }

    /**
     * Return the timer in a string representation
     * @return timer in String representation of 00:00
     */
    @Override
    public String toString(){
        long second = (currentTimeMilli / 1000) % 60;
        long minute = (currentTimeMilli * 60) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
    }

}
