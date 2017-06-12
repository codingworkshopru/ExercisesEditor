package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ui.editor.EditorActivity;
import com.example.exerciseseditor.ui.exercises.ExercisesListActivity;
import com.example.exerciseseditor.ui.muscles.MuscleGroupsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Радик on 31.05.2017.
 */

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MuscleGroupsActivity contributeMuscleGroupsListActivityInjector();

    @ContributesAndroidInjector
    abstract ExercisesListActivity contributeExercisesListActivityInjector();

    @ContributesAndroidInjector
    abstract EditorActivity contributeEditorActivityInjector();
}
