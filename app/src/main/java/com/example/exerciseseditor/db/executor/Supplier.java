package com.example.exerciseseditor.db.executor;

import android.arch.core.util.Function;

/**
 * Created by Радик on 23.05.2017.
 */

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
