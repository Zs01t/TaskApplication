package com.example.taskapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskapplication.Task;
import com.example.taskapplication.activites.TaskDetailActivity;
import com.example.taskapplication.activites.TaskListActivity;
import com.example.taskapplication.databinding.ActivityTaskListBinding;
import com.example.taskapplication.databinding.TaskListFragmentBinding;
import com.example.taskapplication.helpers.TaskItemRecyclerViewAdapter;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.util.List;

public class TaskListFragment  extends Fragment {

    private TaskListFragmentBinding binding;


    TaskRepository taskRepo;
    private TaskItemRecyclerViewAdapter adapter;
    public TaskListFragment()
    {

    }

    public static TaskListFragment newInstance()
    {
        TaskListFragment fragment = new TaskListFragment();
        //arguments can be added here
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = TaskListFragmentBinding.inflate(inflater, container,false);
        View view = binding.getRoot();

        binding.taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskItemRecyclerViewAdapter();
        binding.taskRecyclerView.setAdapter(adapter);
        taskRepo = new TaskRepositoryRoomImpl(getActivity());
        taskRepo.loadTasks().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> updatedTasks) {
                adapter.setItems(updatedTasks); // Update the RecyclerView adapter
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                startActivity(intent);
            }
        });


        return  view;
    }
}
