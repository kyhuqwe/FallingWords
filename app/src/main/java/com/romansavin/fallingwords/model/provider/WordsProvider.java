package com.romansavin.fallingwords.model.provider;

import android.support.annotation.NonNull;

import com.romansavin.fallingwords.model.Word;

import java.util.List;

import rx.Single;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface WordsProvider {

  @NonNull Single<List<Word>> getWords();
}
