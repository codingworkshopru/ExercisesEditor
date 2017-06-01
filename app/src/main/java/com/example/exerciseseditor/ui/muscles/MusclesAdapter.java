package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.MuscleGroupsItemBinding;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.ui.common.BindingAdapter;

import java.util.List;

/**
 * Created by Радик on 01.06.2017.
 */

public class MusclesAdapter extends BindingAdapter<MuscleGroupEntity, MuscleGroupsItemBinding> implements Observer<List<MuscleGroupEntity>> {
    @Override
    protected MuscleGroupsItemBinding createBinding(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return DataBindingUtil.inflate(layoutInflater, R.layout.muscle_groups_item, parent, false);
    }

    @Override
    protected void bind(MuscleGroupsItemBinding binding, MuscleGroupEntity item) {
        binding.setItem(item);
    }

    @Override
    public void onChanged(@Nullable List<MuscleGroupEntity> muscleGroups) {
        setItems(muscleGroups);
    }
}
