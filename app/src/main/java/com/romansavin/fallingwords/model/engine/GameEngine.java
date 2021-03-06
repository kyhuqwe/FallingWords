package com.romansavin.fallingwords.model.engine;

import android.support.annotation.NonNull;

import com.romansavin.fallingwords.model.LevelInfo;
import com.romansavin.fallingwords.model.LevelResult;
import com.romansavin.fallingwords.model.Word;

import java.util.List;

import rx.Observable;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface GameEngine {

  @NonNull LevelInfo startNewGame(@NonNull final List<Word> words) throws IllegalArgumentException;

  void finishGame();

  void startNewLevel();

  @NonNull LevelResult answer(final boolean isYesAnswer);

  @NonNull Observable<Integer> getDropPercentageObservable();

  @NonNull Observable<Integer> getTimeCounterObservable();

  @NonNull LevelInfo getLevelInfo();
}
