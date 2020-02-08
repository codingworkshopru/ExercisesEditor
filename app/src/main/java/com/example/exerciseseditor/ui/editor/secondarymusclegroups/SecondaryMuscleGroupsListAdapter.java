package com.example.exerciseseditor.ui.editor.secondarymusclegroups;

import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.SecondaryMuscleGroupsListItemBinding;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.ui.common.BindingListAdapter;

import java.util.List;

/**
 * Created by Радик on 05.06.2017.
 */

public class SecondaryMuscleGroupsListAdapter
        extends BindingListAdapter<MuscleGroupEntity, SecondaryMuscleGroupsListItemBinding> {

    public SecondaryMuscleGroupsListAdapter(List<MuscleGroupEntity> secondaryMuscleGroups) {
        setItems(secondaryMuscleGroups);
    }

    @Override
    protected SecondaryMuscleGroupsListItemBinding createBinding(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return DataBindingUtil.inflate(inflater, R.layout.secondary_muscle_groups_list_item, parent, false);
    }

    @Override
    protected void bind(SecondaryMuscleGroupsListItemBinding binding, MuscleGroupEntity item) {
        binding.setItem(item);
    }
}
