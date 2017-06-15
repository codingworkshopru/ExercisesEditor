package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.model.MuscleGroup;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;
import com.example.exerciseseditor.ui.exercises.ExercisesListActivity;

import java.util.List;

import javax.inject.Inject;

public class MuscleGroupsActivity extends LifecycleDaggerActivity {
    @Inject ViewModelProvider.Factory viewModelFactory;

    private MusclesViewModel viewModel;
    private MusclesAdapter adapter;
    private LiveData<List<MuscleGroupEntity>> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_groups_list);

        initViewModel();
        obtainData();
        initAdapter();
        initRecyclerView();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MusclesViewModel.class);
    }

    private void obtainData() {
        items = viewModel.getAllMuscleGroups();
    }

    private void initAdapter() {
        adapter = new MusclesAdapter(this::onMuscleGroupClick);
        items.observe(this, adapter);
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.muscleGroups);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
    }

    private void onMuscleGroupClick(MuscleGroup muscleGroup) {
        Intent toStartExercisesActivity = new Intent(this, ExercisesListActivity.class);
        toStartExercisesActivity.putExtra("id", muscleGroup.getId());
        startActivity(toStartExercisesActivity);
    }
}
