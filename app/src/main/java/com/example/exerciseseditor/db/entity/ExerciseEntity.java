package com.example.exerciseseditor.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.exerciseseditor.db.converters.ExerciseDifficultyConverter;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.model.ExerciseDifficulty;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Радик on 22.05.2017.
 */

@Entity(tableName = "exercise", indices = {@Index(value = "name", unique = true)},
foreignKeys = @ForeignKey(
        entity = MuscleGroupEntity.class,
        parentColumns = "id",
        childColumns = "primary_muscle_group_id")
)
@TypeConverters({ExerciseDifficultyConverter.class})
public class ExerciseEntity implements Exercise {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private ExerciseDifficulty difficulty;
    @ColumnInfo(name = "primary_muscle_group_id", index = true)
    private long primaryMuscleGroup;
    @SerializedName("isWithWeight")
    private boolean withWeight;
    private String youTubeVideo;
    private String steps;
    private String advices;
    private String variations;
    private String caution;

    // fields for creating muscle groups <-> exercise links by DB initializer (with Gson)
    @Ignore
    public String primaryMuscle;
    @Ignore
    public List<String> secondaryMuscles;

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
    public ExerciseDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void setDifficulty(ExerciseDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public long getPrimaryMuscleGroup() {
        return primaryMuscleGroup;
    }

    @Override
    public void setPrimaryMuscleGroup(long primaryMuscleGroup) {
        this.primaryMuscleGroup = primaryMuscleGroup;
    }

    @Override
    public boolean isWithWeight() {
        return withWeight;
    }

    @Override
    public void setWithWeight(boolean withWeight) {
        this.withWeight = withWeight;
    }

    @Override
    public String getYouTubeVideo() {
        return youTubeVideo;
    }

    public void setYouTubeVideo(String youTubeVideo) {
        this.youTubeVideo = youTubeVideo;
    }

    @Override
    public String getSteps() {
        return steps;
    }

    @Override
    public void setSteps(String steps) {
        this.steps = steps;
    }

    @Override
    public String getAdvices() {
        return advices;
    }

    @Override
    public void setAdvices(String advices) {
        this.advices = advices;
    }

    @Override
    public String getVariations() {
        return variations;
    }

    @Override
    public void setVariations(String variations) {
        this.variations = variations;
    }

    @Override
    public String getCaution() {
        return caution;
    }

    @Override
    public void setCaution(String caution) {
        this.caution = caution;
    }
}
