package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.MuscleGroupsListItemBinding;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.model.MuscleGroup;
import com.example.exerciseseditor.ui.common.BindingListAdapter;

import java.util.List;

/**
 * Created by Радик on 01.06.2017.
 */

public class MusclesAdapter extends BindingListAdapter<MuscleGroupEntity, MuscleGroupsListItemBinding> implements Observer<List<MuscleGroupEntity>> {

    public interface OnMuscleGroupClickListener {
        void onMuscleGroupClick(MuscleGroup muscleGroup);
    }

    private OnMuscleGroupClickListener listener;

    public MusclesAdapter(OnMuscleGroupClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected MuscleGroupsListItemBinding createBinding(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return DataBindingUtil.inflate(layoutInflater, R.layout.muscle_groups_list_item, parent, false);
    }

    @Override
    protected void bind(MuscleGroupsListItemBinding binding, MuscleGroupEntity item) {
        binding.setCallback(listener);
        binding.setItem(item);
    }

    @Override
    public void onChanged(@Nullable List<MuscleGroupEntity> muscleGroups) {
        setItems(muscleGroups);
    }
}
