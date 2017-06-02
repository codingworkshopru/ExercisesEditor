package com.example.exerciseseditor.ui.exercises;

import android.os.Bundle;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

public class ExercisesListActivity extends LifecycleDaggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_list);
    }
}
