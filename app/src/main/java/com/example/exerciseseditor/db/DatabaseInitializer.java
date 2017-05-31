package com.example.exerciseseditor.db;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase.Builder;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;

import com.example.exerciseseditor.BuildConfig;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.executor.TaskExecutor;

/**
 * Created by Радик on 23.05.2017.
 */

public final class DatabaseInitializer {
    @SuppressLint("StaticFieldLeak") // using an application's context
    private static volatile DatabaseInitializer INSTANCE = null;
    public static final String DATABASE_NAME = "ExerciseEditor.db";

    public static DatabaseInitializer getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseInitializer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseInitializer();
                }
            }
        }
        return INSTANCE;
    }

    private Context context;
    private MutableLiveData<AppDatabase> db = new MutableLiveData<>();

    public void initialiseDatabase(@NonNull Context context) {
        this.context = context.getApplicationContext();
        if (BuildConfig.DEBUG) {
            context.deleteDatabase(DATABASE_NAME);
        }
        TaskExecutor.execute(
                this::initializeDatabase,
                this::onDatabaseInitialized
        );
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

    @VisibleForTesting
    public static void populateDatabase(AppDatabase database) {
        MuscleGroupEntity.insertInitialMuscleGroups(database);
    }
}
