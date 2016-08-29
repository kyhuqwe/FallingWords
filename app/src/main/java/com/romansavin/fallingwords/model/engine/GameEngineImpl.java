package com.romansavin.fallingwords.model.engine;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.romansavin.fallingwords.model.LevelInfo;
import com.romansavin.fallingwords.model.LevelResult;
import com.romansavin.fallingwords.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;

import static rx.Observable.interval;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class GameEngineImpl implements GameEngine {

  private static final int MS_IN_SECOND = 1000;
  private static final int UPDATE_DROP_PERCENTAGE_MS = 100;

  private static final int MAX_DURATION_SECONDS = 6;

  private static final int MIN_SCORE = 0;

  private static final int MIN_LEVEL = 1;
  private static final int MAX_LEVEL = 5;

  private static final int MIN_HEALTH = 1;
  private static final int MAX_HEALTH = 3;

  private static final int SCORE_LEVEL_STEP = 100;

  private final Random randomGenerator = new Random();

  @NonNull private final List<Word> usedWords = new ArrayList<>();

  @NonNull private final List<Word> words = new ArrayList<>();

  private int level;
  private int score;
  private int duration;
  private int health;

  @Nullable private String sourceWord;
  @Nullable private String assumedTranslation;
  @Nullable private String rightTranslation;

  @Override public void startNewGame(@NonNull final List<Word> words) throws IllegalArgumentException {
    if (words.isEmpty()) {
      throw new IllegalArgumentException("Words list can't be empty");
    }

    this.words.addAll(words);
    level = MIN_LEVEL;
    score = MIN_SCORE;
    health = MAX_HEALTH;
    duration = getDurationByLevel();
  }

  @Override public void finishGame() {
    words.clear();
    usedWords.clear();
    level = MIN_LEVEL;
    score = MIN_SCORE;
    duration = getDurationByLevel();
    sourceWord = null;
    assumedTranslation = null;
    rightTranslation = null;
  }

  @Override public void startNewLevel() {
    if (words.isEmpty() && usedWords.isEmpty()) {
      throw new IllegalStateException("Empty words array");
    } else if (words.isEmpty() && !usedWords.isEmpty()) {
      words.addAll(usedWords);
      usedWords.clear();
    }

    final Word randomWord = words.get(randomGenerator.nextInt(words.size()));
    sourceWord = randomWord.word();
    rightTranslation = randomWord.translation();

    if (useRightTranslation()) {
      assumedTranslation = rightTranslation;
    } else {
      final Word randomAssumedWord = words.get(randomGenerator.nextInt(words.size()));
      assumedTranslation = randomAssumedWord.translation();
    }

    words.remove(randomWord);
    usedWords.add(randomWord);
  }

  @Override public LevelResult answer(final boolean isYesAnswer) {
    if (assumedTranslation == null) {
      throw new IllegalStateException("Assumed translation is null");
    }
    if (rightTranslation == null) {
      throw new IllegalStateException("Right translation is null");
    }

    final boolean isWin = assumedTranslation.equals(rightTranslation) == isYesAnswer;
    if (isWin) {
      increaseLevelValues();
    } else {
      decreaseHealth();
    }

    final boolean isGameOver = level > MAX_LEVEL || health < MIN_HEALTH;
    return LevelResult.create(isWin, isGameOver, score, rightTranslation);
  }

  @Override public Observable<Integer> getDropPercentageObservable() {
    final int maxStepsCount = duration * MS_IN_SECOND / UPDATE_DROP_PERCENTAGE_MS;
    final float dropVelocity = 100 / (float) maxStepsCount; // Velocity in Percentages per step
    return interval(UPDATE_DROP_PERCENTAGE_MS, TimeUnit.MILLISECONDS)
        .map(step -> step * dropVelocity)
        .map(Math::round)
        .take(maxStepsCount + 1);
  }

  @Override public Observable<Integer> getTimeCounterObservable() {
    return interval(1, TimeUnit.SECONDS)
        .map(value -> duration - value.intValue())
        .take(duration + 1);
  }

  @Override public LevelInfo getLevelInfo() {
    if (assumedTranslation == null) {
      throw new IllegalStateException("Assumed translation is null");
    }
    if (rightTranslation == null) {
      throw new IllegalStateException("Right translation is null");
    }
    if (sourceWord == null) {
      throw new IllegalStateException("Word is null");
    }

    return LevelInfo.create(level, duration, health, score, sourceWord, assumedTranslation, rightTranslation);
  }

  private boolean useRightTranslation() {
    return Math.random() < 0.5;
  }

  private void increaseLevelValues() {
    score += SCORE_LEVEL_STEP * level;
    ++level;
    duration = getDurationByLevel();
  }

  private int getDurationByLevel() {
    return MAX_DURATION_SECONDS - level;
  }

  private void decreaseHealth() {
    --health;
  }
}
