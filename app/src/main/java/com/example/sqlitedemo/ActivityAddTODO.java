package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityAddTODO extends AppCompatActivity {

    TextView txtBanner;
    EditText etTitle, etDescription;
    Button btnAddTODO;
    Context context;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        txtBanner = findViewById(R.id.txtBanner);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        btnAddTODO = findViewById(R.id.btnAddToDo);
        context = getApplicationContext();
        dbHandler = new DBHandler(context);

        // Add new ToDo action button
        btnAddTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title, description;
                long started, finished;

                // get user input data
                title = etTitle.getText().toString();
                description = etDescription.getText().toString();
                started = System.currentTimeMillis();
                finished = 0;

                // set user input all data to todo model class
                TODOModel todoModel = new TODOModel(title, description, started, finished);

                // parse todo model data to it
                dbHandler.insertData(todoModel);

                // back to the activity main
                startActivity(new Intent(context, MainActivity.class));
                Toast.makeText(context, "New TODO has been added.", Toast.LENGTH_LONG).show();
            }
        });
    }
}