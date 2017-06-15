package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.AppInitializer;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;
import com.example.exerciseseditor.util.LiveDataUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * Created by Радик on 31.05.2017.
 */

public class MusclesViewModel extends ViewModel {
    @Inject Lazy<MuscleGroupsRepository> muscleGroupsRepository;
    private LiveData<List<MuscleGroupEntity>> muscleGroups;

    @Inject
    MusclesViewModel(AppInitializer appInitializer) {
        muscleGroups = Transformations.switchMap(
                appInitializer.getInitialized(),
                (initialized) -> initialized
                        ? muscleGroupsRepository.get().getMuscleGroups()
                        : LiveDataUtil.getAbsent()

        );
    }

    LiveData<List<MuscleGroupEntity>> getAllMuscleGroups() {
        return muscleGroups;
    }
}
