package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.QueryExecutor;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.google.common.collect.Lists;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
public final class ExercisesRepository {
    private QueryExecutor executor;

    @Inject
    ExercisesRepository(QueryExecutor executor) {
        this.executor = executor;
    }

    public LiveData<List<ExerciseEntity>> getExercisesForMuscleGroup(long id) {
        return executor.read((db) -> db.getExerciseDao().getExercisesForPrimaryMuscleGroup(id));
    }

    public LiveData<ExerciseEntity> getExerciseById(long id) {
        return executor.read((db) -> db.getExerciseDao().getExerciseById(id));
    }

    public void remove(Exercise exercise) {
        ExerciseEntity exerciseEntity = (ExerciseEntity) exercise;
        executor.execute((db) -> db.getExerciseDao().deleteExercise(exerciseEntity));
    }

    public void create(ExerciseEntity exercise) {
        executor.execute((db) -> db.getExerciseDao().createExercise(exercise));
    }

    public void update(ExerciseEntity exercise) {
        executor.execute((db) -> db.getExerciseDao().updateExercise(exercise));
    }

    public LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long id) {
        return executor.read((db) -> db.getSecondaryMuscleGroupsForExerciseDao().getSecondaryMuscleGroupsForExercise(id));
    }

    public void addSecondaryMuscleGroupsToExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        executor.execute((db) -> db.getSecondaryMuscleGroupsForExerciseDao().createLinks(links));
    }

    public void deleteSecondaryMuscleGroupsFromExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        executor.execute((db) -> db.getSecondaryMuscleGroupsForExerciseDao().deleteLinks(links));
    }

    private static List<SecondaryMuscleGroupsForExerciseEntity> createLinkInstances(Exercise e, List<MuscleGroupEntity> muscleGroups) {
        return Lists.transform(
                muscleGroups,
                (mg) -> new SecondaryMuscleGroupsForExerciseEntity(e.getId(), mg.getId())
        );
    }
}
