package com.example.taskapplication.activites;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taskapplication.R;
import com.example.taskapplication.Task;
import com.example.taskapplication.databinding.ActivityTaskDetailBinding;
import com.example.taskapplication.fragments.TaskDetailFragment;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryDatabaseImpl;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity{

    private ActivityTaskDetailBinding binding;
    private TaskDetailFragment taskDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityTaskDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager fm = getSupportFragmentManager();
        taskDetailFragment = (TaskDetailFragment) fm.findFragmentById(R.id.TaskDetailFragmentContainer);

        if(taskDetailFragment == null)
        {
            FragmentTransaction t = fm.beginTransaction();
            taskDetailFragment = taskDetailFragment.newInstance();
            t.add(R.id.TaskDetailFragmentContainer, taskDetailFragment);
            t.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        Intent inIntent = getIntent();
        Task tmp = inIntent.getParcelableExtra("taskToBeModified");
        boolean editMode = true;
        if (tmp == null) editMode = false;
        getSupportActionBar().setTitle(editMode ? "Modify existing task" : "Create New Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}