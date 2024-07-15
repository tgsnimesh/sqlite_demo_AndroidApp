package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtBanner, txtTODOCount;
    ListView lvTODO;
    Button btnAddTODO;
    Context context;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtBanner = findViewById(R.id.txtBanner);
        txtTODOCount = findViewById(R.id.txtTodoCount);
        lvTODO = findViewById(R.id.listViewTodo);
        btnAddTODO = findViewById(R.id.btnAddToDo);

        context = this;
        dbHandler = new DBHandler(context);

        // set todo count
        updateTODOCount();

        // Navigate button for add new todo activity
        btnAddTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate ActivityAddTODO activity
                startActivity(new Intent(getApplicationContext(), ActivityAddTODO.class));
            }
        });
    }

    // get all todo count from database todo table
    @SuppressLint("SetTextI18n")
    public void updateTODOCount() {

        int todoCount = dbHandler.getTODOCount();
        txtTODOCount.setText("You have " + String.valueOf(todoCount) + " todos in the list");
    }
}