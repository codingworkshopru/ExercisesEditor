package com.example.exerciseseditor.db.executor;

import android.os.AsyncTask;

/**
 * Created by Радик on 23.05.2017.
 */

public class TaskExecutor<T> extends AsyncTask<Supplier<T>, Void, T> {
    private Consumer<T> resultConsumer;

    private void setConsumer(Consumer<T> consumer) {
        resultConsumer = consumer;
    }

    @Override
    protected T doInBackground(Supplier<T>[] suppliers) {
        return suppliers[0].get();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        resultConsumer.accept(t);
    }

    @SuppressWarnings("unchecked")
    public static <T> void execute(Supplier<T> supplier, Consumer<T> consumer) {
        TaskExecutor<T> instance = new TaskExecutor<>();
        instance.setConsumer(consumer);
        instance.execute(supplier);
    }
}
