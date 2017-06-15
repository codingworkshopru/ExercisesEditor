package com.example.exerciseseditor.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.db.entity.SecondaryMuscleGroupsForExerciseEntity;

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

    @Query("select count(*) from exercise")
    int getExercisesCount();

    @Query(
            "select e.* from exercise as e " +
                    "join muscle_group_exercise_link as l on l.exercise_id = e.id " +
                    "where l.muscle_group_id = :muscleGroupId " +
                    "order by e.name"
    )
    LiveData<List<ExerciseEntity>> getExercisesForSecondaryMuscleGroup(long muscleGroupId);

    @Query(
            "select mg.* from muscle_group as mg " +
                    "join muscle_group_exercise_link as l on l.muscle_group_id = mg.id " +
                    "where l.exercise_id = :exerciseId " +
                    "order by mg.name"
    )
    LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long exerciseId);

    @Insert(onConflict = FAIL)
    void insertExercises(List<ExerciseEntity> entities);

    @Insert(onConflict = FAIL)
    void createExercise(ExerciseEntity exercise);

    @Update(onConflict = FAIL)
    void updateExercise(ExerciseEntity exercise);

    @Delete
    void deleteExercise(ExerciseEntity exercise);

    @Insert(onConflict = FAIL)
    void createLinks(List<SecondaryMuscleGroupsForExerciseEntity> links);

    @Delete
    void deleteLinks(List<SecondaryMuscleGroupsForExerciseEntity> link);
}
