package com.example.exerciseseditor.di;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.initializer.DatabaseInitializer;
import com.example.exerciseseditor.viewmodel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 31.05.2017.
 */

@Module(subcomponents = ViewModelSubComponent.class, includes = ActivityModule.class)
class ApplicationModule {
    @Provides @LiveDatabase
    LiveData<AppDatabase> providesLiveDatabase(DatabaseInitializer databaseInitializer) {
        return databaseInitializer.getDatabase();
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(ViewModelSubComponent.Builder builder) {
        return new ViewModelFactory(builder.build());
    }
}
