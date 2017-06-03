package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.exercises.ExercisesListViewModel;
import com.example.exerciseseditor.ui.muscles.MusclesViewModel;

import javax.inject.Provider;

import dagger.Subcomponent;

/**
 * Created by Радик on 02.06.2017.
 */

@Subcomponent
public interface ViewModelSubComponent {
    Provider<MusclesViewModel> musclesViewModel();
    Provider<ExercisesListViewModel> exercisesViewModel();
    Provider<EditorViewModel> editorViewModel();

    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }
}
