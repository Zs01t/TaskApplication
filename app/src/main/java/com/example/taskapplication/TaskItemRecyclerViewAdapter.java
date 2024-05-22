package com.example.taskapplication;

import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        holder.bindItem(item);
    }

}
