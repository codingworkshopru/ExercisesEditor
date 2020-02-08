package com.example.exerciseseditor.ui.editor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ActivityEditorBinding;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;
import com.example.exerciseseditor.ui.editor.secondarymusclegroups.SecondaryMuscleGroupSelector;
import com.example.exerciseseditor.ui.editor.secondarymusclegroups.SecondaryMuscleGroupsListAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class EditorActivity extends LifecycleDaggerActivity
        implements HasAndroidInjector,
        SecondaryMuscleGroupSelector.OnSecondaryMuscleGroupSelectListener {

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject DispatchingAndroidInjector<Object> fragmentInjector;

    private ActivityEditorBinding binding;
    private EditorViewModel viewModel;
    private SecondaryMuscleGroupsListAdapter secondaryMuscleGroupsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);

        initViewModel();
        initData();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, viewModelFactory).get(EditorViewModel.class);
        long exerciseId = getIntent().getLongExtra("id", EditorViewModel.PHANTOM_ID);
        viewModel.init(exerciseId);
    }

    private void initData() {
        viewModel.getMuscleGroups().observe(this, this::initMuscleGroupsSpinner);
        viewModel.getExercise().observe(this, (exercise) -> binding.setExercise(exercise));
        viewModel.getSecondaryMuscleGroups().observe(this, this::initSecondaryMuscleGroupsList);
    }

    private void initMuscleGroupsSpinner(List<MuscleGroupEntity> muscleGroups) {
        MuscleGroupsAdapter muscleGroupsAdapter = new MuscleGroupsAdapter(muscleGroups);
        binding.spinner2.setAdapter(muscleGroupsAdapter);
    }

    private void initSecondaryMuscleGroupsList(List<MuscleGroupEntity> secondaryMuscleGroups) {
        RecyclerView rv = binding.secondaryMuscleGroups;
        rv.setLayoutManager(new LinearLayoutManager(this));
        secondaryMuscleGroupsListAdapter = new SecondaryMuscleGroupsListAdapter(secondaryMuscleGroups);
        rv.setAdapter(secondaryMuscleGroupsListAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int removedIndex = viewHolder.getAdapterPosition();
                viewModel.removeSecondaryMuscleGroupFromExercise(removedIndex);
                secondaryMuscleGroupsListAdapter.notifyItemRemoved(removedIndex);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.done_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.doneMenuItem) {
            viewModel.saveChanges();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddMuscleGroupButtonClick(View view) {
        final String tag =  "selector";
        DialogFragment selector = (DialogFragment) getSupportFragmentManager().findFragmentByTag(tag);
        if (selector == null) {
            selector = new SecondaryMuscleGroupSelector();
        }
        selector.show(getSupportFragmentManager(), tag);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return fragmentInjector;
    }

    @Override
    public void onMuscleGroupSelect(DialogInterface dialog, int which) {
        int index = secondaryMuscleGroupsListAdapter.getItemCount();
        viewModel.addSecondaryMuscleGroupToExercise(which);
        secondaryMuscleGroupsListAdapter.notifyItemInserted(index);
    }
}
