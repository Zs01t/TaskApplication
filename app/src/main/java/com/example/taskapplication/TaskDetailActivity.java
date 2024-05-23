package com.example.taskapplication;

import android.app.DatePickerDialog;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSaveButton;
    private Task mCurrentTask;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //implementing the save button functionality
        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(this);



        calendar = Calendar.getInstance();

        //reloading current Task if something happens
        if(savedInstanceState != null)
        {
            //String name = ((EditText)findViewById(R.id.editText_Name)).getText().toString();
            //String desc = ((EditText)findViewById(R.id.editText_Description)).getText().toString();

            //is the line below important? maybe, because if it is commented out the app crashes...
            //putting a breakpoint above this line doesn't help though
            mCurrentTask = savedInstanceState.getParcelable("CURRENT_TASK");

            ((TextView)findViewById(R.id.editText_Name)).setText(mCurrentTask.getShortName());
            ((TextView)findViewById(R.id.editText_Description)).setText(mCurrentTask.getDescription());

            int day = mCurrentTask.getDueDate().getDay();
            int month = mCurrentTask.getDueDate().getMonth();
            int year = mCurrentTask.getDueDate().getYear();

            String stringDate = day + "/" + month + "/" + year;

            ((TextView)findViewById(R.id.textView_Date)).setText(stringDate);
            //boolean isDone = ((CheckBox)findViewById(R.id.checkBox_Done)).isChecked();

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

                        String selectedDate = dayOfMonth + "/" + month + "/" + year;
                        ((TextView)findViewById(R.id.textView_Date)).setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putParcelable("CURRENT_TASK", (Parcelable) mCurrentTask);

        super.onSaveInstanceState(savedInstanceState);
    }

    //maybe I should make it an anonymous function inside OnCreate(), so that way I don't need to implement the View.OneClickListener interface
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

            String dueDateTextView = ((TextView)findViewById(R.id.textView_Date)).getText().toString();

            String[] splitDate = dueDateTextView.split("/");

            Date date = new Date(Integer.parseInt(splitDate[2]) , Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]));
            System.out.println("day: " + splitDate[0]);
            System.out.println("month: " + splitDate[1]);
            System.out.println("year: " + splitDate[2]);

            mCurrentTask.setDueDate(date);
            //String dateToString = DateFormat.getDateInstance().format(date);
            //((TextView)findViewById(R.id.textView_Date)).setText(dateToString);


        }
    }
}