package com.example.exerciseseditor.di;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.initializer.DatabaseInitializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 31.05.2017.
 */

@Module(
        includes = {
                ViewModelModule.class,
                ActivityModule.class,
                FragmentModule.class,
                InitializerModule.class,
                DatabaseModule.class
        }
)
class ApplicationModule {

    @Provides @Singleton
    AppDatabase providesDatabase(DatabaseInitializer databaseInitializer) {
        return databaseInitializer.getDatabase();
    }
}
