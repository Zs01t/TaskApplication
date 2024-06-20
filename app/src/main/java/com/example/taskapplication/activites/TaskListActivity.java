package com.example.taskapplication.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.R;
import com.example.taskapplication.Task;
import com.example.taskapplication.databinding.ActivityTaskDetailBinding;
import com.example.taskapplication.databinding.ActivityTaskListBinding;
import com.example.taskapplication.fragments.TaskDetailFragment;
import com.example.taskapplication.fragments.TaskListFragment;
import com.example.taskapplication.helpers.TaskItemRecyclerViewAdapter;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryDatabaseImpl;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.util.ArrayList;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {

    private ActivityTaskListBinding binding;
    private TaskListFragment taskListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        taskListFragment = (TaskListFragment) fm.findFragmentById(R.id.TaskListFragmentContainer);

        if(taskListFragment == null)
        {
            FragmentTransaction t = fm.beginTransaction();
            taskListFragment = taskListFragment.newInstance();
            t.add(R.id.TaskListFragmentContainer, taskListFragment);
            t.commit();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        getSupportActionBar().setTitle("Task List");
    }

}
