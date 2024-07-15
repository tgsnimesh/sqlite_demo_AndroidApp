package com.example.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtBanner, txtTODOCount;
    ListView lvTODO;
    Button btnAddTODO;
    Context context;
    DBHandler dbHandler;
    ArrayList<TODOModel> todos;
    TODOArrayAdapter todoArrayAdapter;

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
        todos = new ArrayList<>();

        // set todo count
        updateTODOCount();

        // get and load all todo list from dbHandler when app is creating
        loadAllTODOs();

        // Navigate button for add new todo activity
        btnAddTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate ActivityAddTODO activity
                startActivity(new Intent(getApplicationContext(), ActivityAddTODO.class));
            }
        });

        // show dialog box when user click todo list item
        lvTODO.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get user selected todo model class object from the all todo array list
                TODOModel userSelectedTodo = todos.get(position);
                // create popup alert dialog action box
                showPopupActionBox(userSelectedTodo);
            }
        });
    }

    // create popup action alert dialog box
    private void showPopupActionBox(TODOModel userSelectedTodo) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(userSelectedTodo.getTitle());
        alertBuilder.setMessage(userSelectedTodo.getDescription());
        alertBuilder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // navigate to the edit todo activity with user selected id
                Intent activityEditTODO = new Intent(context, ActivityEditTODO.class);
                activityEditTODO.putExtra("SELECTED_ID", userSelectedTodo.getId());
                startActivity(activityEditTODO);

            }
        });
        alertBuilder.setPositiveButton(userSelectedTodo.getFinished() > 0 ? "Remove Finished" : "Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userSelectedTodo.setFinished(userSelectedTodo.getFinished() > 0 ? 0 : System.currentTimeMillis());
                if (dbHandler.finishTodo(userSelectedTodo) == 1)
                    Toast.makeText(context, "Well done! your todo has been finished.", Toast.LENGTH_SHORT).show();
                loadAllTODOs();
            }
        });
        // Delete selected dialog box
        alertBuilder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // call delete method
                dbHandler.deleteSingleTodo(userSelectedTodo.getId());
                // reload updated todo list
                loadAllTODOs();
                Toast.makeText(context, "Your todo has been deleted.", Toast.LENGTH_LONG).show();
            }
        });
        alertBuilder.show();
    }

    // get all todo count from database todo table
    @SuppressLint("SetTextI18n")
    public void updateTODOCount() {

        int todoCount = dbHandler.getTODOCount();
        txtTODOCount.setText("You have " + String.valueOf(todoCount) + " todos in the list");
    }

    // get and lead all todo list
    public void loadAllTODOs() {

        // get all todos from the DBHandler
        todos = (ArrayList<TODOModel>) dbHandler.getAllTODOS();
        // load todos calling custom array adapter.
        todoArrayAdapter = new TODOArrayAdapter(context, R.layout.single_todo_row, todos);
        // set adapter in to the list view
        lvTODO.setAdapter(todoArrayAdapter);
    }
}