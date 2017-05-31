package com.example.exerciseseditor.model;

/**
 * Created by Радик on 22.05.2017.
 */

public interface Exercise extends WithIdAndName {
    ExerciseDifficulty getDifficulty();
    void setDifficulty(ExerciseDifficulty difficulty);

    long getPrimaryMuscleGroup();
    void setPrimaryMuscleGroup(long primaryMuscleGroup);

    String getDescription();
    void setDescription(String description);

    String getCaution();
    void setCaution(String caution);

    String getAdvices();
    void setAdvices(String advices);

    String getVariations();
    void setVariations(String variations);
}
