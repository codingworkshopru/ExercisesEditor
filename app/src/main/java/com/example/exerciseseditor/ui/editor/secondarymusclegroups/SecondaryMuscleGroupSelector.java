package com.example.exerciseseditor.ui.editor.secondarymusclegroups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.exerciseseditor.ui.editor.EditorActivity;
import com.example.exerciseseditor.ui.editor.EditorViewModel;
import com.example.exerciseseditor.ui.editor.MuscleGroupsAdapter;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by Радик on 06.06.2017.
 */

public class SecondaryMuscleGroupSelector extends DialogFragment {

    public interface OnSecondaryMuscleGroupSelectListener {
        void onMuscleGroupSelect(DialogInterface dialog, int which);
    }

    @Inject ViewModelProvider.Factory viewModelFactory;

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private OnSecondaryMuscleGroupSelectListener listener;
    private EditorViewModel viewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);

        EditorActivity activity = (EditorActivity) getActivity();
        viewModel = new ViewModelProvider((ViewModelStoreOwner) context, viewModelFactory).get(EditorViewModel.class);
        listener = activity;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setAdapter(new MuscleGroupsAdapter(viewModel.getMuscleGroups().getValue()), listener::onMuscleGroupSelect)
                .create();
    }

    @NonNull
    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
