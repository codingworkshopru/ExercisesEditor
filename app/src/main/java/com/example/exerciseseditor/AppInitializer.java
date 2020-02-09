package com.example.exerciseseditor;

import androidx.lifecycle.MutableLiveData;

import com.example.exerciseseditor.db.initializer.Initializer;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 12.06.2017.
 */

@Singleton
public class AppInitializer implements Initializer {
    private Map<Integer, Initializer> initializerMap;
    private Executor executor;
    private MutableLiveData<Boolean> initialized = new MutableLiveData<>();

    @Inject
    AppInitializer(Map<Integer, Initializer> commands, Executor executor) {
        this.initializerMap = new TreeMap<>(commands);
        this.executor = executor;
    }

    public MutableLiveData<Boolean> getInitialized() {
        return initialized;
    }

    @Override
    public void initialize() {
        CountDownLatch cdl = new CountDownLatch(1);
        executor.execute(() -> {
            for (Initializer initializer : initializerMap.values()) {
                initializer.initialize();
            }
            initialized.postValue(true);
            cdl.countDown();
        });
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
