package com.romansavin.fallingwords.model;

import java.util.List;

import rx.Single;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface WordsProvider {

  Single<List<Word>> getWords();
}
