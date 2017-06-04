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
    static final int PHANTOM_ID = 0;

    private int loadingCountdown;

    private ExercisesRepository exercisesRepository;
    private MuscleGroupsRepository muscleGroupsRepository;

    private LiveData<ExerciseEntity> liveExercise;
    private LiveData<List<MuscleGroupEntity>> liveMuscleGroups;
    private ExerciseEntity exercise;
    private List<MuscleGroupEntity> muscleGroups;

    private Runnable callback;

    @Inject
    EditorViewModel(ExercisesRepository exercisesRepository, MuscleGroupsRepository muscleGroupsRepository) {
        this.exercisesRepository = exercisesRepository;
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    List<MuscleGroupEntity> getMuscleGroups() {
        return muscleGroups;
    }

    ExerciseEntity getExercise() {
        return exercise;
    }

    void saveChanges() {
        if (exercise == null)
            return;

        if (exercise.getId() == PHANTOM_ID) {
            exercisesRepository.create(exercise);
        } else {
            exercisesRepository.update(exercise);
        }
    }

    void init(long exerciseId, Runnable callback) {
        this.callback = callback;

        if (exercise != null && muscleGroups != null) {
            onDataLoaded();
            return;
        }

        queryMuscleGroups();

        if (exerciseId == PHANTOM_ID) {
            createNewExercise();
        } else {
            queryExercise(exerciseId);
        }
    }

    private void onDataLoaded() {
        callback.run();
    }

    private void queryMuscleGroups() {
        liveMuscleGroups = muscleGroupsRepository.getMuscleGroups();
        liveMuscleGroups.observeForever(this::onMuscleGroupsLoaded);
        loadingCountdown++;
    }

    private void onMuscleGroupsLoaded(List<MuscleGroupEntity> muscleGroups) {
        if (muscleGroups != null) {
            this.muscleGroups = muscleGroups;
            liveMuscleGroups.removeObserver(this::onMuscleGroupsLoaded);
            decreaseLoadingCountdown();
        }
    }

    private void queryExercise(long exerciseId) {
        liveExercise = exercisesRepository.getExerciseById(exerciseId);
        liveExercise.observeForever(this::onExerciseLoaded);
        loadingCountdown++;
    }

    private void onExerciseLoaded(ExerciseEntity exercise) {
        if (exercise != null) {
            this.exercise = exercise;
            liveExercise.removeObserver(this::onExerciseLoaded);
            decreaseLoadingCountdown();
        }
    }

    private void createNewExercise() {
        exercise = new ExerciseEntity();
    }

    private void decreaseLoadingCountdown() {
        if (--loadingCountdown == 0) {
            onDataLoaded();
        }
    }
}
