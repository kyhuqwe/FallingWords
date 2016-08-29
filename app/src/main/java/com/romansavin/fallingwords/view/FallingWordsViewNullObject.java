package com.romansavin.fallingwords.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author Roman Savin
 * @since 28.08.2016.
 */
public class FallingWordsViewNullObject implements FallingWordsMVPView {

  @Nullable private final WeakReference<FallingWordsMVPView> viewWeakRef;

  public FallingWordsViewNullObject() {
    this.viewWeakRef = null;
  }

  public FallingWordsViewNullObject(@NonNull final FallingWordsMVPView view) {
    this.viewWeakRef = new WeakReference<>(view);
  }

  @Override public void showProgress() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showProgress();
      }
    }
  }

  @Override public void hideProgress() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.hideProgress();
      }
    }
  }

  @Override public void showLevelStartingInfo(final int level, final int duration, final int score, final int health) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showLevelStartingInfo(level, duration, score, health);
      }
    }
  }

  @Override public void hideLevelStartingInfo() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.hideLevelStartingInfo();
      }
    }
  }

  @Override public void showStartGameCounter(final int count) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showStartGameCounter(count);
      }
    }
  }

  @Override public void hideStartGameCounter() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.hideStartGameCounter();
      }
    }
  }

  @Override public void showGameBoard(
      final int level,
      final int score,
      final int health,
      @NonNull final String word,
      @NonNull final String assumedTranslation
  ) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showGameBoard(level, score, health, word, assumedTranslation);
      }
    }
  }

  @Override public void hideGameBoard() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.hideGameBoard();
      }
    }
  }

  @Override public void updateFallingWordPosition(final int dropPercentage) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.updateFallingWordPosition(dropPercentage);
      }
    }
  }

  @Override public void showFallingTimeCounter(final int count) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showFallingTimeCounter(count);
      }
    }
  }

  @Override public void showResult(final boolean isWin, @NonNull final String rightTranslation) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showResult(isWin, rightTranslation);
      }
    }
  }
}
