package com.example.exerciseseditor.ui.muscles;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.MuscleGroupsItemBinding;
import com.example.exerciseseditor.model.MuscleGroup;
import com.example.exerciseseditor.ui.common.BindingAdapter;

/**
 * Created by Радик on 01.06.2017.
 */

public class MusclesAdapter extends BindingAdapter<MuscleGroup, MuscleGroupsItemBinding> {
    @Override
    protected MuscleGroupsItemBinding createBinding(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return DataBindingUtil.inflate(layoutInflater, R.layout.muscle_groups_item, parent, false);
    }

    @Override
    protected void bind(MuscleGroupsItemBinding binding, MuscleGroup item) {
        binding.setItem(item);
    }
}
