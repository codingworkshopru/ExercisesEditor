package com.example.exerciseseditor.db;

import android.arch.core.executor.testing.CountingTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.exerciseseditor.db.dao.ExerciseDao;
import com.example.exerciseseditor.db.entity.ExerciseEntity;
import com.example.exerciseseditor.db.entity.MuscleGroupEntity;
import com.example.exerciseseditor.model.ExerciseDifficulty;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Радик on 23.05.2017.
 */

@RunWith(AndroidJUnit4.class)
public class InitializationAndLoadingTest {
    private Context context = InstrumentationRegistry.getTargetContext();
    private AppDatabase db;
    private ExerciseDao exerciseDao;

    @Rule
    public CountingTaskExecutorRule rule = new CountingTaskExecutorRule();

    @Before
    public void initializeDatabase() throws Throwable {
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        rule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
//                AsyncTask.execute(() -> new DatabaseInitializer().populateDatabase(db));
                exerciseDao = db.getExerciseDao();
            }
        }, null).evaluate();
    }

    @After
    public void cleanup() {
        db.close();
    }

    @Test
    public void testDbInitialization() throws Throwable {
        final String expected = "Плечи";
        rule.apply(new Statement() {
            @Override
            public void evaluate() throws Throwable {
//                LiveData<List<MuscleGroupEntity>> liveResult = db.getMuscleGroupDao().getAllMuscleGroups();
//                liveResult.observeForever(result -> {
                List<MuscleGroupEntity> result = db.getMuscleGroupDao().getAllMuscleGroupsSync();
                    assertNotNull("Null result returned", result);
                    assertNotEquals("List with result is empty", 0, result.size());
                    assertEquals("This is unbelievable", expected, result.get(0).getName());
//                });
            }
        }, null).evaluate();
    }

    @Test
    public void testCRUD() {
        String exerciseName = "Squats";
        String description = "Sit down and stand up. That's all.";
        ExerciseDifficulty difficulty = ExerciseDifficulty.DIFFICULT;
        long primaryMuscleGroup = 1;

        // create
        ExerciseEntity exercise = new ExerciseEntity();
        exercise.setName(exerciseName);
        exercise.setDifficulty(difficulty);
        exercise.setPrimaryMuscleGroup(primaryMuscleGroup);
        exerciseDao.createExercise(exercise);

        // read
        List<ExerciseEntity> result = getExercisesSync();
        assertNotNull("Null result returned.", result);
        exercise = result.get(0);
        assertEquals("Insert fail.", exercise.getName(), exerciseName);

        // update
        exercise.setSteps(description);
        exerciseDao.updateExercise(exercise);
        result = getExercisesSync();
        exercise = result.get(0);
        assertEquals("Update fail.", exercise.getSteps(), description);

        // delete
        exerciseDao.deleteExercise(exercise);
        assertEquals("Deletion fail.", 0, getExercisesSync().size());
    }

    private List<ExerciseEntity> getExercisesSync() {
        return exerciseDao.getAllExercisesSync();
    }
}
