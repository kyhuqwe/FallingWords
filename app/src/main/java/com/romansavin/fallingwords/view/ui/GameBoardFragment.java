package com.romansavin.fallingwords.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.romansavin.fallingwords.R;
import com.romansavin.fallingwords.presenter.FallingWordsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

  @BindView(R.id.level_info) TextView levelInfoTextView;

  @BindView(R.id.time_left) TextView timeLeftTextView;

  @BindView(R.id.falling_word) TextView fallingWordTextView;

  @BindView(R.id.assumed_translation) TextView assumedTranslationTextView;

  @BindView(R.id.falling_field) View fallingField;

  @Nullable private FallingWordsPresenter presenter;

  private Unbinder unbinder;

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
    final View view = inflater.inflate(R.layout.fragment_game_board, container, false);
    unbinder = ButterKnife.bind(this, view);

    final Bundle arguments = getArguments();
    levelInfoTextView.setText(getString(
        R.string.game_board_level_info_fmt,
        arguments.getInt(LEVEL_BUNDLE_KEY),
        arguments.getInt(SCORE_BUNDLE_KEY),
        arguments.getInt(HEALTH_BUNDLE_KEY)
    ));
    fallingWordTextView.setText(arguments.getString(WORD_BUNDLE_KEY));
    assumedTranslationTextView.setText(arguments.getString(ASSUMED_TRANSLATION_BUNDLE_KEY));

    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.no_btn) void onNoClick() {
    if (presenter != null) {
      presenter.chooseAnswerNo();
    }
  }

  @OnClick(R.id.yes_btn) void onYesClick() {
    if (presenter != null) {
      presenter.chooseAnswerYes();
    }
  }

  public void updateTime(final int time) {
    timeLeftTextView.setText(getString(R.string.time_left_fmt, time));
  }

  public void updateFallingWordPosition(final int dropPercentage) {
    fallingWordTextView.setTranslationY(calculateTransitionY(dropPercentage));
  }

  public void setPresenter(@NonNull final FallingWordsPresenter presenter) {
    this.presenter = presenter;
  }

  private float calculateTransitionY(final int dropPercentage) {
    if (dropPercentage == 0) {
      return 0;
    }

    final float height = fallingField.getHeight();
    return height / 100 * (float) dropPercentage;
  }
}
