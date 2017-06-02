package com.example.exerciseseditor.ui.editor;

import android.os.Bundle;

import com.example.exerciseseditor.R;
import com.example.exerciseseditor.ui.common.LifecycleDaggerActivity;

public class EditorActivity extends LifecycleDaggerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
    }
}
