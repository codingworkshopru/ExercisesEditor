package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.QueryExecutor;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 24.05.2017.
 */

@Singleton
public final class MuscleGroupsRepository {
    private QueryExecutor executor;

    @Inject
    public MuscleGroupsRepository(QueryExecutor executor) {
        this.executor = executor;
    }

    public LiveData<List<MuscleGroupEntity>> getMuscleGroups() {
        return executor.execute((db) -> db.getMuscleGroupDao().getAllMuscleGroups());
    }
}
