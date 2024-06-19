package com.example.taskapplication.repositories;

import android.app.Application;
import android.content.Context;

import com.example.taskapplication.Task;

import java.util.List;

public class TaskRepositoryRoomImpl implements TaskRepository{

    TaskRoomDatabase taskRoomDatabase;
    TaskDao taskDao;
    private List<Task> tasks;

    public TaskRepositoryRoomImpl(Context context)
    {
        taskRoomDatabase = TaskRoomDatabase.getInstance(context);
        taskDao = taskRoomDatabase.TaskDao();
        tasks = taskDao.getAll();
    }

    @Override
    public List<Task> loadTasks() {
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
