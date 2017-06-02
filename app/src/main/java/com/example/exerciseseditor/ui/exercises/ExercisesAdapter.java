package com.example.exerciseseditor.ui.exercises;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ExercisesListItemBinding;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.model.Exercise;
import com.example.exerciseseditor.ui.common.BindingAdapter;

import java.util.List;

/**
 * Created by Радик on 02.06.2017.
 */

public class ExercisesAdapter extends BindingAdapter<ExerciseEntity, ExercisesListItemBinding> implements Observer<List<ExerciseEntity>> {

    public interface OnExerciseClickListener {
        void onExerciseClick(Exercise exercise);
    }

    private OnExerciseClickListener listener;

    public ExercisesAdapter(OnExerciseClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected ExercisesListItemBinding createBinding(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return DataBindingUtil.inflate(inflater, R.layout.exercises_list_item, parent, false);
    }

    @Override
    protected void bind(ExercisesListItemBinding binding, ExerciseEntity item) {
        binding.setCallback(listener);
        binding.setItem(item);
    }

    @Override
    public void onChanged(@Nullable List<ExerciseEntity> entities) {
        setItems(entities);
    }
}
