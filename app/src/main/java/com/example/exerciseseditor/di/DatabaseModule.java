package com.example.exerciseseditor.di;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.initializer.DatabaseInitializer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 31.05.2017.
 */

@Module
class DatabaseModule {
    @Provides @LiveDatabase
    public LiveData<AppDatabase> providesLiveDatabase(DatabaseInitializer databaseInitializer) {
        return databaseInitializer.getDatabase();
    }
}
