package com.example.exerciseseditor;

import android.app.Activity;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.widget.TextView;

import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.repository.MuscleGroupsRepository;

import java.util.List;

import dagger.android.AndroidInjection;

public class ExercisesListActivity extends Activity implements LifecycleRegistryOwner {
    public static final String TAG = ExercisesListActivity.class.getSimpleName();

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        MuscleGroupsRepository repository = new MuscleGroupsRepository();
        LiveData<List<MuscleGroupEntity>> muscleGroups = repository.getMuscleGroups();
        muscleGroups.observe(this, (list) ->
            ((TextView) findViewById(R.id.holaWorld)).setText(
                    list.stream().map((g)-> g.getName()).reduce("", (a, b)->a+","+b)
            )
        );
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
