package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ExercisesEditorApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
@Component(modules = {
        MuscleGroupsActivityModule.class,
        DatabaseModule.class
})
public interface ExercisesEditorApplicationComponent {
    void inject(ExercisesEditorApplication app);
}
