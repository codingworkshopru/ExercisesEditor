package com.example.exerciseseditor.repository;

import android.arch.lifecycle.LiveData;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;

import java.util.List;

/**
 * Created by Радик on 24.05.2017.
 */

public final class MuscleGroupsRepository extends BaseRepository {
    private LiveData<List<MuscleGroupEntity>> muscleGroups = queryWhenDbInitialized(this::queryAllMuscleGroups);

    private LiveData<List<MuscleGroupEntity>> queryAllMuscleGroups(AppDatabase db) {
        return db.getMuscleGroupDao().getAllMuscleGroups();
    }

    public LiveData<List<MuscleGroupEntity>> getMuscleGroups() {
        return muscleGroups;
    }
}
