package com.romansavin.fallingwords.presenter;

import com.romansavin.fallingwords.model.LevelInfo;
import com.romansavin.fallingwords.model.LevelResult;
import com.romansavin.fallingwords.model.Word;
import com.romansavin.fallingwords.model.engine.GameEngine;
import com.romansavin.fallingwords.model.provider.WordsProvider;
import com.romansavin.fallingwords.view.FallingWordsMVPView;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Scheduler;
import rx.Single;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class FallingWordsPresenterImplTest {

  private static final String WORD = "primary school";
  private static final String ASSUMED_TRANSLATION = "alumno / alumna";
  private static final String RIGHT_TRANSLATION = "escuela primaria";

  private static final int MAX_DURATION_SECONDS = 6;

  private static final int MIN_LEVEL = 1;

  private static final int MAX_HEALTH = 3;

  private static final int MIN_SCORE = 0;
  private static final int WIN_SCORE = 100;

  private FallingWordsMVPView view;

  private List<Word> words;

  private GameEngine gameEngine;

  private Scheduler mainScheduler;

  private Scheduler workerScheduler;

  private FallingWordsPresenterImpl presenter;

  @Before public void beforeEachTest() {
    view = mock(FallingWordsMVPView.class);

    words = new ArrayList<Word>() {{
      add(Word.create("primary school", "escuela primaria"));
      add(Word.create("teacher", "profesor / profesora"));
      add(Word.create("pupil", "alumno / alumna"));
    }};
    final WordsProvider wordsProvider = () -> Single.just(words);

    gameEngine = mock(GameEngine.class);
    when(gameEngine.getLevelInfo()).then(
        invocation ->
            LevelInfo.create(
                MIN_LEVEL,
                MAX_DURATION_SECONDS - MIN_LEVEL,
                MAX_HEALTH,
                MIN_SCORE,
                WORD,
                ASSUMED_TRANSLATION,
                RIGHT_TRANSLATION
            )
    );
    when(gameEngine.startNewGame(words)).then(invocation -> LevelInfo.create(
        MIN_LEVEL,
        MAX_DURATION_SECONDS - MIN_LEVEL,
        MAX_HEALTH,
        MIN_SCORE,
        "", "", ""
    ));

    mainScheduler = Schedulers.from(Runnable::run);
    workerScheduler = Schedulers.from(Runnable::run);

    presenter = new FallingWordsPresenterImpl(view, wordsProvider, gameEngine, mainScheduler, workerScheduler);
  }

  @Test public void loadWords() {
    presenter.loadWords();

    verify(view).showProgress();
    verify(gameEngine).startNewGame(words);
    verify(view).hideProgress();
    verify(view).showLevelStartingInfo(MIN_LEVEL, MAX_DURATION_SECONDS - MIN_LEVEL, MIN_SCORE, MAX_HEALTH);
  }

  @Test public void loadWords_errorWordsLoading() {
    final WordsProvider errorWordsProvider = () -> Single.create(singleSubscriber ->
        singleSubscriber.onError(new IOException())
    );
    presenter = new FallingWordsPresenterImpl(view, errorWordsProvider, gameEngine, mainScheduler, workerScheduler);

    presenter.loadWords();
    verify(view).showProgress();
    verify(gameEngine, never()).startNewGame(words);
    verify(view).hideProgress();
    verify(view, never()).showLevelStartingInfo(MIN_LEVEL, MAX_DURATION_SECONDS - MIN_LEVEL, MIN_SCORE, MAX_HEALTH);
    verify(view).showLoadWordsError();
  }

  @Test public void startLevel() {
    final FallingWordsPresenterImpl presenterMock = spy(presenter);
    doCallRealMethod().when(presenterMock).startLevel();
    presenterMock.startLevel();
    verify(presenterMock).startLevelObservable();
    verify(view).hideLevelStartingInfo();
    verify(gameEngine).startNewLevel();
  }

  @Test public void startLevelObservable() {
    final TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
    presenter.startLevelObservable().subscribe(testSubscriber);
    testSubscriber.awaitTerminalEvent();

    verify(view).showStartGameCounter(3);
    verify(view).showStartGameCounter(2);
    verify(view).showStartGameCounter(1);
    verify(view).showStartGameCounter(0);
    verify(view).hideStartGameCounter();
    verify(view).showGameBoard(MIN_LEVEL, MIN_SCORE, MAX_HEALTH, WORD, ASSUMED_TRANSLATION);
  }

  @Test public void finishGame() {
    presenter.finishGame();

    verify(gameEngine).finishGame();
    verify(view).finishGame();
  }

  @Test public void chooseAnswerYes() {
    when(gameEngine.answer(true)).then(invocation -> LevelResult.create(false, false, MIN_SCORE, RIGHT_TRANSLATION));
    presenter.chooseAnswerYes();

    verify(gameEngine).answer(true);
    verify(view).showResult(false, false, MIN_SCORE, RIGHT_TRANSLATION);
    verify(view).showLevelStartingInfo(MIN_LEVEL, MAX_DURATION_SECONDS - MIN_LEVEL, MIN_SCORE, MAX_HEALTH);
    verify(view).hideGameBoard();
  }

  @Test public void chooseAnswerNo() {
    when(gameEngine.answer(false)).then(invocation -> LevelResult.create(true, false, WIN_SCORE, RIGHT_TRANSLATION));
    presenter.chooseAnswerNo();

    verify(gameEngine).answer(false);
    verify(view).showResult(true, false, WIN_SCORE, RIGHT_TRANSLATION);
    verify(view).showLevelStartingInfo(MIN_LEVEL, MAX_DURATION_SECONDS - MIN_LEVEL, MIN_SCORE, MAX_HEALTH);
    verify(view).hideGameBoard();
  }

  @Test public void chooseAnswerYes_gameOver() {
    when(gameEngine.answer(true)).then(invocation -> LevelResult.create(false, true, MIN_SCORE, RIGHT_TRANSLATION));
    presenter.chooseAnswerYes();

    verify(gameEngine).answer(true);
    verify(view).showResult(false, true, MIN_SCORE, RIGHT_TRANSLATION);
  }

  @Test public void chooseAnswerNo_gameOver() {
    when(gameEngine.answer(false)).then(invocation -> LevelResult.create(true, true, WIN_SCORE, RIGHT_TRANSLATION));
    presenter.chooseAnswerNo();

    verify(gameEngine).answer(false);
    verify(view).showResult(true, true, WIN_SCORE, RIGHT_TRANSLATION);
  }
}
