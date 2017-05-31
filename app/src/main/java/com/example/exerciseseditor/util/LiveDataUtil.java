package com.example.exerciseseditor.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by Радик on 31.05.2017.
 */

public final class LiveDataUtil {
    private static final LiveData ABSENT = new LiveData() {
        {
            // noinspection unchecked
            setValue(null);
        }
    };

    public static LiveData getAbsent() {
        return ABSENT;
    }
}
