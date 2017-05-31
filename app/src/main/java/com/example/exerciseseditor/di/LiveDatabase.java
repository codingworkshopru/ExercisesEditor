package com.example.exerciseseditor.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Радик on 31.05.2017.
 */

@Qualifier
@Documented
@Retention(RUNTIME)
public @interface LiveDatabase {}
