package com.example.exerciseseditor.ui.exercises;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ExercisesListItemBinding;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.ui.common.BindingViewHolder;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

import java.util.List;

import javax.inject.Inject;

public class ExercisesListActivity extends LifecycleDaggerActivity {
    private ExercisesListViewModel viewModel;
    private LiveData<List<ExerciseEntity>> items;
    private ExercisesAdapter adapter;
    @Inject ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);

        initViewModel();
        obtainData();
        initAdapter();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMenuItem:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExercisesListViewModel.class);
    }

    private void obtainData() {
        Bundle args = getIntent().getExtras();
        long muscleGroupId = args.getLong("id");
        items = viewModel.getExercisesForMuscleGroup(muscleGroupId);
    }

    private void initAdapter() {
        adapter = new ExercisesAdapter(this::onExerciseClick);
        items.observe(this, adapter);
    }

    private void onExerciseClick(Exercise exercise) {
        System.out.println(exercise.getName());
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.exercises);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                Exercise exercise = ((ExercisesListItemBinding)((BindingViewHolder) viewHolder).binding).getItem();
                viewModel.remove(exercise);
            }
        }).attachToRecyclerView(rv);
    }
}
