package com.example.todolist.livedata;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Database;

import com.example.todolist.database.TodoDatabase;
import com.example.todolist.database.TodoModel;
import com.example.todolist.recyclerView.TodoAdapter;
import com.example.todolist.view.MainActivity;

import java.util.List;

public class TodoLiveData extends ViewModel {


    static public MutableLiveData<List<Boolean>> todoLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Boolean>> getTodoLiveData(){
        if (todoLiveData == null) {
            todoLiveData = new MutableLiveData<>();
        }
        return todoLiveData;
    }

    public void fetch(List<Boolean>item){
        todoLiveData.setValue(item);
    }
}
