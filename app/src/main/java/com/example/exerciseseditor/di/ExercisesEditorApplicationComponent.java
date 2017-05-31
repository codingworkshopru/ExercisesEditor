package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ExercisesEditorApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
@Component(modules = {
        ExercisesListActivityModule.class,
        DatabaseModule.class,
        ViewModelModule.class
})
public interface ExercisesEditorApplicationComponent {
    void inject(ExercisesEditorApplication app);
}
