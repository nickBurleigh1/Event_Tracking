package com.example.eventtracking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addEvent extends AppCompatActivity {
    EditText txtDate, txtDescription, txtTitle;
    Button btnAdd;
    Event myEvent;
    private UpcomingEventsDB mUpcomingEventsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        txtDate = findViewById(R.id.txtDate);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpcomingEventsDB = UpcomingEventsDB.getInstance(getApplicationContext());


                mUpcomingEventsDB.addEvent(txtTitle.getText().toString().trim(),
                        txtDate.getText().toString().trim(),
                        txtDescription.getText().toString().trim());
            }
        });
    }
}