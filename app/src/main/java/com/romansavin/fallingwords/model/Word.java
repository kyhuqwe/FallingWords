package com.romansavin.fallingwords.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@AutoValue
public abstract class Word {

  public static Word create(@NonNull final String word, @NonNull final String translation) {
    return new AutoValue_Word(word, translation);
  }

  public static TypeAdapter<Word> typeAdapter(final Gson gson) {
    return new AutoValue_Word.GsonTypeAdapter(gson);
  }

  @SerializedName("text_eng") public abstract String word();

  @SerializedName("text_spa") public abstract String translation();
}
