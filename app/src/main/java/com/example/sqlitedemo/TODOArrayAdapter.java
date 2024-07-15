package com.example.sqlitedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TODOArrayAdapter extends ArrayAdapter<TODOModel> {

    private final Context context;
    private final int resource;
    private final ArrayList<TODOModel> todos;

    TODOArrayAdapter(Context context, int resource, ArrayList<TODOModel> todos) {
        super(context, resource, todos);
        this.context = context;
        this.resource = resource;
        this.todos = todos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder")
        View singleRow = inflater.inflate(resource, parent, false);

        TextView txtTitle, txtDescription;
        ImageView imgViewFinished;

        txtTitle = singleRow.findViewById(R.id.txtTitle);
        txtDescription = singleRow.findViewById(R.id.txtDescription);
        imgViewFinished = singleRow.findViewById(R.id.imgFinished);

        TODOModel todoModel = todos.get(position);

        txtTitle.setText(todoModel.getTitle());
        txtDescription.setText(todoModel.getDescription());

        if(todoModel.getFinished() > 0) {
            imgViewFinished.setVisibility(View.VISIBLE);
        }

        return singleRow;
    }
}
