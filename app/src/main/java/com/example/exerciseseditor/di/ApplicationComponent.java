package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ExercisesEditorApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ExercisesEditorApplication app);
}
