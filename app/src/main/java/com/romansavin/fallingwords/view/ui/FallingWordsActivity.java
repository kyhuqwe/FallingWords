package com.romansavin.fallingwords.view.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.romansavin.fallingwords.FallingWordsApplication;
import com.romansavin.fallingwords.R;
import com.romansavin.fallingwords.model.engine.GameEngineImpl;
import com.romansavin.fallingwords.presenter.FallingWordsPresenter;
import com.romansavin.fallingwords.presenter.FallingWordsPresenterImpl;
import com.romansavin.fallingwords.view.FallingWordsMVPView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FallingWordsActivity extends AppCompatActivity implements FallingWordsMVPView {

  @BindView(R.id.progress_bar) ProgressBar progressBar;

  @BindView(R.id.fragment_container) View fragmentContainer;

  @BindView(R.id.start_counter) TextView startCounterTextView;

  @BindView(R.id.level_info) TextView levelInfoTextView;

  @BindViews({R.id.start_level_btn, R.id.finish_game_btn}) List<Button> levelInfoButtons;

  @Nullable private FallingWordsPresenter presenter;

  @Nullable private GameBoardFragment currentGameBoardFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_falling_words);

    ButterKnife.bind(this);
    presenter = new FallingWordsPresenterImpl(
        this,
        getFallingWordsApplication().getWordsProvider(),
        new GameEngineImpl(),
        getFallingWordsApplication().getMainScheduler(),
        getFallingWordsApplication().getWorkerScheduler()
    );
    presenter.loadWords();
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    currentGameBoardFragment = null;
  }

  @OnClick(R.id.finish_game_btn) void onFinishGameClick() {
    if (presenter != null) {
      presenter.finishGame();
    }
  }

  @OnClick(R.id.start_level_btn) void onStartLevelClick() {
    if (presenter != null) {
      presenter.startLevel();
    }
  }

  @Override public void showProgress() {
    progressBar.setVisibility(VISIBLE);
  }

  @Override public void hideProgress() {
    progressBar.setVisibility(GONE);
  }

  @Override public void showLoadWordsError() {
    Toast.makeText(this, R.string.load_words_error, Toast.LENGTH_LONG).show();
  }

  @Override public void showLevelStartingInfo(final int level, final int duration, final int score, final int health) {
    levelInfoTextView.setText(getString(R.string.level_info_fmt, level, duration, score, health));
    levelInfoTextView.setVisibility(VISIBLE);
    ButterKnife.apply(levelInfoButtons, (view, value, index) -> view.setVisibility(value), VISIBLE);
  }

  @Override public void hideLevelStartingInfo() {
    levelInfoTextView.setVisibility(GONE);
    ButterKnife.apply(levelInfoButtons, (view, value, index) -> view.setVisibility(value), GONE);
  }

  @Override public void showStartGameCounter(final int count) {
    startCounterTextView.setText(String.valueOf(count));
    startCounterTextView.setVisibility(VISIBLE);
  }

  @Override public void hideStartGameCounter() {
    startCounterTextView.setVisibility(GONE);
  }

  @Override public void showGameBoard(
      final int level, final int score, final int health,
      @NonNull final String word, @NonNull final String assumedTranslation
  ) {
    if (presenter != null) {
      currentGameBoardFragment = GameBoardFragment.newInstance(level, score, health, word, assumedTranslation);
      currentGameBoardFragment.setPresenter(presenter);
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, currentGameBoardFragment).commit();

      fragmentContainer.setVisibility(VISIBLE);
    }
  }

  @Override public void hideGameBoard() {
    fragmentContainer.setVisibility(GONE);
    if (currentGameBoardFragment != null) {
      getSupportFragmentManager().beginTransaction().remove(currentGameBoardFragment).commit();
      currentGameBoardFragment = null;
    }
  }

  @Override public void updateFallingWordPosition(final int dropPercentage) {
    if (currentGameBoardFragment != null) {
      currentGameBoardFragment.updateFallingWordPosition(dropPercentage);
    }
  }

  @Override public void showFallingTimeCounter(final int count) {
    if (currentGameBoardFragment != null) {
      currentGameBoardFragment.updateTime(count);
    }
  }

  @Override public void showResult(final boolean isWin, final boolean isGameOver, final int score,
                                   @NonNull final String rightTranslation) {
    final String resultString;

    if (isWin && isGameOver) {
      resultString = getString(R.string.game_over_win_info, score);
    } else if (isWin) {
      resultString = getString(R.string.win_result_info, rightTranslation);
    } else if (isGameOver) {
      resultString = getString(R.string.game_over_lose_info, score);
    } else {
      resultString = getString(R.string.lose_result_info, rightTranslation);
    }

    final AlertDialogFragment dialogFragment = AlertDialogFragment.newInstance(resultString);
    dialogFragment.setClickListener((dialog, which) -> {
      dialog.dismiss();
      if (isGameOver && presenter != null) {
        presenter.finishGame();
      }
    });
    dialogFragment.show(getSupportFragmentManager(), AlertDialogFragment.TAG);
  }

  @Override public void finishGame() {
    finish();
  }

  @NonNull private FallingWordsApplication getFallingWordsApplication() {
    return (FallingWordsApplication) getApplication();
  }
}
