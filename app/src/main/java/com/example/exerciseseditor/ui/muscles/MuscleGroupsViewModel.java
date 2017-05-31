package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.repository.MuscleGroupsRepository;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class MuscleGroupsViewModel extends ViewModel {
    private MuscleGroupsRepository muscleGroupsRepository;

    @Inject
    public MuscleGroupsViewModel(MuscleGroupsRepository muscleGroupsRepository) {
        this.muscleGroupsRepository = muscleGroupsRepository;
    }
}
