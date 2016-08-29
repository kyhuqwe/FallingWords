package com.romansavin.fallingwords.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romansavin.fallingwords.R;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class GameBoardFragment extends Fragment {

  private static final String LEVEL_BUNDLE_KEY = "LEVEL_BUNDLE_KEY";
  private static final String SCORE_BUNDLE_KEY = "SCORE_BUNDLE_KEY";
  private static final String HEALTH_BUNDLE_KEY = "HEALTH_BUNDLE_KEY";
  private static final String WORD_BUNDLE_KEY = "WORD_BUNDLE_KEY";
  private static final String ASSUMED_TRANSLATION_BUNDLE_KEY = "ASSUMED_TRANSLATION_BUNDLE_KEY";

  public static GameBoardFragment newInstance(
      final int level,
      final int score,
      final int health,
      @NonNull final String word,
      @NonNull final String assumedTranslation
  ) {
    final GameBoardFragment fragment = new GameBoardFragment();
    final Bundle arguments = new Bundle();
    arguments.putInt(LEVEL_BUNDLE_KEY, level);
    arguments.putInt(SCORE_BUNDLE_KEY, score);
    arguments.putInt(HEALTH_BUNDLE_KEY, health);
    arguments.putString(WORD_BUNDLE_KEY, word);
    arguments.putString(ASSUMED_TRANSLATION_BUNDLE_KEY, assumedTranslation);

    fragment.setArguments(arguments);
    return fragment;
  }

  @Nullable @Override
  public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                           @Nullable final Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_game_board, container, false);
  }

  public void updateTime(final int time) {

  }

  public void updateFallingWordPosition(final int dropPercentage) {

  }
}
