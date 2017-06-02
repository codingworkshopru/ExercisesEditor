package com.example.exerciseseditor.ui.muscles;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.MuscleGroupItemBinding;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.model.MuscleGroup;
import com.example.exerciseseditor.ui.common.BindingAdapter;

import java.util.List;

/**
 * Created by Радик on 01.06.2017.
 */

public class MusclesAdapter extends BindingAdapter<MuscleGroupEntity, MuscleGroupItemBinding> implements Observer<List<MuscleGroupEntity>> {

    public interface OnMuscleGroupClickListener {
        void onMuscleGroupClick(MuscleGroup muscleGroup);
    }

    private OnMuscleGroupClickListener listener;

    public MusclesAdapter(OnMuscleGroupClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected MuscleGroupItemBinding createBinding(ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MuscleGroupItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.muscle_group_item, parent, false);
        binding.setCallback(listener);
        return binding;
    }

    @Override
    protected void bind(MuscleGroupItemBinding binding, MuscleGroupEntity item) {
        binding.setItem(item);
    }

    @Override
    public void onChanged(@Nullable List<MuscleGroupEntity> muscleGroups) {
        setItems(muscleGroups);
    }
}
