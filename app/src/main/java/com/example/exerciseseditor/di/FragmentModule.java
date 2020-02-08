package com.example.exerciseseditor.di;

import com.example.exerciseseditor.ui.editor.secondarymusclegroups.SecondaryMuscleGroupSelector;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Радик on 06.06.2017.
 */

@Module
interface FragmentModule {
    @ContributesAndroidInjector
    SecondaryMuscleGroupSelector contributeSecondaryMuscleGroupSelectorInjector();
}
