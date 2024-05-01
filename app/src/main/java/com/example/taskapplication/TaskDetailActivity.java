package com.example.taskapplication;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mSaveButton;
    private Task mCurrentTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        //implementing the save button functionality
        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(this);

        //reloading current Task if something happens
        if(savedInstanceState != null)
        {
            String name = ((EditText)findViewById(R.id.editText_Name)).getText().toString();
            String desc = ((EditText)findViewById(R.id.editText_Description)).getText().toString();

            //is the line below important? maybe, because if it is commented out the app crashes...
            //putting a breakpoint above this line doesn't help though
            mCurrentTask = savedInstanceState.getParcelable("CURRENT_TASK");
            mCurrentTask.setShortName(name);
            mCurrentTask.setDescription(desc);
            boolean isDone = ((CheckBox)findViewById(R.id.checkBox_Done)).isChecked();
            mCurrentTask.setDone(isDone);

            Date date = mCurrentTask.getCreationDate();
            String dateToString = DateFormat.getDateInstance().format(date);
            ((TextView)findViewById(R.id.textView_Date)).setText(dateToString);
        }

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

            Date date = mCurrentTask.getCreationDate();
            String dateToString = DateFormat.getDateInstance().format(date);
            ((TextView)findViewById(R.id.textView_Date)).setText(dateToString);
        }
    }
}