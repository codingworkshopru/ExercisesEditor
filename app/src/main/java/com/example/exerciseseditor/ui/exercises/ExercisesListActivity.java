package com.example.exerciseseditor.ui.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ExercisesListItemBinding;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.ui.common.BindingListViewHolder;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;
import com.example.exerciseseditor.ui.editor.EditorActivity;

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
        if (item.getItemId() == R.id.addMenuItem) {
            Intent intent = new Intent(this, EditorActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ExercisesListViewModel.class);
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
        Intent forEdit = new Intent(this, EditorActivity.class);
        forEdit.putExtra("id", exercise.getId());
        startActivity(forEdit);
    }

    private void initRecyclerView() {
        RecyclerView rv = findViewById(R.id.exercises);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ViewDataBinding binding = ((BindingListViewHolder) viewHolder).binding;
                Exercise exercise = ((ExercisesListItemBinding) binding).getItem();
                viewModel.remove(exercise);
            }
        }).attachToRecyclerView(rv);
    }
}
