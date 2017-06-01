package com.example.exerciseseditor.db;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;

import com.example.exerciseseditor.BuildConfig;
import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.converters.ExerciseDifficultyConverter;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;
import com.example.exerciseseditor.model.ExerciseDifficulty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 23.05.2017.
 */

@Singleton
public final class DatabaseInitializer {
    public static final String DATABASE_NAME = "ExerciseEditor.db";

    private Context context;
    private MutableLiveData<AppDatabase> db = new MutableLiveData<>();

    @Inject
    public DatabaseInitializer() {}

    public void initialiseDatabase(@NonNull Context context) {
        this.context = context.getApplicationContext();
        if (BuildConfig.DEBUG) {
            context.deleteDatabase(DATABASE_NAME);
        }

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Void, Void, AppDatabase> databaseInitializeTask = new AsyncTask<Void, Void, AppDatabase>() {
            protected AppDatabase doInBackground(Void... voids) {
                return initializeDatabase();
            }

            protected void onPostExecute(AppDatabase database) {
                onDatabaseInitialized(database);
            }
        };

        databaseInitializeTask.execute();
    }

    public @NonNull LiveData<AppDatabase> getDatabase() {
        return db;
    }

    @WorkerThread
    private AppDatabase initializeDatabase() {
        AppDatabase database = buildDatabase();
        populateDatabase(database);
        return database;
    }

    @MainThread
    private void onDatabaseInitialized(AppDatabase database) {
        db.setValue(database);
    }

    private AppDatabase buildDatabase() {
        Builder<AppDatabase> dbBuilder = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME);
        return dbBuilder.build();
    }

    @WorkerThread
    @VisibleForTesting
    void populateDatabase(AppDatabase database) {
        insertInitialMuscleGroups(database);
        insertInitialExercises(database);
    }

    // TODO refactor this shit!
    private void insertInitialMuscleGroups(AppDatabase db) {
        Reader r = new InputStreamReader(context.getResources().openRawResource(R.raw.muscle_groups));
        Gson gson = new Gson();

        List<MuscleGroupEntity> muscleGroupEntities = gson.fromJson(r, new TypeToken<List<MuscleGroupEntity>>(){}.getType());
        MuscleGroupDao dao = db.getMuscleGroupDao();
        dao.insertMuscleGroups(muscleGroupEntities);
    }

    // TODO especially this
    private void insertInitialExercises(AppDatabase db) {
        List<MuscleGroupEntity> muscleGroups = db.getMuscleGroupDao().getAllMuscleGroupsSync();

        Map<String, Long> muscleGroupsMap = new HashMap<>(muscleGroups.size());
        for (MuscleGroupEntity muscleGroup : muscleGroups) {
            muscleGroupsMap.put(muscleGroup.getName(), muscleGroup.getId());
        }

        Reader r = new InputStreamReader(context.getResources().openRawResource(R.raw.exercises));
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ExerciseDifficulty.class, new JsonDeserializer<ExerciseDifficulty>() {
            @Override
            public ExerciseDifficulty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return ExerciseDifficultyConverter.fromIntToEnum(json.getAsInt());
            }
        });

        Type type = new TypeToken<List<ExerciseEntity>>(){}.getType();
        List<ExerciseEntity> exercises = builder.create().fromJson(r, type);

        for (ExerciseEntity exercise : exercises) {
            exercise.setPrimaryMuscleGroup(muscleGroupsMap.get(exercise.primaryMuscle));
        }

        db.getExerciseDao().insertExercises(exercises);

        List<ExerciseEntity> exercisesFromDb = db.getExerciseDao().getAllExercisesSync();
        for (int i = 0; i < exercises.size(); i++) {
            exercises.get(i).setId(exercisesFromDb.get(i).getId());
        }

        List<SecondaryMuscleGroupsForExerciseEntity> links = new LinkedList<>();
        for (ExerciseEntity exercise : exercises) {
            if (exercise.secondaryMuscles == null)
                continue;
            for (String muscleGroupName : exercise.secondaryMuscles) {
                long muscleGroupId = muscleGroupsMap.get(muscleGroupName);
                SecondaryMuscleGroupsForExerciseEntity link = new SecondaryMuscleGroupsForExerciseEntity();
                link.setExerciseId(exercise.getId());
                link.setMuscleGroupId(muscleGroupId);
                links.add(link);
            }
        }

        db.getSecondaryMuscleGroupsForExerciseDao().createLinks(links);
    }
}
