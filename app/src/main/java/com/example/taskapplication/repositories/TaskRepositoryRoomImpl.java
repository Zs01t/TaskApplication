package com.example.taskapplication.repositories;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.taskapplication.Task;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TaskRepositoryRoomImpl implements TaskRepository{

    TaskRoomDatabase taskRoomDatabase;
    TaskDao taskDao;
    //private List<Task> tasks;
    private LiveData<List<Task>> tasks;
    public TaskRepositoryRoomImpl(Context context)
    {
        taskRoomDatabase = TaskRoomDatabase.getInstance(context);
        taskDao = taskRoomDatabase.TaskDao();
        tasks = taskDao.getAll();

    }

    @Override
    public LiveData<List<Task>> loadTasks() {
        return tasks;
    }

    @Override
    public void deleteFinishedTasks() {

    }

    @Override
    public void addTask(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> taskDao.insert(task));
    }

    @Override
    public void updateTask(Task task) {
        TaskRoomDatabase.databaseWriteExecutor.execute(() -> taskDao.update(task));
    }
}
