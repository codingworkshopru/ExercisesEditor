package com.example.exerciseseditor;

import android.app.Application;

import com.example.exerciseseditor.di.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

/**
 * Created by Радик on 24.05.2017.
 */

public class ExercisesEditorApplication extends Application implements HasAndroidInjector {
    @Inject DispatchingAndroidInjector<Object> dispatchingAndroidInjector;
    @Inject AppInitializer appInitializer;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .injectContext(getApplicationContext())
                .build()
                .inject(this);
        appInitializer.initialize();
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
