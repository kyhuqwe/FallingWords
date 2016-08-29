package com.romansavin.fallingwords.model;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface GameEngine {

  void startNewGame(@NonNull final List<Word> words) throws IllegalArgumentException;

  void finishGame();

  void startNewLevel();

  LevelResult answer(final boolean isYesAnswer);

  Observable<Integer> getDropPercentageObservable();

  Observable<Integer> getTimeCounterObservable();

  LevelInfo getLevelInfo();
}
