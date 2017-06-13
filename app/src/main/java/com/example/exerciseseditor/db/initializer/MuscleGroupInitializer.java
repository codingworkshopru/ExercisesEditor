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

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Радик on 01.06.2017.
 */


public class MuscleGroupInitializer extends EntityInitializer<List<MuscleGroupEntity>> {
    @Inject Lazy<AppDatabase> database;
    private MuscleGroupDao muscleGroupDao;

    @Inject
    MuscleGroupInitializer(Context context) {
        super(context);
    }

    @Override
    void preInitialize() {
        muscleGroupDao = database.get().getMuscleGroupDao();
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
