package com.example.taskapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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
    private List<Task> tasks = new ArrayList<>();

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        if(tasks != null)
        {
            savedInstanceState.putParcelableArrayList("LIST_STATE", new ArrayList<>(tasks));
        }


        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        taskRepo = new TaskRepositoryRoomImpl(this);


        RecyclerView recyclerView = findViewById(R.id.taskRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskItemRecyclerViewAdapter();
        adapter.setItems(tasks);
        recyclerView.setAdapter(adapter);
        taskRepo.loadTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> updatedTasks) {
                tasks = updatedTasks; // Update the local list of tasks
                adapter.setItems(tasks); // Update the RecyclerView adapter
            }
        });



        Button addTaskButton = findViewById(R.id.button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
         //       intent.putExtra("from", "addTask");
                startActivity(intent);

            }
        });
    }
}
