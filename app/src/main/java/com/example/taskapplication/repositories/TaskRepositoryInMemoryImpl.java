package com.example.taskapplication.repositories;

import androidx.lifecycle.LiveData;

import com.example.taskapplication.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thorsten on 23.03.20.
 */

public class TaskRepositoryInMemoryImpl implements TaskRepository {

    private static TaskRepositoryInMemoryImpl instance;

    private List<Task> mTasks;


    public static synchronized TaskRepositoryInMemoryImpl getInstance() {
        if (instance == null) {
            instance = new TaskRepositoryInMemoryImpl();
        }
        return instance;
    }


    private TaskRepositoryInMemoryImpl() {
        mTasks = new ArrayList<>();

        Task myTask = new Task("Empty the trash");
        myTask.setDescription("Someone has to get the dirty jobs done...");
        myTask.setDone(true);
        myTask.setDueDate(new Date(2024, 6, 10));
        mTasks.add(myTask);
        mTasks.add(new Task("Groceries"));
        mTasks.add(new Task("Call parents"));
        myTask = new Task("Do Android programming");
        myTask.setDescription("Nobody said it would be easy!");
        myTask.setDone(false);
        mTasks.add(myTask);

//        for (int i=0; i<40; i++)
//            mTasks.add(new Task("Task" + i));
    }

    @Override
    public LiveData<List<Task>> loadTasks() {
        return null;
    }

    @Override
    public void deleteFinishedTasks() {
        for (int i=0; i<mTasks.size(); i++) {
            Task task = mTasks.get(i);
            if (task.isDone()) {
                mTasks.remove(task);
                i--;
            }
        }
    }

    @Override
    public void addTask(Task task) {
        mTasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        int id = task.getId();

        for(int i = 0; i < mTasks.size(); i++) {
            if(mTasks.get(i).getId() == id) {
                mTasks.set(i, task);
            }
        }
    }
}
