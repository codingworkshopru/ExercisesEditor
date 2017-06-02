package com.example.exerciseseditor.ui.editor;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.databinding.ActivityEditorBinding;
import com.example.exerciseseditor.model.ExerciseDifficulty;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

import javax.inject.Inject;

public class EditorActivity extends LifecycleDaggerActivity {
    private ActivityEditorBinding binding;
    private EditorViewModel viewModel;
    @Inject ViewModelProvider.Factory viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EditorViewModel.class);
        viewModel.getExerciseById(getIntent().getLongExtra("id", 0)).observe(this, (exercise) -> binding.setExercise(exercise));
        viewModel.getAllMuscleGroups().observe(this, (muscleGroups) -> {
            binding.spinner2.setAdapter(new BaseAdapter() {
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
            });

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.done_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.doneMenuItem:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @BindingAdapter(value = {"android:value", "android:valueAttrChanged"}, requireAll = false)
    public static void setExerciseDifficulty(Spinner spinner, ExerciseDifficulty a, final InverseBindingListener l) {
        if (a != null) {
            spinner.setSelection(a.ordinal(), false);
        } else {
            spinner.setSelection(2, false);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (l != null) {
                    l.onChange();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    @InverseBindingAdapter(attribute = "android:value", event = "android:valueAttrChanged")
    public static long getPrimaryMuscleGroup(Spinner spinner) {
        return 0L;
    }
}
