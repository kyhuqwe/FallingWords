package com.romansavin.fallingwords.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@AutoValue
public abstract class Word {

  public static Word create(@NonNull final String word, @NonNull final String translation) {
    return new AutoValue_Word(word, translation);
  }

  public abstract String word();

  public abstract String translation();
}
