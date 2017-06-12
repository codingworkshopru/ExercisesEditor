package com.example.exerciseseditor.ui.editor;

import android.app.DialogFragment;
import android.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ActivityEditorBinding;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;
import com.example.exerciseseditor.ui.editor.secondarymusclegroups.SecondaryMuscleGroupSelector;
import com.example.exerciseseditor.ui.editor.secondarymusclegroups.SecondaryMuscleGroupsListAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;

public class EditorActivity extends LifecycleDaggerActivity
        implements HasFragmentInjector,
        SecondaryMuscleGroupSelector.OnSecondaryMuscleGroupSelectListener {

    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;

    private ActivityEditorBinding binding;
    private EditorViewModel viewModel;
    private SecondaryMuscleGroupsListAdapter secondaryMuscleGroupsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);

        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditorViewModel.class);
        long exerciseId = getIntent().getLongExtra("id", EditorViewModel.PHANTOM_ID);
        viewModel.init(exerciseId);
        subscribeWhenDataLoaded();
    }

    private void subscribeWhenDataLoaded() {
        viewModel.getDataLoaded().observe(this, this::onDataLoaded);
    }

    private void onDataLoaded(boolean loaded) {
        if (loaded) {
            initMuscleGroupsSpinner();
            bindContentView();
            initSecondaryMuscleGroupsList();
        }
    }

    private void initMuscleGroupsSpinner() {
        binding.spinner2.setAdapter(new MuscleGroupsAdapter(viewModel.getMuscleGroups()));
    }

    private void bindContentView() {
        binding.setExercise(viewModel.getExercise());
        binding.executePendingBindings();
    }

    private void initSecondaryMuscleGroupsList() {
        binding.secondaryMuscleGroups.setLayoutManager(new LinearLayoutManager(this));
        secondaryMuscleGroupsListAdapter = new SecondaryMuscleGroupsListAdapter(viewModel.getSecondaryMuscleGroups());
        binding.secondaryMuscleGroups.setAdapter(secondaryMuscleGroupsListAdapter);
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

    public void onAddMuscleGroupButtonClick(View view) {
        final String tag =  "selector";
        DialogFragment selector = (DialogFragment) getFragmentManager().findFragmentByTag(tag);
        if (selector == null) {
            selector = new SecondaryMuscleGroupSelector();
        }
        selector.show(getFragmentManager(), tag);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public void onMuscleGroupSelect(DialogInterface dialog, int which) {
        int index = secondaryMuscleGroupsListAdapter.getItemCount();
        viewModel.addSecondaryMuscleGroupToExercise(which);
        secondaryMuscleGroupsListAdapter.notifyItemInserted(index);
    }
}
