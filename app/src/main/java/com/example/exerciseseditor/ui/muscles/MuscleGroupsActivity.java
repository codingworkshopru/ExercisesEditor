package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MuscleGroupsActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private MusclesViewModel viewModel;
    private MusclesAdapter adapter;
    private LiveData<List<MuscleGroupEntity>> items;
    @Inject MusclesViewModel.MusclesViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();

        super.onCreate(savedInstanceState);

        initViewModel();
        obtainData();
        initAdapter();
        initRecyclerView();
    }

    private void init() {
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_muscle_groups);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusclesViewModel.class);
    }

    private void obtainData() {
        items = viewModel.getAllMuscleGroups();
    }

    private void initAdapter() {
        adapter = new MusclesAdapter();
        items.observe(this, adapter);
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.muscleGroups);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
