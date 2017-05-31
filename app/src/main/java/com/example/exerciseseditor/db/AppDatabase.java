package com.example.exerciseseditor.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;

/**
 * Created by Радик on 22.05.2017.
 */

@Database(version = 1, exportSchema = false, entities = {
        MuscleGroupEntity.class,
        ExerciseEntity.class,
        SecondaryMuscleGroupsForExerciseEntity.class
})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MuscleGroupDao getMuscleGroupDao();
    public abstract ExerciseDao getExerciseDao();
}
