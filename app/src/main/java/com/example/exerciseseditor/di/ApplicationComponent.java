package com.example.exerciseseditor.di;

import android.content.Context;

import com.example.exerciseseditor.ExercisesEditorApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Радик on 31.05.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(ExercisesEditorApplication app);

    @Component.Builder
    interface Builder {
        ApplicationComponent build();
        @BindsInstance Builder injectContext(Context context);
    }
}
