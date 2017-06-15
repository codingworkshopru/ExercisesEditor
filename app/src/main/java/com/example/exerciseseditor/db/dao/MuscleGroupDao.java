package com.example.exerciseseditor.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.exerciseseditor.db.entity.MuscleGroupEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.FAIL;

/**
 * Created by Радик on 22.05.2017.
 */

@Dao
public interface MuscleGroupDao {
    @Query("select * from muscle_group order by name")
    LiveData<List<MuscleGroupEntity>> getAllMuscleGroups();

    @Query("select * from muscle_group order by name")
    List<MuscleGroupEntity> getAllMuscleGroupsSync();

    @Query("select count(*) from muscle_group")
    int getMuscleGroupsCount();

    @Query("select * from muscle_group where id = :id")
    LiveData<MuscleGroupEntity> getMuscleGroupById(long id);

    @Insert(onConflict = FAIL)
    void insertMuscleGroups(List<MuscleGroupEntity> muscleGroups);
}
