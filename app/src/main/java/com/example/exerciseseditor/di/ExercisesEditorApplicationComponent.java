package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ExercisesEditorApplication;

import dagger.Component;

/**
 * Created by Радик on 31.05.2017.
 */

@Component(modules = ExercisesListActivityModule.class)
public interface ExercisesEditorApplicationComponent {
    void inject(ExercisesEditorApplication app);
}
