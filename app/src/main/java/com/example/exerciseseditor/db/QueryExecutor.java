package com.example.exerciseseditor.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.example.exerciseseditor.di.LiveDatabase;
import com.example.exerciseseditor.util.LiveDataUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
public final class QueryExecutor implements Observer<AppDatabase> {
    public interface Operation<T> {
        void execute(T database);
    }

    private LiveData<AppDatabase> liveDb;
    private AppDatabase database;
    private Executor executor;

    @Inject
    public QueryExecutor(@LiveDatabase LiveData<AppDatabase> liveDatabase) {
        liveDb = liveDatabase;
        database = null;
        executor = Executors.newSingleThreadExecutor();
        liveDb.observeForever(this);
    }

    @Override
    public void onChanged(@Nullable AppDatabase database) {
        if (database != null)
            this.database = database;
    }

    public <F> LiveData<F> read(Function<AppDatabase, LiveData<F>> query) {
        if (database == null) {
            return Transformations.switchMap(
                    liveDb, (db) -> db == null ? LiveDataUtil.getAbsent() : query.apply(db));
        } else {
            return query.apply(database);
        }
    }

    public void execute(Operation<AppDatabase> query) {
        executor.execute(() -> query.execute(database));
    }
}
