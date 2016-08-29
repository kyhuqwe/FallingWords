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

  @Test public void showProgress_initializedWithView() {
    initializedWithView.showProgress();
    verify(view).showProgress();
  }

  @Test public void showProgress_initializedWithNull() {
    initializedWithNull.showProgress();
    verify(view, never()).showProgress();
  }

  @Test public void showProgress_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showProgress();
    verify(view, never()).showProgress();
  }

  @Test public void hideProgress_initializedWithView() {
    initializedWithView.hideProgress();
    verify(view).hideProgress();
  }

  @Test public void hideProgress_initializedWithNull() {
    initializedWithNull.hideProgress();
    verify(view, never()).hideProgress();
  }

  @Test public void hideProgress_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.hideProgress();
    verify(view, never()).hideProgress();
  }

  @Test public void showLoadWordsError_initializedWithView() {
    initializedWithView.showLoadWordsError();
    verify(view).showLoadWordsError();
  }

  @Test public void showLoadWordsError_initializedWithNull() {
    initializedWithNull.showLoadWordsError();
    verify(view, never()).showLoadWordsError();
  }

  @Test public void showLoadWordsError_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showLoadWordsError();
    verify(view, never()).showLoadWordsError();
  }

  @Test public void showLevelStartingInfo_initializedWithView() {
    initializedWithView.showLevelStartingInfo(1, 1, 1, 1);
    verify(view).showLevelStartingInfo(1, 1, 1, 1);
  }

  @Test public void showLevelStartingInfo_initializedWithNull() {
    initializedWithNull.showLevelStartingInfo(1, 1, 1, 1);
    verify(view, never()).showLevelStartingInfo(1, 1, 1, 1);
  }

  @Test public void showLevelStartingInfo_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showLevelStartingInfo(1, 1, 1, 1);
    verify(view, never()).showLevelStartingInfo(1, 1, 1, 1);
  }

  @Test public void hideLevelStartingInfo_initializedWithView() {
    initializedWithView.hideLevelStartingInfo();
    verify(view).hideLevelStartingInfo();
  }

  @Test public void hideLevelStartingInfo_initializedWithNull() {
    initializedWithNull.hideLevelStartingInfo();
    verify(view, never()).hideLevelStartingInfo();
  }

  @Test public void hideLevelStartingInfo_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.hideLevelStartingInfo();
    verify(view, never()).hideLevelStartingInfo();
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

  @Test public void showGameBoard_initializedWithView() {
    initializedWithView.showGameBoard(1, 1, 1, WORD, TRANSLATION);
    verify(view).showGameBoard(1, 1, 1, WORD, TRANSLATION);
  }

  @Test public void showGameBoard_initializedWithNull() {
    initializedWithNull.showGameBoard(1, 1, 1, WORD, TRANSLATION);
    verify(view, never()).showGameBoard(1, 1, 1, WORD, TRANSLATION);
  }

  @Test public void showGameBoard_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showGameBoard(1, 1, 1, WORD, TRANSLATION);
    verify(view, never()).showGameBoard(1, 1, 1, WORD, TRANSLATION);
  }

  @Test public void hideGameBoard_initializedWithView() {
    initializedWithView.hideGameBoard();
    verify(view).hideGameBoard();
  }

  @Test public void hideGameBoard_initializedWithNull() {
    initializedWithNull.hideGameBoard();
    verify(view, never()).hideGameBoard();
  }

  @Test public void hideGameBoard_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.hideGameBoard();
    verify(view, never()).hideGameBoard();
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

  @Test public void showFallingTimeCounter_initializedWithView() {
    initializedWithView.showFallingTimeCounter(1);
    verify(view).showFallingTimeCounter(1);
  }

  @Test public void showFallingTimeCounter_initializedWithNull() {
    initializedWithNull.showFallingTimeCounter(1);
    verify(view, never()).showFallingTimeCounter(1);
  }

  @Test public void showFallingTimeCounter_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showFallingTimeCounter(1);
    verify(view, never()).showFallingTimeCounter(1);
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

  @Test public void showGameOverInfo_initializedWithView() {
    initializedWithView.showGameOverInfo(0);
    verify(view).showGameOverInfo(0);
  }

  @Test public void showGameOverInfo_initializedWithNull() {
    initializedWithNull.showGameOverInfo(0);
    verify(view, never()).showGameOverInfo(0);
  }

  @Test public void showGameOverInfo_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.showGameOverInfo(0);
    verify(view, never()).showGameOverInfo(0);
  }

  @Test public void finish_initializedWithView() {
    initializedWithView.finish();
    verify(view).finish();
  }

  @Test public void finish_initializedWithNull() {
    initializedWithNull.finish();
    verify(view, never()).finish();
  }

  @Test public void finish_initializedWithDefaultConstructor() {
    initializedWithDefaultConstructor.finish();
    verify(view, never()).finish();
  }
}
