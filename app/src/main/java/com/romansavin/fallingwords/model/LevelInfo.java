package com.romansavin.fallingwords.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@AutoValue
public abstract class LevelInfo {

  public static LevelInfo create(
      final int level,
      final int duration,
      final int health,
      final int score,
      @NonNull final String word,
      @NonNull final String assumedTranslation,
      @NonNull final String rightTranslation
  ) {
    return new AutoValue_LevelInfo(level, duration, health, score, word, assumedTranslation, rightTranslation);
  }

  public abstract int level();

  public abstract int duration();

  public abstract int health();

  public abstract int score();

  public abstract String word();

  public abstract String assumedTranslation();

  public abstract String rightTranslation();
}
