package com.romansavin.fallingwords.model.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.romansavin.fallingwords.model.Word;
import com.romansavin.fallingwords.model.WordsTypeAdapterFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import rx.Single;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class WordsProviderAssetsImpl implements WordsProvider {

  private final Context appContext;
  private final Gson gson;

  public WordsProviderAssetsImpl(@NonNull final Context appContext) {
    this.appContext = appContext;
    this.gson = new GsonBuilder()
        .registerTypeAdapterFactory(WordsTypeAdapterFactory.create())
        .create();
  }

  @NonNull @Override public Single<List<Word>> getWords() {
    return Single.create(singleSubscriber -> {
      try {
        final Reader reader = new InputStreamReader(appContext.getAssets().open("words.json"), "UTF-8");
        singleSubscriber.onSuccess(Arrays.asList(gson.fromJson(reader, Word[].class)));
      } catch (IOException e) {
        singleSubscriber.onError(e);
      }
    });
  }
}
