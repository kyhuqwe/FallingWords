package com.romansavin.fallingwords.model.provider;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.romansavin.fallingwords.model.Word;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
@RunWith(AndroidJUnit4.class)
public class WordsProviderAssetsImplTest {

  private List<Word> words;
  private WordsProviderAssetsImpl wordsProvider;

  @Before public void beforeEachTest() {
    words = new ArrayList<Word>() {{
      add(Word.create("primary school", "escuela primaria"));
      add(Word.create("teacher", "profesor / profesora"));
      add(Word.create("pupil", "alumno / alumna"));
    }};

    wordsProvider = new WordsProviderAssetsImpl(InstrumentationRegistry.getContext());
  }

  @Test public void getWords() throws Exception {
    final TestSubscriber<List<Word>> testSubscriber = new TestSubscriber<>();
    wordsProvider.getWords().subscribe(testSubscriber);
    testSubscriber.assertValue(words);
  }
}
