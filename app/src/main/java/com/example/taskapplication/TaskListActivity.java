package com.example.taskapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {


    //this is an interface of a repository
    TaskRepository taskRepo;

    private TaskItemRecyclerViewAdapter adapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        //The repository gets created and then from it we load the tasks
        taskRepo = TaskRepositoryInMemoryImpl.getInstance();
        tasks = taskRepo.loadTasks();

        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItems(tasks);
        recyclerView.setAdapter(adapter);

    }
}
