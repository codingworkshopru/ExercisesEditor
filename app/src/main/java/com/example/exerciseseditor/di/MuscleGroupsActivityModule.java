package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ui.muscles.MuscleGroupsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Радик on 31.05.2017.
 */

@Module
public abstract class MuscleGroupsActivityModule {
    @ContributesAndroidInjector
    abstract MuscleGroupsActivity contributeExercisesListActivityInjector();
}
