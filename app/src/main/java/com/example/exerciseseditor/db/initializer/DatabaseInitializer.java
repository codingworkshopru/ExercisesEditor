package com.example.exerciseseditor.db.initializer;

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
import com.example.exerciseseditor.db.AppDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 23.05.2017.
 */

@Singleton
public final class DatabaseInitializer {
    private static final String DATABASE_NAME = "ExerciseEditor.db";

    private Context context;
    private MutableLiveData<AppDatabase> db = new MutableLiveData<>();

    @Inject
    public DatabaseInitializer() {}

    public @NonNull LiveData<AppDatabase> getDatabase() {
        return db;
    }

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

    @WorkerThread
    private AppDatabase initializeDatabase() {
        AppDatabase database = buildDatabase();
        populateDatabase(database);
        return database;
    }

    private AppDatabase buildDatabase() {
        Builder<AppDatabase> dbBuilder = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME);
        return dbBuilder.build();
    }

    @VisibleForTesting
    public void populateDatabase(AppDatabase database) {
        MuscleGroupInitializer.initializeIfNeeded(database, context);
        ExerciseInitializer.initializeIfNeeded(database, context);
    }

    @MainThread
    private void onDatabaseInitialized(AppDatabase database) {
        db.setValue(database);
    }
}
