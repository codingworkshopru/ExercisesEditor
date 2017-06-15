package com.example.exerciseseditor.db.initializer;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase.Builder;
import android.content.Context;

import com.example.exerciseseditor.BuildConfig;
import com.example.exerciseseditor.db.AppDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 23.05.2017.
 */

@Singleton
public final class DatabaseInitializer implements Initializer {
    private static final String DATABASE_NAME = "ExerciseEditor.db";

    private AppDatabase db;
    private Context context;

    @Inject
    DatabaseInitializer(Context context) {
        this.context = context;
    }


    public AppDatabase getDatabase() {
        return db;
    }

    @Override
    public void initialize() {
        if (BuildConfig.DEBUG) {
            this.context.deleteDatabase(DATABASE_NAME);
        }

        Builder<AppDatabase> dbBuilder = Room.databaseBuilder(this.context, AppDatabase.class, DATABASE_NAME);
        db = dbBuilder.build();
    }
}
