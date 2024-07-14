package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityAddTODO extends AppCompatActivity {

    TextView txtBanner;
    EditText etTitle, etDescription;
    Button btnAddTODO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        txtBanner = findViewById(R.id.txtBanner);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        btnAddTODO = findViewById(R.id.btnAddToDo);

        // Add new ToDo action button
        btnAddTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}