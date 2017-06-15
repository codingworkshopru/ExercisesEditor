package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.WorkerThread;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.model.MuscleGroup;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
public final class ExercisesRepository {
    private ExerciseDao exerciseDao;
    private SecondaryMuscleGroupsHelper helper;

    @Inject
    ExercisesRepository(ExerciseDao exerciseDao, MuscleGroupDao muscleGroupDao) {
        this.exerciseDao = exerciseDao;
        helper = new SecondaryMuscleGroupsHelper(exerciseDao, muscleGroupDao);
    }

    public boolean isEmpty() {
        return exerciseDao.getExercisesCount() == 0;
    }

    public LiveData<List<ExerciseEntity>> getExercisesForMuscleGroup(long id) {
        return exerciseDao.getExercisesForPrimaryMuscleGroup(id);
    }

    public LiveData<ExerciseEntity> getExerciseById(long id) {
        return exerciseDao.getExerciseById(id);
    }

    public void remove(Exercise exercise) {
        exerciseDao.deleteExercise((ExerciseEntity) exercise);
    }

    public void create(ExerciseEntity exercise) {
        exerciseDao.createExercise(exercise);
    }

    public void createWithSecondaryMuscleGroups(List<ExerciseEntity> exercises) {
        helper.createExercises(exercises);
    }

    public void update(ExerciseEntity exercise) {
        exerciseDao.updateExercise(exercise);
    }

    public LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long id) {
        return exerciseDao.getSecondaryMuscleGroupsForExercise(id);
    }

    public void addSecondaryMuscleGroupsToExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        exerciseDao.createLinks(links);
    }

    public void deleteSecondaryMuscleGroupsFromExercise(Exercise exercise, List<MuscleGroupEntity> muscleGroups) {
        List<SecondaryMuscleGroupsForExerciseEntity> links = createLinkInstances(exercise, muscleGroups);
        exerciseDao.deleteLinks(links);
    }

    private static List<SecondaryMuscleGroupsForExerciseEntity> createLinkInstances(Exercise e, List<? extends MuscleGroup> muscleGroups) {
        return Lists.transform(
                muscleGroups,
                (mg) -> new SecondaryMuscleGroupsForExerciseEntity(e.getId(), mg.getId())
        );
    }

    private static final class SecondaryMuscleGroupsHelper {
        private ExerciseDao exerciseDao;
        private MuscleGroupDao muscleGroupDao;

        SecondaryMuscleGroupsHelper(ExerciseDao exerciseDao, MuscleGroupDao muscleGroupDao) {
            this.exerciseDao = exerciseDao;
            this.muscleGroupDao = muscleGroupDao;
        }

        @WorkerThread
        void createExercises(List<ExerciseEntity> exercises) {
            List<MuscleGroupEntity> muscleGroups = muscleGroupDao.getAllMuscleGroupsSync();
            Map<String, Long> nameIdMuscleGroupsMap = Maps.newHashMapWithExpectedSize(muscleGroups.size());
            for (MuscleGroup muscleGroup : muscleGroups) {
                nameIdMuscleGroupsMap.put(muscleGroup.getName(), muscleGroup.getId());
            }
            for (ExerciseEntity exercise : exercises) {
                exercise.setPrimaryMuscleGroup(nameIdMuscleGroupsMap.get(exercise.primaryMuscle));
            }
            exerciseDao.insertExercises(exercises);
            List<ExerciseEntity> exercisesWithIds = exerciseDao.getAllExercisesSync();
            Map<String, Long> nameIdExercisesMap = Maps.newHashMapWithExpectedSize(exercisesWithIds.size());
            for (Exercise exercise : exercisesWithIds) {
                nameIdExercisesMap.put(exercise.getName(), exercise.getId());
            }

            List<SecondaryMuscleGroupsForExerciseEntity> links = new LinkedList<>();
            for (ExerciseEntity exercise : exercises) {
                if (exercise.secondaryMuscles == null) continue;
                links.addAll(
                        Lists.transform(exercise.secondaryMuscles, (name) ->
                                new SecondaryMuscleGroupsForExerciseEntity(
                                        nameIdExercisesMap.get(exercise.getName()),
                                        nameIdMuscleGroupsMap.get(name)
                                )
                        )
                );
            }

            exerciseDao.createLinks(links);
        }
    }
}
