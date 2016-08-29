package com.romansavin.fallingwords.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.romansavin.fallingwords.model.LevelInfo;
import com.romansavin.fallingwords.model.LevelResult;
import com.romansavin.fallingwords.model.engine.GameEngine;
import com.romansavin.fallingwords.model.provider.WordsProvider;
import com.romansavin.fallingwords.view.FallingWordsMVPView;
import com.romansavin.fallingwords.view.FallingWordsViewNullObject;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;

import static rx.Observable.interval;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class FallingWordsPresenterImpl implements FallingWordsPresenter {

  private static final int START_LEVEL_SECONDS = 3;

  @NonNull private final FallingWordsMVPView view;

  @NonNull private final WordsProvider wordsProvider;

  @NonNull private final GameEngine gameEngine;

  @NonNull private final Scheduler mainScheduler;

  @NonNull private final Scheduler workerScheduler;

  @Nullable private Subscription dropPercentageSubscription;

  @Nullable private Subscription gameTimeCounterSubscription;

  public FallingWordsPresenterImpl(
      @NonNull final FallingWordsMVPView view,
      @NonNull final WordsProvider wordsProvider,
      @NonNull final GameEngine gameEngine,
      @NonNull final Scheduler mainScheduler,
      @NonNull final Scheduler workerScheduler
  ) {
    this.view = new FallingWordsViewNullObject(view);
    this.wordsProvider = wordsProvider;
    this.gameEngine = gameEngine;
    this.mainScheduler = mainScheduler;
    this.workerScheduler = workerScheduler;
  }

  @Override public void loadWords() {
    view.showProgress();

    wordsProvider.getWords()
        .subscribeOn(workerScheduler)
        .observeOn(mainScheduler)
        .doOnError(throwable -> {
          view.hideProgress();
          view.showLoadWordsError();
        })
        .doOnSuccess(words -> {
          view.hideProgress();
          final LevelInfo levelInfo = gameEngine.startNewGame(words);
          view.showLevelStartingInfo(levelInfo.level(), levelInfo.duration(), levelInfo.score(), levelInfo.health());
        })
        .subscribe();
  }

  @Override public void startLevel() {
    view.hideLevelStartingInfo();
    gameEngine.startNewLevel();
    startLevelObservable().subscribe();
  }

  @Override public void finishGame() {
    gameEngine.finishGame();
    view.finishGame();
  }

  @Override public void chooseAnswerYes() {
    final LevelResult result = gameEngine.answer(true);
    unsubscribeFromGameEngineObservables();
    handleResult(result);
  }

  @Override public void chooseAnswerNo() {
    final LevelResult result = gameEngine.answer(false);
    unsubscribeFromGameEngineObservables();
    handleResult(result);
  }

  protected Observable<Integer> startLevelObservable() {
    return interval(0, 1, TimeUnit.SECONDS)
        .map(value -> START_LEVEL_SECONDS - value.intValue())
        .take(START_LEVEL_SECONDS + 1)
        .observeOn(mainScheduler)
        .doOnNext(view::showStartGameCounter)
        .doOnCompleted(() -> {
          view.hideStartGameCounter();
          final LevelInfo levelInfo = gameEngine.getLevelInfo();
          view.showGameBoard(levelInfo.level(), levelInfo.score(), levelInfo.health(),
              levelInfo.word(), levelInfo.assumedTranslation());
          subscribeToGameEngineObservables();
        });
  }

  private void subscribeToGameEngineObservables() {
    dropPercentageSubscription = gameEngine.getDropPercentageObservable()
        .observeOn(mainScheduler)
        .doOnNext(view::updateFallingWordPosition)
        .subscribe();

    gameTimeCounterSubscription = gameEngine.getTimeCounterObservable()
        .observeOn(mainScheduler)
        .doOnNext(view::showFallingTimeCounter)
        .doOnCompleted(() -> {
          view.hideGameBoard();
          final LevelInfo levelInfo = gameEngine.getLevelInfo();
          final LevelResult levelResult = gameEngine.answer(!levelInfo.rightTranslation().equals(levelInfo.assumedTranslation()));
          handleResult(levelResult);
        })
        .subscribe();
  }

  private void unsubscribeFromGameEngineObservables() {
    if (dropPercentageSubscription != null) {
      dropPercentageSubscription.unsubscribe();
    }
    if (gameTimeCounterSubscription != null) {
      gameTimeCounterSubscription.unsubscribe();
    }
  }

  private void handleResult(@NonNull final LevelResult result) {
    view.showResult(result.isWin(), result.isGameOver(), result.score(), result.rightTranslation());

    if (!result.isGameOver()) {
      final LevelInfo levelInfo = gameEngine.getLevelInfo();
      view.showLevelStartingInfo(levelInfo.level(), levelInfo.duration(), levelInfo.score(), levelInfo.health());
      view.hideGameBoard();
    }
  }
}
