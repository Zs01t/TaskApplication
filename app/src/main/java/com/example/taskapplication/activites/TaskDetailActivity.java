package com.example.taskapplication.activites;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taskapplication.R;
import com.example.taskapplication.Task;
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

    TaskRepository taskRepo;
    private Button mSaveButton;
    private Task mCurrentTask;
    private Calendar calendar;
    private Date dueDate;
    private boolean editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        //for the datepicker but also for conversions
        calendar = Calendar.getInstance();
        //we need it to save or update the database
        taskRepo = new TaskRepositoryRoomImpl(this);
        //implementing the save button functionality
        mSaveButton = findViewById(R.id.button_save);
        //we check if we are at this activity because another activity sent an intent
        Intent inIntent = getIntent();

        //if we came from the task list, then the taskToBeModified is not empty
        mCurrentTask = inIntent.getParcelableExtra("taskToBeModified");
        if (mCurrentTask == null) {
            mCurrentTask = new Task();
            editMode = false;
        }
        mSaveButton.setText(editMode ? "Confirm changes" : "Save");
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.editText_Name)).getText().toString();
                String desc = ((EditText)findViewById(R.id.editText_Description)).getText().toString();
                String dueDateInString = ((TextView)findViewById(R.id.textView_Date)).getText().toString();
                if(!name.isEmpty() && !dueDateInString.isEmpty())
                {
                    mCurrentTask.setShortName(name);
                    mCurrentTask.setDescription(desc);
                    boolean isDone = ((CheckBox)findViewById(R.id.checkBox_Done)).isChecked();
                    mCurrentTask.setDone(isDone);
                    if(editMode)
                    {
                        //in this case the date is already given so we need to get that value from the date Textview and set it
                        //the date is a string and its format is dd/mm/yyyy
                        //we need to transform this back to a Date
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            mCurrentTask.setDueDate(format.parse(dueDateInString));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        mCurrentTask.setDueDate(dueDate);
                    }
                    taskRepo.addTask(mCurrentTask);
                    //here, it would be better if we called the finish() method,
                    //but the task list only updates if we recreate the list activity
                    //TODO: make the list responsive with observer(?)
                    Intent backToListIntent = new Intent(TaskDetailActivity.this, TaskListActivity.class);
                    startActivity(backToListIntent);

                }
            }
        });


        //reloading current Task if something happens (we rotated the phone) or if you are modifying an existing task
        //the savedInstanceState signals this
        if(savedInstanceState != null)
        {
            ((TextView)findViewById(R.id.editText_Name)).setText(mCurrentTask.getShortName());
            ((TextView)findViewById(R.id.editText_Description)).setText(mCurrentTask.getDescription());

            Date date = mCurrentTask.getDueDate();
            Calendar cal = Calendar.getInstance();
            if (editMode) {
                cal.setTime(date);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                String stringDate = day + "/" + month + "/" + year;

                ((TextView) findViewById(R.id.textView_Date)).setText(stringDate);
            }
            ((CheckBox)findViewById(R.id.checkBox_Done)).setChecked(mCurrentTask.isDone());
        }
    }

    public void showDatePickerDialog(View view) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {

                        String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                        System.out.println(selectedDate);

                        ((TextView)findViewById(R.id.textView_Date)).setText(selectedDate);

                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            dueDate = df.parse(selectedDate);
                            //System.out.println(dueDate.toString());
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