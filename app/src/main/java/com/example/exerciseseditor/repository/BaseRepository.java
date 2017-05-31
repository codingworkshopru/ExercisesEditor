package com.example.exerciseseditor.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.example.exerciseseditor.db.AppDatabase;
import com.example.exerciseseditor.db.DatabaseInitializer;

/**
 * Created by Радик on 24.05.2017.
 */

abstract class BaseRepository {
    private static final LiveData ABSENT = new LiveData() {
        {
            // noinspection unchecked
            setValue(null);
        }
    };

    private LiveData<AppDatabase> observableDatabase = DatabaseInitializer.getInstance().getDatabase();

    <F> LiveData<F> queryWhenDbInitialized(Function<AppDatabase, LiveData<F>> query) {
        return Transformations.switchMap(
                observableDatabase,
                (db) -> db == null ? ABSENT : query.apply(db)
        );
    }
}
