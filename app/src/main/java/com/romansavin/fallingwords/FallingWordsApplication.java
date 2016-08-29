package com.romansavin.fallingwords;

import android.app.Application;
import android.support.annotation.NonNull;

import com.romansavin.fallingwords.model.provider.WordsProvider;
import com.romansavin.fallingwords.model.provider.WordsProviderAssetsImpl;
import com.squareup.leakcanary.LeakCanary;

import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Roman Savin
 * @since 29.08.2016.
 */
public class FallingWordsApplication extends Application {

  @NonNull private final Scheduler mainScheduler = AndroidSchedulers.mainThread();

  @NonNull private final Scheduler workerScheduler = Schedulers.from(Executors.newFixedThreadPool(3));

  private WordsProvider wordsProvider;

  @Override public void onCreate() {
    super.onCreate();

    wordsProvider = new WordsProviderAssetsImpl(this);
    initializeLeakDetection();
  }

  @NonNull public Scheduler getMainScheduler() {
    return mainScheduler;
  }

  @NonNull public Scheduler getWorkerScheduler() {
    return workerScheduler;
  }

  @NonNull public WordsProvider getWordsProvider() {
    return wordsProvider;
  }

  private void initializeLeakDetection() {
    LeakCanary.install(this);
  }
}
