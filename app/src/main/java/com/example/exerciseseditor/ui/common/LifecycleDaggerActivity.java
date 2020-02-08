package com.example.exerciseseditor.ui.common;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dagger.android.AndroidInjection;

/**
 * Created by Радик on 02.06.2017.
 */

public abstract class LifecycleDaggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }
}
