package com.romansavin.fallingwords.model;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@GsonTypeAdapterFactory
public abstract class WordsTypeAdapterFactory implements TypeAdapterFactory {

  public static TypeAdapterFactory create() {
    return new AutoValueGson_WordsTypeAdapterFactory();
  }
}
