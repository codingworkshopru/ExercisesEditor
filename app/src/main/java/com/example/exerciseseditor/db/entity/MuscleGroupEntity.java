package com.example.exerciseseditor.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.dao.MuscleGroupDao;
import com.example.exerciseseditor.model.MuscleGroup;

import java.util.ArrayList;
import java.util.List;

import static android.support.annotation.VisibleForTesting.PRIVATE;

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

    @WorkerThread
    public static void insertInitialMuscleGroups(AppDatabase db) {
        MuscleGroupDao dao = db.getMuscleGroupDao();
        List<MuscleGroupEntity> muscleGroupEntities = new ArrayList<>(MUSCLE_GROUPS.length);
        for (String storedName : MUSCLE_GROUPS)
            muscleGroupEntities.add(new MuscleGroupEntity(storedName));

        dao.insertAllMuscleGroups(muscleGroupEntities);
    }

    public static final String[] MUSCLE_GROUPS = {
            "Шея", "Трапеции", "Пресс", "Бедра", "Бицепс", "Трицепс", "Предплечья", "Спина",
            "Широчайшие", "Ягодицы", "Икры", "Квадрицепс", "Плечи", "Грудь"
    };
}
