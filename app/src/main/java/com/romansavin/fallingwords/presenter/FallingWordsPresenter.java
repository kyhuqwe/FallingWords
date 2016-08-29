package com.romansavin.fallingwords.presenter;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public interface FallingWordsPresenter {

  void loadWords();

  void startLevel();

  void finishGame();

  void chooseAnswerYes();

  void chooseAnswerNo();
}
