package com.example.todolist.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.R;
import com.example.todolist.database.TodoDatabase;
import com.example.todolist.database.TodoModel;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editText;
    Button priority1, priority2, priority3;
    Button uploadButton;

    String task ="";
    int priority = 1;
    TodoDatabase db = TodoDatabase.getInstance(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initButtonListener();
    }
    private void initButtonListener(){
        editText = (EditText) findViewById(R.id.editText);
        priority1 = (Button) findViewById(R.id.priority1);
        priority1.setOnClickListener(this);
        priority2 = (Button) findViewById(R.id.priority2);
        priority2.setOnClickListener(this);
        priority3 = (Button) findViewById(R.id.priority3);
        priority3.setOnClickListener(this);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.priority1:
                Log.d("myTag","priority1 clicked");
                priority = 1;
                changeBack();
                priority1.setBackgroundResource(R.drawable.button_fill_red);
                priority1.setTextColor(Color.WHITE);
                break;

            case R.id.priority2:
                Log.d("myTag","priority2 clicked");
                priority = 2;
                changeBack();
                priority2.setBackgroundResource(R.drawable.button_fill_orange);
                break;

            case R.id.priority3:
                Log.d("myTag", "prriority3 clicked");
                priority = 3;
                changeBack();
                priority3.setBackgroundResource(R.drawable.button_fill_green);
                break;

            case R.id.uploadButton:
                Log.d("myTag", "uploadButton clicked");
                TodoModel model = new TodoModel();
                model.title = editText.getText().toString();
                model.priority = priority;

                if(editText.getText().toString().equals("")){
                    Toast.makeText(this, "할 일을 입력해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                db.todoDAO().insert(model);

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

    }

    private void changeBack(){
        priority1.setBackgroundResource(R.drawable.button_empty_red);
        priority2.setBackgroundResource(R.drawable.button_empty_orange);
        priority3.setBackgroundResource(R.drawable.button_empty_green);
        priority1.setTextColor(Color.BLACK);
        priority2.setTextColor(Color.BLACK);
        priority3.setTextColor(Color.BLACK);
    }
}
