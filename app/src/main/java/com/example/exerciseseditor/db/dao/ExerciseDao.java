package com.example.exerciseseditor.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.exerciseseditor.db.entity.ExerciseEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;

/**
 * Created by Радик on 22.05.2017.
 */

@Dao
public interface ExerciseDao {
    @Query("select * from exercise")
    LiveData<List<ExerciseEntity>> getAllExercises();

    @Query("select * from exercise")
    List<ExerciseEntity> getAllExercisesSync();

    @Query("select * from exercise where primary_muscle_group_id = :id")
    LiveData<List<ExerciseEntity>> getExercisesForPrimaryMuscleGroup(long id);

    @Query("select * from exercise where id = :id")
    LiveData<ExerciseEntity> getExerciseById(long id);

    @Query("select * from exercise where id = :id")
    ExerciseEntity getExerciseByIdSync(long id);

    @Insert(onConflict = FAIL)
    void insertExercises(List<ExerciseEntity> entities);

    @Insert(onConflict = FAIL)
    void createExercise(ExerciseEntity exercise);

    @Update(onConflict = FAIL)
    void updateExercise(ExerciseEntity exercise);

    @Delete
    void deleteExercise(ExerciseEntity exercise);
}
