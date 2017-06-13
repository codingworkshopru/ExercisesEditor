package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.SecondaryMuscleGroupsForExerciseDao;
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
    private ExerciseDao exerciseDao;
    private SecondaryMuscleGroupsForExerciseDao secondaryMuscleGroupsForExerciseDao;

    @Inject
    public ExercisesRepository(ExerciseDao exerciseDao, SecondaryMuscleGroupsForExerciseDao secondaryMuscleGroupsForExerciseDao) {
        this.exerciseDao = exerciseDao;
        this.secondaryMuscleGroupsForExerciseDao = secondaryMuscleGroupsForExerciseDao;
    }


    public LiveData<List<ExerciseEntity>> getExercisesForMuscleGroup(long id) {
        return exerciseDao.getExercisesForPrimaryMuscleGroup(id);
    }

    public LiveData<ExerciseEntity> getExerciseById(long id) {
        return exerciseDao.getExerciseById(id);
    }

    public void remove(Exercise exercise) {
        ExerciseEntity exerciseEntity = (ExerciseEntity) exercise;
        exerciseDao.deleteExercise(exerciseEntity);
    }

    public void create(ExerciseEntity exercise) {
        exerciseDao.createExercise(exercise);
    }

    public void update(ExerciseEntity exercise) {
        exerciseDao.updateExercise(exercise);
    }

    public LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long id) {
        return secondaryMuscleGroupsForExerciseDao.getSecondaryMuscleGroupsForExercise(id);
    }

    public void addSecondaryMuscleGroupsToExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        secondaryMuscleGroupsForExerciseDao.createLinks(links);
    }

    public void deleteSecondaryMuscleGroupsFromExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        secondaryMuscleGroupsForExerciseDao.deleteLinks(links);
    }

    private static List<SecondaryMuscleGroupsForExerciseEntity> createLinkInstances(Exercise e, List<MuscleGroupEntity> muscleGroups) {
        return Lists.transform(
                muscleGroups,
                (mg) -> new SecondaryMuscleGroupsForExerciseEntity(e.getId(), mg.getId())
        );
    }
}
