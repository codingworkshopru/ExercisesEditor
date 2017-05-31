package com.example.exerciseseditor.db.converters;

import android.arch.persistence.room.TypeConverter;

import com.example.exerciseseditor.model.ExerciseDifficulty;

/**
 * Created by Радик on 22.05.2017.
 */

public final class ExerciseDifficultyConverter {
    @TypeConverter
    public static int fromEnumToInt(ExerciseDifficulty difficulty) {
        return difficulty.ordinal();
    }

    @TypeConverter
    public static ExerciseDifficulty fromIntToEnum(int difficulty) {
        return ExerciseDifficulty.values()[difficulty];
    }
}
