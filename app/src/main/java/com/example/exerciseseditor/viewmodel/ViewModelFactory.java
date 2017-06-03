package com.example.exerciseseditor.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.exerciseseditor.di.ViewModelSubComponent;
import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.exercises.ExercisesListViewModel;
import com.example.exerciseseditor.ui.muscles.MusclesViewModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;

/**
 * Created by Радик on 02.06.2017.
 */

public class ViewModelFactory implements ViewModelProvider.Factory {
    private Map<Class<?>, Provider<? extends ViewModel>> viewModelMap = new HashMap<>();

    public ViewModelFactory(ViewModelSubComponent viewModelSubComponent) {
        viewModelMap.put(MusclesViewModel.class, viewModelSubComponent.musclesViewModel());
        viewModelMap.put(ExercisesListViewModel.class, viewModelSubComponent.exercisesViewModel());
        viewModelMap.put(EditorViewModel.class, viewModelSubComponent.editorViewModel());
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        // noinspection unchecked
        return (T) viewModelMap.get(modelClass).get();
    }
}
