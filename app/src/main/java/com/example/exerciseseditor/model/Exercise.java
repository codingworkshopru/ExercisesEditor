package com.example.exerciseseditor.model;

/**
 * Created by Радик on 22.05.2017.
 */

public interface Exercise extends WithIdAndName {
    ExerciseDifficulty getDifficulty();
    void setDifficulty(ExerciseDifficulty difficulty);

    long getPrimaryMuscleGroup();
    void setPrimaryMuscleGroup(long primaryMuscleGroup);

    boolean isWithWeight();
    void setWithWeight(boolean withWeight);

    String getYouTubeVideo();
    void setYouTubeVideo(String youTubeVideo);

    String getSteps();
    void setSteps(String steps);

    String getCaution();
    void setCaution(String caution);

    String getAdvices();
    void setAdvices(String advices);

    String getVariations();
    void setVariations(String variations);
}
