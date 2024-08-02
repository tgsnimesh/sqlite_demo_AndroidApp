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

public class ActivityEditTODO extends AppCompatActivity {

    TextView txtBanner;
    EditText etTitle, etDescription;
    Button btnEditTODO, btnLoadTodo;

    Context context;
    DBHandler dbHandler;
    TODOModel todoModel;
    int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        txtBanner = findViewById(R.id.txtBanner);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        btnEditTODO = findViewById(R.id.btnEditTODO);
        btnLoadTodo = findViewById(R.id.btnLoadTodo);

        context = this;
        todoModel = new TODOModel();

        // get user selected id parsed from activity main
        Intent activityMain = getIntent();
        selectedId = activityMain.getIntExtra("SELECTED_ID", -1);

        // get database connection
        dbHandler = new DBHandler(context);
        // get user selected row and set it into the model class object
        todoModel = dbHandler.selectSingleTodo(selectedId);

        // load todo info
        completeTodoInputField();

        // edit selected ToDo action button
        btnEditTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get current updated todo information
                String title = etTitle.getText().toString().trim()
                        , description = etDescription.getText().toString().trim();
                long started = System.currentTimeMillis();

                // call update method and parse model class object to it
                int status = 0;
                if(!title.isEmpty())
                    status = dbHandler.updateSingleTodo(new TODOModel(
                            selectedId
                            , title
                            , description
                            , started
                            , 0
                    ));
                else
                    Toast.makeText(context, "Your title is Empty !", Toast.LENGTH_SHORT).show();

                if (status == 1) {
                    startActivity(new Intent(context, MainActivity.class));
                    Toast.makeText(context, "ToDo has been updated.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // load todo info
        btnLoadTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeTodoInputField();
            }
        });
    }

    public void completeTodoInputField() {
        // set user selected todo to text field
        etTitle.setText(todoModel.getTitle());
        etDescription.setText(todoModel.getDescription());
    }
}