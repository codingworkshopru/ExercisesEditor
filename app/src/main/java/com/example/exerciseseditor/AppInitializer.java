package com.example.exerciseseditor;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.exerciseseditor.db.initializer.Initializer;

import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 12.06.2017.
 */

@Singleton
public class AppInitializer extends Observable implements Initializer {
    private Map<Integer, Initializer> initializerMap;

    @Inject
    public AppInitializer(Map<Integer, Initializer> commands) {
        this.initializerMap = new TreeMap<>(commands);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void initialize() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (Initializer initializer : initializerMap.values()) {
                    initializer.initialize();
                }
                setChanged();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                notifyObservers();
            }
        }.execute();
    }
}
