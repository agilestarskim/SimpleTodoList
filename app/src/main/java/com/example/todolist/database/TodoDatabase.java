package com.example.todolist.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {TodoModel.class}, version = 2)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase INSTANCE = null;
    public abstract TodoDAO todoDAO();

    public static TodoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    TodoDatabase.class, "TodoList.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}