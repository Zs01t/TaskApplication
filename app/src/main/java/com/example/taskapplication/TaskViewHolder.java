package com.example.taskapplication;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Import the generated binding class
import com.example.taskapplication.databinding.TaskitemBinding;

public class TaskViewHolder extends RecyclerView.ViewHolder {


    public final TaskitemBinding binding;

    //public TextView taskNameTextView;
    //public TextView taskDescTextView;

    public TaskViewHolder(TaskitemBinding binding) {
        super(binding.getRoot());
        // Access the views through the binding object
        //taskNameTextView = binding.taskNameTextView;
        //taskDescTextView = binding.taskDescTextView;

        this.binding = binding;
    }

    public void bindItem(Task item) {
        binding.taskNameTextView.setText(item.getShortName());
        binding.taskDescTextView.setText(item.getDescription());
        binding.checkBoxDone.setChecked(item.isDone());
    }
}

