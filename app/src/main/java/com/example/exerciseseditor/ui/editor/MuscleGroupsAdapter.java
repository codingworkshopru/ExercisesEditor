package com.example.exerciseseditor.ui.editor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.example.exerciseseditor.db.entity.MuscleGroupEntity;

import java.util.List;

/**
 * Created by Радик on 03.06.2017.
 */
class MuscleGroupsAdapter extends BaseAdapter {
    private final List<MuscleGroupEntity> muscleGroups;

    public MuscleGroupsAdapter(List<MuscleGroupEntity> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    @Override
    public int getCount() {
        return muscleGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return muscleGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return muscleGroups.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        } else {
            view = convertView;
        }

        ((CheckedTextView) view).setText(muscleGroups.get(position).getName());

        return view;
    }
}
