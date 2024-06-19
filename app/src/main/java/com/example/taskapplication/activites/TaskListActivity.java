package com.example.taskapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.Task;
import com.example.taskapplication.helpers.TaskItemRecyclerViewAdapter;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryDatabaseImpl;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.util.ArrayList;
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
        taskRepo = new TaskRepositoryRoomImpl(this);
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
         //       intent.putExtra("from", "addTask");
                startActivity(intent);

            }
        });

        if(savedInstanceState != null) {
            tasks = savedInstanceState.getParcelableArrayList("LIST_STATE");

        }
    }
}
