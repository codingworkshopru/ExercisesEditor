package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class MusclesViewModel extends ViewModel {
    private MuscleGroupsRepository muscleGroupsRepository;
    private LiveData<List<MuscleGroupEntity>> muscleGroups;

    @Inject
    MusclesViewModel(MuscleGroupsRepository muscleGroupsRepository) {
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    LiveData<List<MuscleGroupEntity>> getAllMuscleGroups() {
        if (muscleGroups == null) {
            muscleGroups = muscleGroupsRepository.getMuscleGroups();
        }
        return muscleGroups;
    }
}
