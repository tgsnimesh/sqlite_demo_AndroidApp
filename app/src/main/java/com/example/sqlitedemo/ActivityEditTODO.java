package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityEditTODO extends AppCompatActivity {

    TextView txtBanner;
    EditText etTitle, etDescription;
    Button btnEditTODO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        txtBanner = findViewById(R.id.txtBanner);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        btnEditTODO = findViewById(R.id.btnEditTODO);

        // edit selected ToDo action button
        btnEditTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}