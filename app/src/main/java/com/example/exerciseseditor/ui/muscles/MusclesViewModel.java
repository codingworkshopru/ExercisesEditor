package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Радик on 31.05.2017.
 */

public class MusclesViewModel extends ViewModel {
    private MuscleGroupsRepository muscleGroupsRepository;

    MusclesViewModel(MuscleGroupsRepository muscleGroupsRepository) {
        this.muscleGroupsRepository = muscleGroupsRepository;
    }

    public LiveData<List<MuscleGroupEntity>> getAllMuscleGroups() {
        return muscleGroupsRepository.getMuscleGroups();
    }

    public static final class MusclesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private MuscleGroupsRepository repository;

        @Inject
        public MusclesViewModelFactory(MuscleGroupsRepository repository) {
            this.repository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            // noinspection unchecked
            return (T) new MusclesViewModel(repository);
        }
    }
}
