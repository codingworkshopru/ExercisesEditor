package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.ExercisesRepository;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;
import com.google.common.collect.Iterables;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class EditorViewModel extends ViewModel {
    static final int PHANTOM_ID = 0;

    private ExercisesRepository exercisesRepository;
    private MuscleGroupsRepository muscleGroupsRepository;

    private LiveData<ExerciseEntity> liveExercise;
    private LiveData<List<MuscleGroupEntity>> liveMuscleGroups;
    private LiveData<List<MuscleGroupEntity>> liveSecondaryMuscleGroups;

    @Inject
    EditorViewModel(ExercisesRepository exercisesRepository, MuscleGroupsRepository muscleGroupsRepository) {
        this.exercisesRepository = exercisesRepository;
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    void init(long exerciseId) {
        if (liveExercise != null) {
            return;
        }

        liveExercise = exercisesRepository.getExerciseById(exerciseId);
        liveSecondaryMuscleGroups = exercisesRepository.getSecondaryMuscleGroupsForExercise(exerciseId);
        liveMuscleGroups = muscleGroupsRepository.getMuscleGroups();
    }

    public LiveData<List<MuscleGroupEntity>> getMuscleGroups() {
        return liveMuscleGroups;
    }

    public LiveData<ExerciseEntity> getExercise() {
        return liveExercise;
    }

    LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroups() {
        return liveSecondaryMuscleGroups;
    }

    void addSecondaryMuscleGroupToExercise(int selectedMuscleGroupIndex) {
        MuscleGroupEntity muscleGroup = liveMuscleGroups.getValue().get(selectedMuscleGroupIndex);
        if (!Iterables.tryFind(liveSecondaryMuscleGroups.getValue(), (mg) -> mg.getId() == muscleGroup.getId()).isPresent())
            liveSecondaryMuscleGroups.getValue().add(muscleGroup);
    }

    void removeSecondaryMuscleGroupFromExercise(int secondaryMuscleGroupIndex) {
        liveSecondaryMuscleGroups.getValue().remove(secondaryMuscleGroupIndex);
    }

    void saveChanges() {
        exercisesRepository.update(liveExercise.getValue(), liveSecondaryMuscleGroups.getValue());
    }
}
