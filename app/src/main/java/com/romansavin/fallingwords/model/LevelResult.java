package com.romansavin.fallingwords.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@AutoValue
public abstract class LevelResult {

  public static LevelResult create(final boolean isWin, final int score, @NonNull final String rightTranslation) {
    return new AutoValue_LevelResult(isWin, score, rightTranslation);
  }

  public abstract boolean isWin();

  public abstract int score();

  public abstract String rightTranslation();
}
