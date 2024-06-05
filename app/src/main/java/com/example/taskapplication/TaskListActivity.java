package com.example.taskapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {


    //this is an interface of a repository
    TaskRepository taskRepo;
    private TaskItemRecyclerViewAdapter adapter;
    private List<Task> tasks;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelableArrayList("LIST_STATE", new ArrayList<>(tasks));

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        //The repository gets created and then from it we load the tasks
        taskRepo = TaskRepositoryDatabaseImpl.getInstance(this);
        tasks = taskRepo.loadTasks();

        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskItemRecyclerViewAdapter();
        adapter.setItems(tasks);
        recyclerView.setAdapter(adapter);

        Button addTaskButton = findViewById(R.id.button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                intent.putExtra("from", "addTask");
                startActivity(intent);


            }
        });

        if(savedInstanceState != null) {
            tasks = savedInstanceState.getParcelableArrayList("LIST_STATE");

        }
    }
}
