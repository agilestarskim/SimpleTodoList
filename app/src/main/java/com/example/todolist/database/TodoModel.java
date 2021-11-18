package com.example.todolist.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todoList")
public class TodoModel{
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "priority")
    public int priority;
    @ColumnInfo(name = "success", defaultValue = "false")
    public boolean success;

}
