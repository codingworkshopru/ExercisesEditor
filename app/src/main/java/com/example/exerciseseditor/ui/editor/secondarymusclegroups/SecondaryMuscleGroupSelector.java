package com.example.exerciseseditor.ui.editor.secondarymusclegroups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.exerciseseditor.ui.editor.EditorActivity;
import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.editor.MuscleGroupsAdapter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Радик on 06.06.2017.
 */

public class SecondaryMuscleGroupSelector extends DialogFragment implements LifecycleRegistryOwner {

    public interface OnSecondaryMuscleGroupSelectListener {
        void onMuscleGroupSelect(DialogInterface dialog, int which);
    }

    @Inject ViewModelProvider.Factory viewModelFactory;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private OnSecondaryMuscleGroupSelectListener listener;
    private EditorViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);

        EditorActivity activity = (EditorActivity) getActivity();
        viewModel = ViewModelProviders.of(activity, viewModelFactory).get(EditorViewModel.class);
        listener = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setAdapter(new MuscleGroupsAdapter(viewModel.getMuscleGroups().getValue()), listener::onMuscleGroupSelect)
                .create();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
