package com.example.taskapplication.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taskapplication.Task;
import com.example.taskapplication.databinding.ActivityTaskDetailBinding;
import com.example.taskapplication.databinding.TaskDetailFragmentBinding;
import com.example.taskapplication.repositories.TaskRepository;
import com.example.taskapplication.repositories.TaskRepositoryRoomImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskDetailFragment extends Fragment {
    private TaskDetailFragmentBinding binding;

    public TaskDetailFragment()
    {

    }

    public static TaskDetailFragment newInstance()
    {
        TaskDetailFragment fragment = new TaskDetailFragment();
        //arguments can be added here
        return fragment;
    }
    TaskRepository taskRepo;
    private Task mCurrentTask;
    private boolean editMode = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //attachToParent should be true?
        binding = TaskDetailFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //we need it to save or update the database
        taskRepo = new TaskRepositoryRoomImpl(getActivity());
        //we check if we are at this activity because another activity sent an intent
        Intent inIntent = getActivity().getIntent();
        //if we came from the task list, then the taskToBeModified is not empty
        mCurrentTask = inIntent.getParcelableExtra("taskToBeModified");
        if (mCurrentTask == null) {
            mCurrentTask = new Task();
            editMode = false;


        }



        binding.buttonSave.setText(editMode ? "Confirm changes" : "Save");
        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String desc = binding.editTextDescription.getText().toString();
                String dueDateInString = binding.textViewDate.getText().toString();
                if(!name.isEmpty())
                {
                    mCurrentTask.setShortName(name);
                    mCurrentTask.setDescription(desc);
                    boolean isDone = binding.checkBoxDone.isChecked();
                    mCurrentTask.setDone(isDone);

                    //the date is a string and its format is dd/mm/yyyy
                    //we need to transform this back to a Date
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    try {
                        mCurrentTask.setDueDate(format.parse(dueDateInString));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(editMode)
                    {
                        taskRepo.updateTask(mCurrentTask);
                    }
                    else
                    {
                        taskRepo.addTask(mCurrentTask);
                    }
                    //finally we can use finish() because the observers are implemented
                    getActivity().finish();

                }
            }
        });


        binding.editTextName.setText(mCurrentTask.getShortName());
        binding.editTextDescription.setText(mCurrentTask.getDescription());

        Date date = mCurrentTask.getDueDate();
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String stringDate = day + "/" + month + "/" + year;

        binding.textViewDate.setText(stringDate);
        binding.checkBoxDone.setChecked(mCurrentTask.isDone());
        return  view;
    }



    public void showDatePickerDialog(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {

                        String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                        System.out.println(selectedDate);
                        binding.textViewDate.setText(selectedDate);

//                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//                        try {
//                            mCurrentTask.setDueDate(df.parse(selectedDate));
//                        } catch (ParseException e) {
//                           throw new RuntimeException(e);
//                       }
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
