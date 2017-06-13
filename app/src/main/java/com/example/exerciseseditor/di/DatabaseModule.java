package com.example.exerciseseditor.di;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.dao.SecondaryMuscleGroupsForExerciseDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Радик on 12.06.2017.
 */

@Module
class DatabaseModule {
    @Provides
    @Singleton
    ExerciseDao providesExerciseDao(AppDatabase db) {
        return db.getExerciseDao();
    }

    @Provides
    @Singleton
    MuscleGroupDao providesMuscleGroupDao(AppDatabase db) {
        return db.getMuscleGroupDao();
    }

    @Provides
    @Singleton
    SecondaryMuscleGroupsForExerciseDao getSecondaryMuscleGroupsForExerciseDao(AppDatabase db) {
        return db.getSecondaryMuscleGroupsForExerciseDao();
    }
}
