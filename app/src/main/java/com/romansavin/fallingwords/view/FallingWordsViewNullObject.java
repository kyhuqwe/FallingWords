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

  @Override public void showStartButton() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showStartButton();
      }
    }
  }

  @Override public void hideStartButton() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.hideStartButton();
      }
    }
  }

  @Override public void blurGameSpace() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.blurGameSpace();
      }
    }
  }

  @Override public void showGameSpace() {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showGameSpace();
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

  @Override public void initGameLevel(final int level, @NonNull final String word, @NonNull final String translation) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.initGameLevel(level, word, translation);
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

  @Override public void showFallingCounter(final int count) {
    if (viewWeakRef != null) {
      final FallingWordsMVPView view = viewWeakRef.get();
      if (view != null) {
        view.showFallingCounter(count);
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
