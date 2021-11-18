package com.example.todolist.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.database.TodoDatabase;
import com.example.todolist.database.TodoModel;
import com.example.todolist.livedata.TodoLiveData;
import com.example.todolist.recyclerView.TodoAdapter;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TodoLiveData todoLiveData;
    RecyclerView recyclerView;
    Button addButton;
    TextView percentTextView;
    ImageButton checkButton;
    TodoAdapter todoAdapter;
    SeekBar seekBar;
    TodoDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = TodoDatabase.getInstance(this);
        addButton = findViewById(R.id.addButton);
        percentTextView = findViewById(R.id.percentTextView);
        checkButton = findViewById(R.id.checkButton);
        recyclerView = findViewById(R.id.recyclerView);
        seekBar = findViewById(R.id.seekBar);
        todoAdapter = new TodoAdapter(db);


        todoLiveData = new ViewModelProvider(this).get(TodoLiveData.class);
        todoLiveData.fetch(db.todoDAO().getSuccesses());
        todoLiveData.getTodoLiveData().observe(this, it->{
           float totalSize = it.size();
           float successSize =0;
           for (Boolean check: it){
               if(check) successSize++;
           }
           int successRate = (int)((successSize/totalSize)*100);
           String percent = String.valueOf(successRate)+"%";
           Log.d("myTag", String.valueOf(totalSize));
           Log.d("myTag", String.valueOf(successSize));
           Log.d("myTag", String.valueOf(successRate));
           percentTextView.setText(percent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                seekBar.setProgress(successRate, true);
            }
        });
        initRecyclerView();
        initButtonListener();
        initItemTouchHelper();
        initSeekBar();
    }



    private void initRecyclerView() {
        List<TodoModel> todoItems = db.todoDAO().getAll();
        todoAdapter.setTodoList(todoItems);
        recyclerView.setAdapter(todoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initItemTouchHelper() {
        ItemTouchHelper ith = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return todoAdapter.moveItem(viewHolder.getAbsoluteAdapterPosition(),target.getAbsoluteAdapterPosition());

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoAdapter.removeItem(viewHolder.getAbsoluteAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);

            }
        });
        ith.attachToRecyclerView(recyclerView);
    }
    private void initButtonListener(){
        addButton.setOnClickListener(view -> {

            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);

        });


    }

    private void initSeekBar(){
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }






}