package com.example.exerciseseditor.ui.exercises;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.repository.ExercisesRepository;

import java.util.List;

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

    public LiveData<List<ExerciseEntity>> getExercisesForMuscleGroup(long id) {
        return exercisesRepository.getExercisesForMuscleGroup(id);
    }

    public void remove(Exercise exercise) {
        exercisesRepository.remove(exercise);
    }
}
