package com.example.taskapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapplication.databinding.TaskitemBinding;

import java.util.ArrayList;
import java.util.List;

public class TaskItemRecyclerViewAdapter  extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskList;
    public TaskItemRecyclerViewAdapter() {
        taskList = new ArrayList<>();
    }
    public void setItems(List<Task> itemList) {
        this.taskList = itemList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TaskitemBinding binding = TaskitemBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task item = taskList.get(position);
        holder.binding.taskNameTextView.setText(item.getShortName());
        holder.binding.taskDescTextView.setText(item.getDescription());
        holder.binding.checkBoxDone.setChecked(item.isDone());

        //creating intent for modifying the task in the recyclerview
        holder.itemView.setOnClickListener(v -> {
            Intent modifyTaskIntent = new Intent(v.getContext(), TaskDetailActivity.class);
            modifyTaskIntent.putExtra("taskToBeModified", item);
            modifyTaskIntent.putExtra("from", "modifyTask");
            v.getContext().startActivity(modifyTaskIntent);
        });

    }

}
