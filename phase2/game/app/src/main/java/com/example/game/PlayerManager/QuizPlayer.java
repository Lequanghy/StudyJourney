package com.example.game.PlayerManager;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.game.R;

public class QuizPlayer {

  private MediaPlayer player;

  public QuizPlayer(Context context, String music) {
    initPlayer(context, music);
    player.setLooping(true);
  }

  /**
   * Initializes the player with a specific music file
   * @param context context
   * @param music the music key that chooses what the player should play
   */
  private void initPlayer(Context context, String music) {
    String miiMusic = "miimusic";
    String wiiShopMusic = "wiishopmusic";

    if (music.equals(miiMusic)) {
      player = MediaPlayer.create(context, R.raw.miimusic);
    } else if (music.equals(wiiShopMusic)) {
      player = MediaPlayer.create(context, R.raw.wiishopmusic);
    } else {
      player = MediaPlayer.create(context, R.raw.wiishopmusic);
    }
  }

  /**
   * Starts the player
   */
  public void startPlayer() {
    if (player != null){
        player.start();
    }
  }

  /**
   * Stops the player
   */
  public void stopPlayer() {
    player.release();
    player = null;
  }

  /**
   * Pause the player
   */
  public void pausePlayer() {
    if (player != null) {
      player.pause();
    }
  }
}
