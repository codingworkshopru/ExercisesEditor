package com.example.exerciseseditor.ui.common;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.exerciseseditor.model.ExerciseDifficulty;

/**
 * Created by Радик on 03.06.2017.
 */

public class BindingAdapters {
    @BindingAdapter(value = {"android:value", "android:valueAttrChanged"}, requireAll = false)
    public static void setExerciseDifficulty(Spinner spinner, ExerciseDifficulty a, final InverseBindingListener l) {
        if (a != null) {
            spinner.setSelection(a.ordinal(), false);
        }
        if (l != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    l.onChange();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    @InverseBindingAdapter(attribute = "android:value", event = "android:valueAttrChanged")
    public static ExerciseDifficulty getExerciseDifficulty(Spinner spinner) {
        return ExerciseDifficulty.values()[spinner.getSelectedItemPosition()];
    }

    @BindingAdapter(value = {"android:value", "android:valueAttrChanged"}, requireAll = false)
    public static void setPrimaryMuscleGroup(Spinner spinner, long primaryMuscleGroup, final InverseBindingListener l) {
        SpinnerAdapter adapter = spinner.getAdapter();
        if (adapter != null) {
            int position = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                if (adapter.getItemId(i) == primaryMuscleGroup) {
                    position = i;
                    break;
                }
            }
            spinner.setSelection(position, false);
        }
        if (l != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    l.onChange();
                }

                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
    }

    @InverseBindingAdapter(attribute = "android:value", event = "android:valueAttrChanged")
    public static long getPrimaryMuscleGroup(Spinner spinner) {
        return spinner.getSelectedItemId();
    }
}
