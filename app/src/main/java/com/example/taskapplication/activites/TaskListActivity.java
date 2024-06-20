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
import com.example.taskapplication.databinding.ActivityTaskDetailBinding;
import com.example.taskapplication.databinding.ActivityTaskListBinding;
import com.example.taskapplication.helpers.TaskItemRecyclerViewAdapter;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryDatabaseImpl;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.util.ArrayList;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {


    private ActivityTaskListBinding binding;
    TaskRepository taskRepo;
    private TaskItemRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setTitle("Task List"); // for set actionbar title

        taskRepo = new TaskRepositoryRoomImpl(this);

        binding.taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskItemRecyclerViewAdapter();
        binding.taskRecyclerView.setAdapter(adapter);
        taskRepo.loadTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> updatedTasks) {
                adapter.setItems(updatedTasks); // Update the RecyclerView adapter
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
