package com.example.todolist.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.database.TodoDatabase;
import com.example.todolist.database.TodoModel;
import com.example.todolist.livedata.TodoLiveData;
import com.example.todolist.view.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todoList;
    private TodoDatabase db;
    private TodoLiveData todoLiveData;


    public TodoAdapter(TodoDatabase db){
        this.todoLiveData = new TodoLiveData();
        this.db = db;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(todoList.get(position));
    }

    public boolean moveItem(int fromPosition, int toPosition){
        TodoModel task = todoList.get(fromPosition);
        todoList.remove(fromPosition);
        todoList.add(toPosition, task);
        notifyItemMoved(fromPosition,toPosition);
        return true;

    }

    public void removeItem(int position){
        db.todoDAO().delete(todoList.get(position));
        todoLiveData.fetch(db.todoDAO().getSuccesses());
        todoList.remove(position);
        notifyItemRemoved(position);
    }




    @Override
    public int getItemCount() {
        return (todoList==null)?0:todoList.size();
    }

    public void setTodoList(List<TodoModel> list){

        this.todoList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView priority;
        TextView title;
        ImageButton checkButton;

        private Boolean check = false;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            priority = (ImageView) itemView.findViewById(R.id.priorityCircle);
            title = (TextView) itemView.findViewById(R.id.titleTextView);
            checkButton = (ImageButton) itemView.findViewById(R.id.checkButton);

            checkButton.setOnClickListener(view -> {
                int id = todoList.get(getAbsoluteAdapterPosition()).uid;
                if(check){
                    check = false;
                    checkButton.setBackgroundResource(R.drawable.ic_outline_circle_30);
                    itemView.setBackgroundResource(R.drawable.bg_item);
                }else{
                    check = true;
                    checkButton.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_30);
                    itemView.setBackgroundResource(R.drawable.bg_item_selected);
                }
                db.todoDAO().update(check,id);
                todoLiveData.fetch(db.todoDAO().getSuccesses());
            });
        }



        @SuppressLint({"ResourceAsColor", "NewApi"})
        public void onBind(TodoModel todoItem) {
            if(todoItem.priority == 1)
                priority.setBackgroundResource(R.drawable.ic_baseline_circle_red_12);
            else if(todoItem.priority == 2)
                priority.setBackgroundResource(R.drawable.ic_baseline_circle_orange_12);
            else
                priority.setBackgroundResource(R.drawable.ic_baseline_circle_green_12);
            title.setText(todoItem.title);

            if(todoItem.success){
                checkButton.setBackgroundResource(R.drawable.ic_baseline_check_circle_outline_30);
                itemView.setBackgroundResource(R.drawable.bg_item_selected);
            }

        }
    }
}
