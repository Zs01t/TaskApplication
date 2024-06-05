package com.example.taskapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class TaskDetailActivity extends AppCompatActivity{

    TaskRepository taskRepo;
    private Button mSaveButton;
    private Task mCurrentTask;
    private Calendar calendar;

    private Date dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);


        taskRepo = TaskRepositoryDatabaseImpl.getInstance(this);

        //implementing the save button functionality
        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.editText_Name)).getText().toString();
                String desc = ((EditText)findViewById(R.id.editText_Description)).getText().toString();
                if(!name.isEmpty() && !desc.isEmpty())
                {
                    //in the constructor the date is getting set
                    mCurrentTask = new Task(name);
                    mCurrentTask.setDescription(desc);
                    boolean isDone = ((CheckBox)findViewById(R.id.checkBox_Done)).isChecked();
                    mCurrentTask.setDone(isDone);

                    mCurrentTask.setDueDate(dueDate);
                    taskRepo.addTask(mCurrentTask);
                }
            }
        });

        calendar = Calendar.getInstance();
        Intent inIntent = getIntent();

        //reloading current Task if something happens
        if(savedInstanceState != null || Objects.equals(inIntent.getStringExtra("from"), "modifyTask"))
        {
            if(savedInstanceState != null)
            {
                mCurrentTask = savedInstanceState.getParcelable("CURRENT_TASK");
            }
            else
            {
                mCurrentTask = inIntent.getParcelableExtra("taskToBeModified");
            }

            ((TextView)findViewById(R.id.editText_Name)).setText(mCurrentTask.getShortName());
            ((TextView)findViewById(R.id.editText_Description)).setText(mCurrentTask.getDescription());

            Date date = mCurrentTask.getDueDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int month = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);
            String stringDate = day + "/" + month + "/" + year;

            ((TextView)findViewById(R.id.textView_Date)).setText(stringDate);
            boolean isDone = ((CheckBox)findViewById(R.id.checkBox_Done)).isChecked();

        }


    }

    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                        System.out.println(selectedDate);

                        ((TextView)findViewById(R.id.textView_Date)).setText(selectedDate);

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            dueDate = df.parse(selectedDate);
                            System.out.println(dueDate.toString());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelable("CURRENT_TASK", (Parcelable) mCurrentTask);

        super.onSaveInstanceState(savedInstanceState);
    }

}