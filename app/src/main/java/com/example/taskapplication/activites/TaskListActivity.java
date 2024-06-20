package com.example.taskapplication.activites;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.taskapplication.R;
import com.example.taskapplication.databinding.ActivityTaskListBinding;
import com.example.taskapplication.fragments.TaskDetailFragment;
import com.example.taskapplication.fragments.TaskListFragment;

public class TaskListActivity extends AppCompatActivity {

    private ActivityTaskListBinding binding;
    private TaskListFragment taskListFragment;
    private TaskDetailFragment taskDetailFragment;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        taskListFragment = (TaskListFragment) fm.findFragmentById(R.id.TaskListFragmentContainer);

        if(taskListFragment == null) {
            FragmentTransaction t = fm.beginTransaction();
            taskListFragment = TaskListFragment.newInstance();
            t.add(R.id.TaskListFragmentContainer, taskListFragment);
            t.commit();
        }

        isLandscape = getResources().getBoolean(R.bool.isLandscape);
        if (isLandscape) {
            taskDetailFragment = (TaskDetailFragment) fm.findFragmentById(R.id.TaskDetailFragmentContainer);

            if (taskDetailFragment == null) {
                taskDetailFragment = TaskDetailFragment.newInstance();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.TaskDetailFragmentContainer, taskDetailFragment);
                transaction.commit();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getSupportActionBar().setTitle("Task List");
    }
}