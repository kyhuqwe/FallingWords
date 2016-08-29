package com.romansavin.fallingwords.view;

import android.support.annotation.NonNull;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface FallingWordsMVPView {

  void showStartButton();

  void hideStartButton();

  void blurGameSpace();

  void showGameSpace();

  void showStartGameCounter(final int count);

  void hideStartGameCounter();

  void initGameLevel(final int level, @NonNull final String word, @NonNull final String translation);

  void updateFallingWordPosition(final int dropPercentage);

  void showFallingCounter(final int count);

  void showResult(final boolean isWin, @NonNull final String rightTranslation);
}
