package com.example.exerciseseditor.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.example.exerciseseditor.model.MuscleGroup;
import com.google.common.base.Objects;

/**
 * Created by Радик on 22.05.2017.
 */

@Entity(tableName = "muscle_group", indices = @Index(value = "name", unique = true))
public class MuscleGroupEntity implements MuscleGroup {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    public MuscleGroupEntity(String name) {
        this.name = name;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof MuscleGroupEntity))
            return  false;

        MuscleGroupEntity other = (MuscleGroupEntity) obj;

        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}
