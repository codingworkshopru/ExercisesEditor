package com.example.exerciseseditor.di;

import com.example.exerciseseditor.db.initializer.DatabaseInitializer;
import com.example.exerciseseditor.db.initializer.ExerciseInitializer;
import com.example.exerciseseditor.db.initializer.Initializer;
import com.example.exerciseseditor.db.initializer.MuscleGroupInitializer;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntKey;
import dagger.multibindings.IntoMap;

/**
 * Created by Радик on 12.06.2017.
 */

@Module
abstract class InitializerModule {
    @Binds
    @IntoMap
    @IntKey(0)
    abstract Initializer bindsDatabaseInitializer(DatabaseInitializer databaseInitializer);

    @Binds
    @IntoMap
    @IntKey(1)
    abstract Initializer bindsMuscleGroupInitializer(MuscleGroupInitializer muscleGroupInitializer);

    @Binds
    @IntoMap
    @IntKey(2)
    abstract Initializer bindsExerciseInitializer(ExerciseInitializer exerciseInitializer);
}
