package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityEditTODO extends AppCompatActivity {

    TextView txtBanner;
    EditText etTitle, etDescription;
    Button btnEditTODO;

    Context context;
    DBHandler dbHandler;
    TODOModel todoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        txtBanner = findViewById(R.id.txtBanner);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        btnEditTODO = findViewById(R.id.btnEditTODO);

        context = this;
        todoModel = new TODOModel();

        // get user selected id parsed from activity main
        Intent activityMain = getIntent();
        final int selectedId = activityMain.getIntExtra("SELECTED_ID", -1);

        // get database connection
        dbHandler = new DBHandler(context);
        // get user selected row and set it into the model class object
        todoModel = dbHandler.selectSingleTodo(selectedId);

        // set user selected todo to text field
        etTitle.setText(todoModel.getTitle());
        etDescription.setText(todoModel.getDescription());

        // edit selected ToDo action button
        btnEditTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}