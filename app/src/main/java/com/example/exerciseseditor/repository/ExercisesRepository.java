package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.QueryExecutor;
import com.example.exerciseseditor.db.entity.ExerciseEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
public final class ExercisesRepository {
    private QueryExecutor executor;

    @Inject
    public ExercisesRepository(QueryExecutor executor) {
        this.executor = executor;
    }

    public LiveData<List<ExerciseEntity>> getAllExercises() {
        return executor.execute((db) -> db.getExerciseDao().getAllExercises());
    }
}
