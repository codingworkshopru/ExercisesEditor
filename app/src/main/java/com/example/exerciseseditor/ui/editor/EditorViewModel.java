package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    private int loadingCountdown;

    private ExercisesRepository exercisesRepository;
    private MuscleGroupsRepository muscleGroupsRepository;

    private MutableLiveData<Boolean> dataLoaded = new MutableLiveData<>();
    private LiveData<ExerciseEntity> liveExercise;
    private LiveData<List<MuscleGroupEntity>> liveMuscleGroups;
    private LiveData<List<MuscleGroupEntity>> liveSecondaryMuscleGroups;
    private ExerciseEntity exercise;
    private List<MuscleGroupEntity> secondaryMuscleGroups;

    @Inject
    EditorViewModel(ExercisesRepository exercisesRepository, MuscleGroupsRepository muscleGroupsRepository) {
        this.exercisesRepository = exercisesRepository;
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    public List<MuscleGroupEntity> getMuscleGroups() {
        return liveMuscleGroups.getValue();
    }

    MutableLiveData<Boolean> getDataLoaded() {
        return dataLoaded;
    }

    ExerciseEntity getExercise() {
        return exercise;
    }

    List<MuscleGroupEntity> getSecondaryMuscleGroups() {
        return secondaryMuscleGroups;
    }

    void addSecondaryMuscleGroupToExercise(int index) {
        MuscleGroupEntity muscleGroup = getMuscleGroups().get(index);
        if (!Iterables.tryFind(secondaryMuscleGroups, (mg) -> mg.getId() == muscleGroup.getId()).isPresent())
            secondaryMuscleGroups.add(muscleGroup);
    }

    void saveChanges() {
        if (exercise == null)
            return;

        if (exercise.getId() == PHANTOM_ID) {
            exercisesRepository.create(exercise);
        } else {
            exercisesRepository.update(exercise);
        }

        List<MuscleGroupEntity> originalSecondaryMuscleGroups = liveSecondaryMuscleGroups.getValue();

        Set<MuscleGroupEntity> newSet = createNewHashSet(secondaryMuscleGroups);
        Set<MuscleGroupEntity> oldSet = createNewHashSet(originalSecondaryMuscleGroups);
        Set<MuscleGroupEntity> commonSet = Sets.intersection(newSet, oldSet);

        List<MuscleGroupEntity> toDelete = Lists.newArrayList(Sets.difference(oldSet, commonSet));
        List<MuscleGroupEntity> toCreate = Lists.newArrayList(Sets.difference(newSet, commonSet));

        exercisesRepository.addSecondaryMuscleGroupsToExercise(exercise, toCreate);
        exercisesRepository.deleteSecondaryMuscleGroupsFromExercise(exercise, toDelete);
    }

    private static <T> Set<T> createNewHashSet(Collection<T> from) {
        return from == null ? Sets.newHashSet() : Sets.newHashSet(from);
    }

    void init(long exerciseId) {
        if (exercise != null) {
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
        dataLoaded.setValue(true);
    }

    private void queryMuscleGroups() {
        liveMuscleGroups = muscleGroupsRepository.getMuscleGroups();
        liveMuscleGroups.observeForever(this::onMuscleGroupsLoaded);
        loadingCountdown++;
    }

    private void onMuscleGroupsLoaded(List<MuscleGroupEntity> muscleGroups) {
        if (muscleGroups != null) {
            liveMuscleGroups.removeObserver(this::onMuscleGroupsLoaded);
            decreaseLoadingCountdown();
        }
    }

    private void queryExercise(long exerciseId) {
        liveExercise = exercisesRepository.getExerciseById(exerciseId);
        liveExercise.observeForever(this::onExerciseLoaded);
        loadingCountdown++;
        querySecondaryMuscleGroups(exerciseId);
    }

    private void onExerciseLoaded(ExerciseEntity exercise) {
        if (exercise != null) {
            this.exercise = exercise;
            liveExercise.removeObserver(this::onExerciseLoaded);
            decreaseLoadingCountdown();
        }
    }

    private void querySecondaryMuscleGroups(long exerciseId) {
        liveSecondaryMuscleGroups = exercisesRepository.getSecondaryMuscleGroupsForExercise(exerciseId);
        liveSecondaryMuscleGroups.observeForever(this::onSecondaryMuscleGroupsLoaded);
        loadingCountdown++;
    }

    private void onSecondaryMuscleGroupsLoaded(List<MuscleGroupEntity> loadedSecondaryMuscleGroups) {
        secondaryMuscleGroups = Lists.newArrayList(loadedSecondaryMuscleGroups);
        liveSecondaryMuscleGroups.removeObserver(this::onSecondaryMuscleGroupsLoaded);
        decreaseLoadingCountdown();
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
