package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ExercisesListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Радик on 31.05.2017.
 */

@Module
public abstract class ExercisesListActivityModule {
    @ContributesAndroidInjector
    abstract ExercisesListActivity contributeExercisesListActivityInjector();
}
