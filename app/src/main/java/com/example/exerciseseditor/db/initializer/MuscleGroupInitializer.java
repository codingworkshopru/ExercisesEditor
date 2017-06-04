package com.example.exerciseseditor.db.initializer;

import android.content.Context;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Радик on 01.06.2017.
 */

class MuscleGroupInitializer extends EntityInitializer<List<MuscleGroupEntity>> {
    private MuscleGroupDao muscleGroupDao;

    MuscleGroupInitializer(AppDatabase database, Context context) {
        super(context);
        muscleGroupDao = database.getMuscleGroupDao();
    }

    @Override
    boolean checkIsInitialized() {
        return muscleGroupDao.getMuscleGroupsCount() != 0;
    }

    @Override
    Type getType() {
        return new TypeToken<List<MuscleGroupEntity>>(){}.getType();
    }

    @Override
    int getJsonResourceId() {
        return R.raw.muscle_groups;
    }

    @Override
    Gson buildGson() {
        return new Gson();
    }

    @Override
    void saveToDatabase(List<MuscleGroupEntity> data) {
        muscleGroupDao.insertMuscleGroups(data);
    }
}
