package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ActivityEditorBinding;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

import javax.inject.Inject;

public class EditorActivity extends LifecycleDaggerActivity {
    @Inject ViewModelProvider.Factory viewModelFactory;

    private ActivityEditorBinding binding;
    private EditorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);

        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditorViewModel.class);
        long exerciseId = getIntent().getLongExtra("id", EditorViewModel.PHANTOM_ID);
        viewModel.init(exerciseId, this::bind);
    }

    private void bind() {
        binding.spinner2.setAdapter(new MuscleGroupsAdapter(viewModel.getMuscleGroups()));
        binding.setExercise(viewModel.getExercise());
        binding.executePendingBindings();
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
}
