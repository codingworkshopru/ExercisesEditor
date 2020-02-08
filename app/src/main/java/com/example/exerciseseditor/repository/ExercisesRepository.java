package com.example.exerciseseditor.repository;

import androidx.lifecycle.LiveData;
import androidx.annotation.WorkerThread;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.model.Exercise;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
public final class ExercisesRepository {
    private final ExerciseDao exerciseDao;
    private final SecondaryMuscleGroupsHelper helper;
    private final Executor executor;

    @Inject
    ExercisesRepository(ExerciseDao exerciseDao, MuscleGroupDao muscleGroupDao, Executor executor) {
        this.exerciseDao = exerciseDao;
        this.executor = executor;
        helper = new SecondaryMuscleGroupsHelper(exerciseDao, muscleGroupDao);
    }

    // create

    public void create(ExerciseEntity exercise, List<MuscleGroupEntity> secondaryMuscleGroups) {
        executor.execute(() -> {
            exerciseDao.createExercise(exercise);
            ExerciseEntity exerciseWithId = exerciseDao.getExerciseByName(exercise.getName());
            helper.updateLinks(exerciseWithId, secondaryMuscleGroups);
        });
    }

    @WorkerThread
    // called from initializer
    public void createWithSecondaryMuscleGroups(List<ExerciseEntity> exercises) {
        helper.createExercises(exercises);
    }

    // read

    public boolean isEmpty() {
        return exerciseDao.getExercisesCount() == 0;
    }

    public LiveData<List<ExerciseEntity>> getExercisesForMuscleGroup(long id) {
        return exerciseDao.getExercisesForPrimaryMuscleGroup(id);
    }

    public LiveData<ExerciseEntity> getExerciseById(long id) {
        return exerciseDao.getExerciseById(id);
    }

    public LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long id) {
        return exerciseDao.getSecondaryMuscleGroupsForExercise(id);
    }

    // update

    public void update(ExerciseEntity exercise, List<MuscleGroupEntity> secondaryMuscleGroups) {
        executor.execute(() -> {
            exerciseDao.updateExercise(exercise);
            helper.updateLinks(exercise, secondaryMuscleGroups);
        });
    }

    // delete

    public void delete(Exercise exercise) {
        executor.execute(() -> exerciseDao.deleteExercise((ExerciseEntity) exercise));
    }
}
