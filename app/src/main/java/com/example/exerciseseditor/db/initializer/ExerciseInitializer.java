package com.example.exerciseseditor.db.initializer;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.converters.ExerciseDifficultyConverter;
import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.model.ExerciseDifficulty;
import com.example.exerciseseditor.model.MuscleGroup;
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Радик on 01.06.2017.
 */

public class ExerciseInitializer extends EntityInitializer<List<ExerciseEntity>> {
    private AppDatabase database;
    private ExerciseDao exerciseDao;
    private Map<String, Long> muscleGroupNameIdMap;

    ExerciseInitializer(AppDatabase database, Context context) {
        super(context);
        this.database = database;
        exerciseDao = database.getExerciseDao();
    }

    @Override
    public void preInitialize() {
        createMuscleGroupNameIdMap();
    }

    @Override
    boolean checkIsInitialized() {
        return exerciseDao.getExercisesCount() != 0;
    }

    private void createMuscleGroupNameIdMap() {
        List<MuscleGroupEntity> muscleGroups = database.getMuscleGroupDao().getAllMuscleGroupsSync();
        muscleGroupNameIdMap = createNameIdMap(muscleGroups, MuscleGroup::getName, MuscleGroup::getId);
    }

    @SuppressWarnings("Guava")
    private <T> Map<String, Long> createNameIdMap(List<T> items, Function<T, String> name, Function<T, Long> id) {
        Map<String, Long> result = new HashMap<>(items.size());
        for (T item : items) {
            result.put(name.apply(item), id.apply(item));
        }
        return result;
    }

    @Override
    Type getType() {
        return new TypeToken<List<ExerciseEntity>>(){}.getType();
    }

    @Override
    int getJsonResourceId() {
        return R.raw.exercises;
    }

    @Override
    Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(
                        ExerciseDifficulty.class,
                        (JsonDeserializer<ExerciseDifficulty>) (json, a, b) ->
                                ExerciseDifficultyConverter.fromIntToEnum(json.getAsInt())
                )
                .create();
    }

    @Override
    void saveToDatabase(List<ExerciseEntity> exercises) {
        saveExercises(exercises);
        saveSecondaryMuscleGroupsLinks(exercises);
    }

    private void saveExercises(List<ExerciseEntity> exercises) {
        for (ExerciseEntity exercise : exercises) {
            exercise.setPrimaryMuscleGroup(muscleGroupNameIdMap.get(exercise.primaryMuscle));
        }

        exerciseDao.insertExercises(exercises);
    }

    private void saveSecondaryMuscleGroupsLinks(List<ExerciseEntity> exercises) {
        // we need exercises with db ids
        List<ExerciseEntity> exercisesWithId = exerciseDao.getAllExercisesSync();

        Map<String, Long> exerciseNameIdMap = createNameIdMap(exercisesWithId, Exercise::getName, Exercise::getId);

        List<SecondaryMuscleGroupsForExerciseEntity> links = new LinkedList<>();
        for (ExerciseEntity exercise : exercises) {
            List<SecondaryMuscleGroupsForExerciseEntity> exercisesLinks = getLinksForExercise(exercise, exerciseNameIdMap);
            if (exercisesLinks == null) continue;
            links.addAll(exercisesLinks);
        }

        database.getSecondaryMuscleGroupsForExerciseDao().createLinks(links);
    }

    private @Nullable List<SecondaryMuscleGroupsForExerciseEntity> getLinksForExercise(ExerciseEntity exercise, Map<String, Long> exerciseNameIdMap) {
        if (exercise.secondaryMuscles == null)
            return null;

        List<SecondaryMuscleGroupsForExerciseEntity> result = new LinkedList<>();
        for (String muscleGroupName : exercise.secondaryMuscles) {
            long exerciseId = exerciseNameIdMap.get(exercise.getName());
            long muscleGroupId = muscleGroupNameIdMap.get(muscleGroupName);
            result.add(new SecondaryMuscleGroupsForExerciseEntity(exerciseId, muscleGroupId));
        }

        return result;
    }
}
