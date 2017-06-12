package com.example.exerciseseditor.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

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
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MusclesViewModel.class)
    abstract ViewModel bindsMusclesViewModel(MusclesViewModel musclesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesListViewModel.class)
    abstract ViewModel bindsExercisesListViewModel(ExercisesListViewModel exercisesListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditorViewModel.class)
    abstract ViewModel bindsEditorViewModel(EditorViewModel editorViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelFactory viewModelFactory);
}
