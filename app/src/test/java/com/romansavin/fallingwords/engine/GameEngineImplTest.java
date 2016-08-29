package com.romansavin.fallingwords.engine;

import com.romansavin.fallingwords.model.LevelInfo;
import com.romansavin.fallingwords.model.LevelResult;
import com.romansavin.fallingwords.model.Word;
import com.romansavin.fallingwords.model.engine.GameEngineImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class GameEngineImplTest {

  private static final int MAX_DURATION_SECONDS = 6;

  private static final int MIN_LEVEL = 1;
  private static final int MAX_LEVEL = 5;

  private static final int MIN_HEALTH = 1;
  private static final int MAX_HEALTH = 3;

  private static final int SCORE_LEVEL_STEP = 100;

  private List<Word> words;
  private GameEngineImpl gameEngine;

  @Before public void beforeEachTest() {
    words = new ArrayList<Word>() {{
      add(Word.create("primary school", "escuela primaria"));
      add(Word.create("teacher", "profesor / profesora"));
      add(Word.create("pupil", "alumno / alumna"));
    }};

    gameEngine = new GameEngineImpl();
  }

  @Test public void startNewGame_emptyWords() {
    assertThatThrownBy(() -> gameEngine.startNewGame(Collections.emptyList()))
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test public void finishGame() {
    gameEngine.finishGame();

    assertThatThrownBy(() -> gameEngine.startNewLevel()).isExactlyInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> gameEngine.answer(true)).isExactlyInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> gameEngine.getLevelInfo()).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void finishGame_afterStartGame() {
    gameEngine.startNewGame(words);
    gameEngine.finishGame();

    assertThatThrownBy(() -> gameEngine.startNewLevel()).isExactlyInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> gameEngine.answer(true)).isExactlyInstanceOf(IllegalStateException.class);
    assertThatThrownBy(() -> gameEngine.getLevelInfo()).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void startNewLevel() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final LevelInfo levelInfo = gameEngine.getLevelInfo();
    assertThat(levelInfo.duration()).isEqualTo(MAX_DURATION_SECONDS - MIN_LEVEL);
    assertThat(levelInfo.health()).isEqualTo(MAX_HEALTH);
    assertThat(levelInfo.level()).isEqualTo(MIN_LEVEL);
  }

  @Test public void startNewLevel_reuseUsedWords() {
    gameEngine.startNewGame(words);

    final List<String> usedWords = new ArrayList<>();
    for (int i = 0; i < MAX_LEVEL; ++i) {
      gameEngine.startNewLevel();
      final String word = gameEngine.getLevelInfo().word();
      if (i < words.size()) {
        assertThat(usedWords).doesNotContain(word);
        usedWords.add(word);
      } else {
        assertThat(usedWords).contains(word);
      }
    }

    final LevelInfo levelInfo = gameEngine.getLevelInfo();
    assertThat(levelInfo.duration()).isEqualTo(MAX_DURATION_SECONDS - MIN_LEVEL);
    assertThat(levelInfo.health()).isEqualTo(MAX_HEALTH);
    assertThat(levelInfo.level()).isEqualTo(MIN_LEVEL);
  }

  @Test public void startNewLevel_beforeStartGame() {
    assertThatThrownBy(() -> gameEngine.startNewLevel()).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void answer_win() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final LevelInfo levelInfoBeforeAnswer = gameEngine.getLevelInfo();
    final LevelResult levelResult = gameEngine.answer(
        levelInfoBeforeAnswer.rightTranslation().equals(levelInfoBeforeAnswer.assumedTranslation())
    );
    final LevelInfo levelInfoAfterAnswer = gameEngine.getLevelInfo();

    assertThat(levelResult).isEqualTo(LevelResult.create(
        true,
        false,
        SCORE_LEVEL_STEP * levelInfoBeforeAnswer.level(),
        levelInfoBeforeAnswer.rightTranslation()
    ));
    assertThat(levelInfoAfterAnswer).isEqualTo(LevelInfo.create(
        levelInfoBeforeAnswer.level() + 1,
        levelInfoBeforeAnswer.duration() - 1,
        levelInfoBeforeAnswer.health(),
        SCORE_LEVEL_STEP * levelInfoBeforeAnswer.level(),
        levelInfoBeforeAnswer.word(),
        levelInfoBeforeAnswer.assumedTranslation(),
        levelInfoBeforeAnswer.rightTranslation()
    ));
  }

  @Test public void answer_lose() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final LevelInfo levelInfoBeforeAnswer = gameEngine.getLevelInfo();
    final LevelResult levelResult = gameEngine.answer(
        !levelInfoBeforeAnswer.rightTranslation().equals(levelInfoBeforeAnswer.assumedTranslation())
    );
    final LevelInfo levelInfoAfterAnswer = gameEngine.getLevelInfo();

    assertThat(levelResult).isEqualTo(LevelResult.create(
        false,
        false,
        0,
        levelInfoBeforeAnswer.rightTranslation()
    ));
    assertThat(levelInfoAfterAnswer).isEqualTo(LevelInfo.create(
        levelInfoBeforeAnswer.level(),
        levelInfoBeforeAnswer.duration(),
        levelInfoBeforeAnswer.health() - 1,
        0,
        levelInfoBeforeAnswer.word(),
        levelInfoBeforeAnswer.assumedTranslation(),
        levelInfoBeforeAnswer.rightTranslation()
    ));
  }

  @Test public void answer_winMaxTimes() {
    gameEngine.startNewGame(words);

    LevelResult lastLevelResult;
    LevelInfo lastLevelInfoBeforeAnswer;
    LevelInfo lastLevelInfoAfterAnswer;
    do {
      gameEngine.startNewLevel();
      lastLevelInfoBeforeAnswer = gameEngine.getLevelInfo();
      lastLevelResult = gameEngine.answer(
          lastLevelInfoBeforeAnswer.rightTranslation().equals(lastLevelInfoBeforeAnswer.assumedTranslation())
      );
      lastLevelInfoAfterAnswer = gameEngine.getLevelInfo();
    } while (!lastLevelResult.isGameOver());

    assertThat(lastLevelResult).isEqualTo(LevelResult.create(true, true, 1500, lastLevelInfoBeforeAnswer.rightTranslation()));
    assertThat(lastLevelInfoAfterAnswer).isEqualTo(LevelInfo.create(
        MAX_LEVEL + 1,
        MAX_DURATION_SECONDS - MAX_LEVEL - 1,
        MAX_HEALTH,
        1500,
        lastLevelInfoBeforeAnswer.word(),
        lastLevelInfoBeforeAnswer.assumedTranslation(),
        lastLevelInfoBeforeAnswer.rightTranslation()
    ));
  }

  @Test public void answer_loseMaxTimes() {
    gameEngine.startNewGame(words);

    LevelResult lastLevelResult;
    LevelInfo lastLevelInfoBeforeAnswer;
    LevelInfo lastLevelInfoAfterAnswer;
    do {
      gameEngine.startNewLevel();
      lastLevelInfoBeforeAnswer = gameEngine.getLevelInfo();
      lastLevelResult = gameEngine.answer(
          !lastLevelInfoBeforeAnswer.rightTranslation().equals(lastLevelInfoBeforeAnswer.assumedTranslation())
      );
      lastLevelInfoAfterAnswer = gameEngine.getLevelInfo();
    } while (!lastLevelResult.isGameOver());

    assertThat(lastLevelResult).isEqualTo(LevelResult.create(false, true, 0, lastLevelInfoBeforeAnswer.rightTranslation()));
    assertThat(lastLevelInfoAfterAnswer).isEqualTo(LevelInfo.create(
        MIN_LEVEL,
        MAX_DURATION_SECONDS - MIN_LEVEL,
        MIN_HEALTH - 1,
        0,
        lastLevelInfoBeforeAnswer.word(),
        lastLevelInfoBeforeAnswer.assumedTranslation(),
        lastLevelInfoBeforeAnswer.rightTranslation()
    ));
  }

  @Test public void answer_beforeStartGame() {
    assertThatThrownBy(() -> gameEngine.answer(true)).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void answer_beforeStartLevel() {
    gameEngine.startNewGame(words);
    assertThatThrownBy(() -> gameEngine.answer(true)).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void getDropPercentageObservable() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
    gameEngine.getDropPercentageObservable().subscribe(testSubscriber);

    final List<Integer> expectedStepValues = new ArrayList<>();
    for (int i = 0; i <= 50; ++i) {
      expectedStepValues.add(i * 2);
    }

    testSubscriber.awaitTerminalEvent();
    testSubscriber.assertValues(expectedStepValues.toArray(new Integer[expectedStepValues.size()]));
  }

  @Test public void getTimeCounterObservable() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
    gameEngine.getTimeCounterObservable().subscribe(testSubscriber);

    final List<Integer> expectedStepValues = new ArrayList<>();
    for (int i = 1; i <= MAX_DURATION_SECONDS; ++i) {
      expectedStepValues.add(MAX_DURATION_SECONDS - i);
    }

    testSubscriber.awaitTerminalEvent();
    testSubscriber.assertValues(expectedStepValues.toArray(new Integer[expectedStepValues.size()]));
  }

  @Test public void getLevelInfo() {
    gameEngine.startNewGame(words);
    gameEngine.startNewLevel();

    final LevelInfo levelInfo = gameEngine.getLevelInfo();
    assertThat(levelInfo.level()).isEqualTo(MIN_LEVEL);
    assertThat(levelInfo.duration()).isEqualTo(MAX_DURATION_SECONDS - MIN_LEVEL);
    assertThat(levelInfo.health()).isEqualTo(MAX_HEALTH);
    assertThat(words).contains(Word.create(levelInfo.word(), levelInfo.rightTranslation()));
  }

  @Test public void getLevelInfo_beforeStartGame() {
    assertThatThrownBy(() -> gameEngine.getLevelInfo()).isExactlyInstanceOf(IllegalStateException.class);
  }

  @Test public void getLevelInfo_beforeStartLevel() {
    gameEngine.startNewGame(words);
    assertThatThrownBy(() -> gameEngine.getLevelInfo()).isExactlyInstanceOf(IllegalStateException.class);
  }
}
