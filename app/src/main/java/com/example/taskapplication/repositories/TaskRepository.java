package com.example.taskapplication.repositories;

import com.example.taskapplication.Task;

import java.util.List;

/**
 * Created by thorsten on 23.03.20.
 */

public interface TaskRepository {

    List<Task> loadTasks();

    void deleteFinishedTasks();

    // TODO: add methods for adding new or updating existing tasks

    void addTask(Task task);
    void updateTask(Task task);
}
