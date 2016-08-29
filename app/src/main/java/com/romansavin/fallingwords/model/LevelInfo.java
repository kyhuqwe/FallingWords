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
      @NonNull final String word,
      @NonNull final String assumedTranslation
  ) {
    return new AutoValue_LevelInfo(level, duration, health, word, assumedTranslation);
  }

  public abstract int level();

  public abstract int duration();

  public abstract int health();

  public abstract String word();

  public abstract String assumedTranslation();
}