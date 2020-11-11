package com.example.eventtracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Record";
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ArrayList<String> event_id, event_name, event_description, event_date;
    private UpcomingEventsDB mUpcomingEventsDB;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycleview);
        add_button = findViewById(R.id.add_event);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addEvent.class);
                startActivity(intent);
            }
        });

        mUpcomingEventsDB = UpcomingEventsDB.getInstance(getApplicationContext());
        event_id = new ArrayList<>();
        event_name = new ArrayList<>();
        event_date = new ArrayList<>();
        event_description = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this, event_id, event_name, event_description,
               event_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeDataInArrays(){
        Cursor cursor = mUpcomingEventsDB.readAllData();
       // if(cursor.getCount() == 0){
            //empty_imageview.setVisibility(View.VISIBLE);
           // no_data.setVisibility(View.VISIBLE);
        //}else{
            while (cursor.moveToNext()){
               // long id = cursor.getLong(0);
                event_id.add(cursor.getString(0));
                event_name.add(cursor.getString(1));
                event_description.add(cursor.getString(2));
                event_date.add(cursor.getString(3));
               // String event_date = cursor.getString(3);
               // Log.d(TAG, "Record = " + id + ", " + event_name + ", " + event_date);
            }
            //empty_imageview.setVisibility(View.GONE);
           // no_data.setVisibility(View.GONE);
        //}
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/
}