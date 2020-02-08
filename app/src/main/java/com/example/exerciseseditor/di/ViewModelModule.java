package com.example.exerciseseditor.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.exercises.ExercisesListViewModel;
import com.example.exerciseseditor.ui.muscles.MusclesViewModel;
import com.example.exerciseseditor.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by Радик on 08.06.2017.
 */

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MusclesViewModel.class)
    ViewModel bindsMusclesViewModel(MusclesViewModel musclesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesListViewModel.class)
    ViewModel bindsExercisesListViewModel(ExercisesListViewModel exercisesListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditorViewModel.class)
    ViewModel bindsEditorViewModel(EditorViewModel editorViewModel);

    @Binds
    ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
