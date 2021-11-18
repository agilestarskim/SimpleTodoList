package com.example.todolist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TodoDAO {
    @Query("SELECT * FROM todoList ORDER BY priority")
    List<TodoModel> getAll();

    @Query("SELECT success FROM todoList")
    List<Boolean> getSuccesses();

    @Insert
    void insert(TodoModel todoModel);

    @Delete
    void delete(TodoModel todoModel);

    @Query("UPDATE todoList SET success = :check WHERE uid == :id")
    void update(Boolean check, int id);
}
