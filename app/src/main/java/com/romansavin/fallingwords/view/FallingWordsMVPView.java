package com.romansavin.fallingwords.view;

import android.support.annotation.NonNull;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface FallingWordsMVPView {

  void showProgress();

  void hideProgress();

  void showLoadWordsError();

  void showLevelStartingInfo(final int level, final int duration, final int score, final int health);

  void hideLevelStartingInfo();

  void showStartGameCounter(final int count);

  void hideStartGameCounter();

  void showGameBoard(final int level, final int score, final int health,
                     @NonNull final String word, @NonNull final String assumedTranslation);

  void hideGameBoard();

  void updateFallingWordPosition(final int dropPercentage);

  void showFallingTimeCounter(final int count);

  void showResult(final boolean isWin, final boolean isGameOver, final int score,
                  @NonNull final String rightTranslation);

  void finishGame();
}
