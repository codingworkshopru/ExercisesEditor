package com.example.exerciseseditor.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.exercises.ExercisesListViewModel;
import com.example.exerciseseditor.ui.muscles.MuscleGroupsViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 31.05.2017.
 */

@Module
class ViewModelModule {
    @Provides
    EditorViewModel provideEditorViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(EditorViewModel.class);
    }

    @Provides
    ExercisesListViewModel provideExercisesListViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(ExercisesListViewModel.class);
    }

    @Provides
    MuscleGroupsViewModel provideMuscleGroupsViewModel(FragmentActivity activity) {
        return ViewModelProviders.of(activity).get(MuscleGroupsViewModel.class);
    }
}
