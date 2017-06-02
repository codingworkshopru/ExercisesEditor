package com.example.exerciseseditor.ui.editor;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.model.ExerciseDifficulty;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

public class EditorActivity extends LifecycleDaggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
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
        spinner.setSelection(a.ordinal());
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
    public static long getPrimaryMuscleGroup(Spinner spinner) {
        return 0L;
    }
}
