package com.example.exerciseseditor.repository;

import android.support.annotation.WorkerThread;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.model.MuscleGroup;
import com.example.exerciseseditor.model.WithIdAndName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Радик on 15.06.2017.
 */

@WorkerThread
final class SecondaryMuscleGroupsHelper {
    private ExerciseDao exerciseDao;
    private MuscleGroupDao muscleGroupDao;

    SecondaryMuscleGroupsHelper(ExerciseDao exerciseDao, MuscleGroupDao muscleGroupDao) {
        this.exerciseDao = exerciseDao;
        this.muscleGroupDao = muscleGroupDao;
    }

    void updateLinks(ExerciseEntity exercise, List<MuscleGroupEntity> secondaryMuscleGroups) {
        long exerciseId = exercise.getId();
        if (exerciseId == 0)
            throw new RuntimeException("Exercise doesn't exist: " + exercise.getName());

        List<SecondaryMuscleGroupsForExerciseEntity> oldLinksList =
                exerciseDao.getSecondaryMuscleGroupsForExerciseSync(exerciseId);
        List<SecondaryMuscleGroupsForExerciseEntity> newLinksList =
                createLinkInstances(exercise, secondaryMuscleGroups);

        Set<SecondaryMuscleGroupsForExerciseEntity> oldLinks = Sets.newHashSet(oldLinksList);
        Set<SecondaryMuscleGroupsForExerciseEntity> newLinks = Sets.newHashSet(newLinksList);

        exerciseDao.createLinks(Lists.newArrayList(Sets.difference(newLinks, oldLinks)));
        exerciseDao.deleteLinks(Lists.newArrayList(Sets.difference(oldLinks, newLinks)));
    }

    void createExercises(List<ExerciseEntity> exercises) {
        Map<String, Long> nameIdMuscleGroupsMap = createNameIdMap(muscleGroupDao.getAllMuscleGroupsSync());
        for (ExerciseEntity exercise : exercises)
            exercise.setPrimaryMuscleGroup(nameIdMuscleGroupsMap.get(exercise.primaryMuscle));
        exerciseDao.insertExercises(exercises);

        Map<String, Long> nameIdExercisesMap = createNameIdMap(exerciseDao.getAllExercisesSync());

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

    private static Map<String, Long> createNameIdMap(List<? extends WithIdAndName> entities) {
        Map<String, Long> result = Maps.newHashMapWithExpectedSize(entities.size());
        for (WithIdAndName entity : entities)
            result.put(entity.getName(), entity.getId());
        return result;
    }

    private static List<SecondaryMuscleGroupsForExerciseEntity> createLinkInstances(Exercise e, List<? extends MuscleGroup> muscleGroups) {
        return Lists.transform(
                muscleGroups,
                (mg) -> new SecondaryMuscleGroupsForExerciseEntity(e.getId(), mg.getId())
        );
    }
}
