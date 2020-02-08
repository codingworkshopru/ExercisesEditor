package com.example.exerciseseditor.ui.editor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.ExercisesRepository;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;
import com.example.exerciseseditor.util.LiveDataUtil;
import com.google.common.base.Preconditions;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class EditorViewModel extends ViewModel {
    static final int PHANTOM_ID = 0;

    private ExercisesRepository exercisesRepository;
    private MuscleGroupsRepository muscleGroupsRepository;

    private LiveData<ExerciseEntity> exercise;
    private LiveData<List<MuscleGroupEntity>> muscleGroups;
    private LiveData<List<MuscleGroupEntity>> secondaryMuscleGroups;

    @Inject
    EditorViewModel(ExercisesRepository exercisesRepository, MuscleGroupsRepository muscleGroupsRepository) {
        this.exercisesRepository = exercisesRepository;
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    void init(long exerciseId) {
        if (exercise == null) {
            muscleGroups = muscleGroupsRepository.getMuscleGroups();
            secondaryMuscleGroups = exercisesRepository.getSecondaryMuscleGroupsForExercise(exerciseId);
            exercise = getLiveExercise(exerciseId);
        }
    }

    private LiveData<ExerciseEntity> getLiveExercise(long exerciseId) {
        if (exerciseId == PHANTOM_ID) {
            return Transformations.map(muscleGroups, (mg) -> new ExerciseEntity());
        } else {
            return Transformations.switchMap(muscleGroups,
                    (mg) -> mg == null
                            ? LiveDataUtil.getAbsent()
                            : exercisesRepository.getExerciseById(exerciseId));
        }
    }

    public LiveData<List<MuscleGroupEntity>> getMuscleGroups() {
        return muscleGroups;
    }

    public LiveData<ExerciseEntity> getExercise() {
        return exercise;
    }

    LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    void saveChanges() {
        ExerciseEntity ex = Preconditions.checkNotNull(exercise.getValue(), "Can't save exercise because it's null");

        if (ex.getId() == PHANTOM_ID)
            exercisesRepository.create(ex, secondaryMuscleGroups.getValue());
        else
            exercisesRepository.update(ex, secondaryMuscleGroups.getValue());
    }

    void addSecondaryMuscleGroupToExercise(int selectedMuscleGroupIndex) {
        List<MuscleGroupEntity> staticSecondaryMuscleGroups = returnSecondaryMuscleGroups();
        List<MuscleGroupEntity> staticMuscleGroups = Preconditions.checkNotNull(muscleGroups.getValue(), "Muscle groups have not been loaded");

        MuscleGroupEntity muscleGroup = staticMuscleGroups.get(selectedMuscleGroupIndex);
        if (!staticSecondaryMuscleGroups.contains(muscleGroup)) {
            staticSecondaryMuscleGroups.add(muscleGroup);
        }
    }

    void removeSecondaryMuscleGroupFromExercise(int secondaryMuscleGroupIndex) {
        returnSecondaryMuscleGroups().remove(secondaryMuscleGroupIndex);
    }

    private List<MuscleGroupEntity> returnSecondaryMuscleGroups() {
        return Preconditions.checkNotNull(secondaryMuscleGroups.getValue(), "Secondary muscle groups have not been loaded");
    }
}
