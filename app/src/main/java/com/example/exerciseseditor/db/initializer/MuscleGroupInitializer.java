package com.example.exerciseseditor.db.initializer;

import android.content.Context;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Радик on 01.06.2017.
 */


public class MuscleGroupInitializer extends EntityInitializer<List<MuscleGroupEntity>> {
    private Lazy<MuscleGroupsRepository> muscleGroupsRepository;

    @Inject
    MuscleGroupInitializer(Context context, Lazy<MuscleGroupsRepository> muscleGroupsRepository) {
        super(context);
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    @Override
    boolean needToInitialize() {
        return muscleGroupsRepository.get().isEmpty();
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
    void saveToDatabase(List<MuscleGroupEntity> data) {
        muscleGroupsRepository.get().insertMuscleGroups(data);
    }
}
