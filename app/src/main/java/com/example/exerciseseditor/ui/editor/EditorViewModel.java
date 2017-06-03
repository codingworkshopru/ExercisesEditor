package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.ExercisesRepository;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class EditorViewModel extends ViewModel {
    static final int INVALID_ID = -1;

    private ExercisesRepository exercisesRepository;
    private MuscleGroupsRepository muscleGroupsRepository;

    private LiveData<ExerciseEntity> exercise;

    @Inject
    EditorViewModel(ExercisesRepository exercisesRepository, MuscleGroupsRepository muscleGroupsRepository) {
        this.exercisesRepository = exercisesRepository;
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    LiveData<ExerciseEntity> getExerciseById(long id) {
        if (exercise == null) {
            if (id != INVALID_ID) {
                exercise = exercisesRepository.getExerciseById(id);
            } else {
                final ExerciseEntity newExercise = new ExerciseEntity();
                newExercise.setId(id);
                exercise = new LiveData<ExerciseEntity>() {
                    {
                        postValue(newExercise);
                    }
                };
            }
        }
        return exercise;
    }

    LiveData<List<MuscleGroupEntity>> getAllMuscleGroups() {
        return muscleGroupsRepository.getMuscleGroups();
    }

    public LiveData<ExerciseEntity> getExercise() {
        return exercise;
    }

    void saveChanges() {
        ExerciseEntity exercise = this.exercise.getValue();
        if (exercise == null)
            return;

        if (exercise.getId() == INVALID_ID) {
            exercisesRepository.create(exercise);
        } else {
            exercisesRepository.update(exercise);
        }
    }
}
