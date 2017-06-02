package com.example.exerciseseditor;

import android.app.Activity;
import android.app.Application;

import com.example.exerciseseditor.db.initializer.DatabaseInitializer;
import com.example.exerciseseditor.di.DaggerApplicationComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by Радик on 24.05.2017.
 */

public class ExercisesEditorApplication extends Application implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;
    @Inject DatabaseInitializer initializer;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.create().inject(this);
        initializer.initialiseDatabase(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
