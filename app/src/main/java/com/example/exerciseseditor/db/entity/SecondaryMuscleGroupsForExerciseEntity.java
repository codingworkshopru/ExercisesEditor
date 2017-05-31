package com.example.exerciseseditor.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Радик on 22.05.2017.
 */

@Entity(tableName = "muscle_group_exercise_link", primaryKeys = {"exercise_id", "muscle_group_id"},
foreignKeys = {
        @ForeignKey(
                entity = ExerciseEntity.class,
                parentColumns = "id",
                childColumns = "exercise_id",
                onDelete = CASCADE,
                onUpdate = CASCADE
        ),
        @ForeignKey(
                entity = MuscleGroupEntity.class,
                parentColumns = "id",
                childColumns = "muscle_group_id"
        )
})
public class SecondaryMuscleGroupsForExerciseEntity {
    @ColumnInfo(name = "exercise_id")
    private long exerciseId;
    @ColumnInfo(name = "muscle_group_id")
    private long muscleGroupId;

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getMuscleGroupId() {
        return muscleGroupId;
    }

    public void setMuscleGroupId(long muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }
}
