package com.romansavin.fallingwords.view;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Roman Savin
 * @since 28.08.2016.
 */

@SuppressWarnings("ConstantConditions")
public class FallingWordsViewNullObjectTest {

  private static final String WORD = "WORD";
  
  private static final String TRANSLATION = "TRANSLATION";

  private FallingWordsViewNullObject initializedWithView;

  private FallingWordsViewNullObject initializedWithNull;

  private FallingWordsViewNullObject initializedWithDefaultConstructor;

  private FallingWordsMVPView view;

  @Before public void beforeEachTest() {
    view = mock(FallingWordsMVPView.class);
    initializedWithView = new FallingWordsViewNullObject(view);
    initializedWithNull = new FallingWordsViewNullObject(null);
    initializedWithDefaultConstructor = new FallingWordsViewNullObject();
  }

  @Test public void showStartButton_initializedWithView() {
    initializedWithView.showStartButton();
    verify(view).showStartButton();
  }

  @Test public void showStartButton_initializedWithNull() {
    initializedWithNull.showStartButton();
    verify(view, never()).showStartButton();
  }

  @Test public void showStartButton_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showStartButton();
    verify(view, never()).showStartButton();
  }

  @Test public void hideStartButton_initializedWithView() {
    initializedWithView.hideStartButton();
    verify(view).hideStartButton();
  }

  @Test public void hideStartButton_initializedWithNull() {
    initializedWithNull.hideStartButton();
    verify(view, never()).hideStartButton();
  }

  @Test public void hideStartButton_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.hideStartButton();
    verify(view, never()).hideStartButton();
  }

  @Test public void blurGameSpace_initializedWithView() {
    initializedWithView.blurGameSpace();
    verify(view).blurGameSpace();
  }

  @Test public void blurGameSpace_initializedWithNull() {
    initializedWithNull.blurGameSpace();
    verify(view, never()).blurGameSpace();
  }

  @Test public void blurGameSpace_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.blurGameSpace();
    verify(view, never()).blurGameSpace();
  }

  @Test public void showGameSpace_initializedWithView() {
    initializedWithView.showGameSpace();
    verify(view).showGameSpace();
  }

  @Test public void showGameSpace_initializedWithNull() {
    initializedWithNull.showGameSpace();
    verify(view, never()).showGameSpace();
  }

  @Test public void showGameSpace_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showGameSpace();
    verify(view, never()).showGameSpace();
  }

  @Test public void showStartGameCounter_initializedWithView() {
    initializedWithView.showStartGameCounter(1);
    verify(view).showStartGameCounter(1);
  }

  @Test public void showStartGameCounter_initializedWithNull() {
    initializedWithNull.showStartGameCounter(1);
    verify(view, never()).showStartGameCounter(1);
  }

  @Test public void showStartGameCounter_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showStartGameCounter(1);
    verify(view, never()).showStartGameCounter(1);
  }

  @Test public void hideStartGameCounter_initializedWithView() {
    initializedWithView.hideStartGameCounter();
    verify(view).hideStartGameCounter();
  }

  @Test public void hideStartGameCounter_initializedWithNull() {
    initializedWithNull.hideStartGameCounter();
    verify(view, never()).hideStartGameCounter();
  }

  @Test public void hideStartGameCounter_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.hideStartGameCounter();
    verify(view, never()).hideStartGameCounter();
  }

  @Test public void initGameLevel_initializedWithView() {
    initializedWithView.initGameLevel(1, WORD, TRANSLATION);
    verify(view).initGameLevel(1, WORD, TRANSLATION);
  }

  @Test public void initGameLevel_initializedWithNull() {
    initializedWithNull.initGameLevel(1, WORD, TRANSLATION);
    verify(view, never()).initGameLevel(1, WORD, TRANSLATION);
  }

  @Test public void initGameLevel_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.initGameLevel(1, WORD, TRANSLATION);
    verify(view, never()).initGameLevel(1, WORD, TRANSLATION);
  }

  @Test public void updateFallingWordPosition_initializedWithView() {
    initializedWithView.updateFallingWordPosition(1);
    verify(view).updateFallingWordPosition(1);
  }

  @Test public void updateFallingWordPosition_initializedWithNull() {
    initializedWithNull.updateFallingWordPosition(1);
    verify(view, never()).updateFallingWordPosition(1);
  }

  @Test public void updateFallingWordPosition_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.updateFallingWordPosition(1);
    verify(view, never()).updateFallingWordPosition(1);
  }

  @Test public void showFallingCounter_initializedWithView() {
    initializedWithView.showFallingCounter(1);
    verify(view).showFallingCounter(1);
  }

  @Test public void showFallingCounter_initializedWithNull() {
    initializedWithNull.showFallingCounter(1);
    verify(view, never()).showFallingCounter(1);
  }

  @Test public void showFallingCounter_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showFallingCounter(1);
    verify(view, never()).showFallingCounter(1);
  }

  @Test public void showResult_initializedWithView() {
    initializedWithView.showResult(true, TRANSLATION);
    verify(view).showResult(true, TRANSLATION);
  }

  @Test public void showResult_initializedWithNull() {
    initializedWithNull.showResult(true, TRANSLATION);
    verify(view, never()).showResult(true, TRANSLATION);
  }

  @Test public void showResult_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showResult(true, TRANSLATION);
    verify(view, never()).showResult(true, TRANSLATION);
  }
}
