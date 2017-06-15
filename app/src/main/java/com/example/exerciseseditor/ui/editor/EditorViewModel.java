package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.ExercisesRepository;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    public LiveData<List<MuscleGroupEntity>> getMuscleGroups() {
        return liveMuscleGroups;
    }

    public LiveData<ExerciseEntity> getExercise() {
        return liveExercise;
    }

    LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroups() {
        return liveSecondaryMuscleGroups;
    }

    void addSecondaryMuscleGroupToExercise(int index) {
        if (liveMuscleGroups.getValue() == null) {
            return;
        }

        MuscleGroupEntity muscleGroup = liveMuscleGroups.getValue().get(index);
        if (!Iterables.tryFind(liveSecondaryMuscleGroups.getValue(), (mg) -> mg.getId() == muscleGroup.getId()).isPresent())
            liveSecondaryMuscleGroups.getValue().add(muscleGroup);
    }

    void saveChanges() {
        exercisesRepository.update(liveExercise.getValue());

        List<MuscleGroupEntity> originalSecondaryMuscleGroups = liveSecondaryMuscleGroups.getValue();

        Set<MuscleGroupEntity> newSet = createNewHashSet(liveSecondaryMuscleGroups.getValue());
        Set<MuscleGroupEntity> oldSet = createNewHashSet(originalSecondaryMuscleGroups);
        Set<MuscleGroupEntity> commonSet = Sets.intersection(newSet, oldSet);

        List<MuscleGroupEntity> toDelete = Lists.newArrayList(Sets.difference(oldSet, commonSet));
        List<MuscleGroupEntity> toCreate = Lists.newArrayList(Sets.difference(newSet, commonSet));

        exercisesRepository.addSecondaryMuscleGroupsToExercise(liveExercise.getValue(), toCreate);
        exercisesRepository.deleteSecondaryMuscleGroupsFromExercise(liveExercise.getValue(), toDelete);
    }

    private static <T> Set<T> createNewHashSet(Collection<T> from) {
        return from == null ? Sets.newHashSet() : Sets.newHashSet(from);
    }

    void init(long exerciseId) {
        if (liveExercise != null) {
            return;
        }

        liveExercise = exercisesRepository.getExerciseById(exerciseId);
        liveSecondaryMuscleGroups = exercisesRepository.getSecondaryMuscleGroupsForExercise(exerciseId);
        liveMuscleGroups = muscleGroupsRepository.getMuscleGroups();
    }
}
