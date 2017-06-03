package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ActivityEditorBinding;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

import javax.inject.Inject;

// TODO refactor all this shit
public class EditorActivity extends LifecycleDaggerActivity implements Observer<ExerciseEntity> {
    private ActivityEditorBinding binding;
    private EditorViewModel viewModel;
    @Inject ViewModelProvider.Factory viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditorViewModel.class);

        long exerciseId = getIntent().getLongExtra("id", EditorViewModel.INVALID_ID);
        LiveData<ExerciseEntity> liveExercise = viewModel.getExerciseById(exerciseId);
        liveExercise.observe(this, this);

        viewModel.getAllMuscleGroups().observe(this, (muscleGroups) -> binding.spinner2.setAdapter(new MuscleGroupsAdapter(muscleGroups)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.done_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doneMenuItem:
                viewModel.saveChanges();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onChanged(@Nullable ExerciseEntity exerciseEntity) {
        if (exerciseEntity != null)
            binding.setExercise(exerciseEntity);
    }
}
