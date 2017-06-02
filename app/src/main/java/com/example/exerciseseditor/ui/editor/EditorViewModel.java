package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.repository.ExercisesRepository;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class EditorViewModel extends ViewModel {
    private ExercisesRepository exercisesRepository;

    @Inject
    public EditorViewModel(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }
}
