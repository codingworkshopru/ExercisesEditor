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
public interface SecondaryMuscleGroupsForExerciseDao {
    @Query(
            "select * from muscle_group as mg " +
                    "join muscle_group_exercise_link as l on l.muscle_group_id = mg.id " +
                    "where l.exercise_id = :exerciseId"
    )
    LiveData<List<MuscleGroupEntity>> getSecondaryMuscleGroupsForExercise(long exerciseId);

    @Query(
            "select * from exercise as e " +
                    "join muscle_group_exercise_link as l on l.exercise_id = e.id " +
                    "where l.muscle_group_id = :muscleGroupId"
    )
    LiveData<List<ExerciseEntity>> getExercisesForSecondaryMuscleGroup(long muscleGroupId);

    @Insert(onConflict = FAIL)
    void createLink(SecondaryMuscleGroupsForExerciseEntity link);

    @Update(onConflict = FAIL)
    void updateLink(SecondaryMuscleGroupsForExerciseEntity link);

    @Delete
    void deleteLink(SecondaryMuscleGroupsForExerciseEntity link);
}
