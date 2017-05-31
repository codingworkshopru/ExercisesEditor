package com.example.exerciseseditor.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.entity.ExerciseEntity;

import java.util.List;

/**
 * Created by Радик on 23.05.2017.
 */

public class ExercisesListViewModel extends ViewModel {
    private AppDatabase db;
    private LiveData<List<ExerciseEntity>> exercises;

    public ExercisesListViewModel() {

    }

    public LiveData<List<ExerciseEntity>> getExercises() {
        return db.getExerciseDao().getAllExercises();
    }
}
