package com.example.exerciseseditor.di;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.initializer.DatabaseInitializer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 31.05.2017.
 */

@Module(
        includes = {
                InitializerModule.class,
                ViewModelModule.class,
                ActivityModule.class,
                FragmentModule.class,
                DatabaseModule.class
        }
)
class ApplicationModule {

    @Provides
    @Singleton
    AppDatabase providesDatabase(DatabaseInitializer databaseInitializer) {
        return databaseInitializer.getDatabase();
    }

    @Provides
    @Singleton
    Executor providesExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
