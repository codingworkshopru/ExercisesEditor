package com.example.exerciseseditor.ui.exercises;

import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.repository.ExercisesRepository;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class ExercisesListViewModel extends ViewModel {
    private ExercisesRepository exercisesRepository;

    @Inject
    public ExercisesListViewModel(ExercisesRepository exercisesRepository) {
        this.exercisesRepository = exercisesRepository;
    }
}
