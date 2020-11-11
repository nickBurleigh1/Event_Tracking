package com.example.eventtracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private EditText editUsername;
    private EditText editPassword;
    private String mUsername;
    private String mPassword;
    public boolean mfirstStart;
    private UpcomingEventsDB mUpcomingEventsDB;
    public static final String EXTRA_USER = "com.example.eventtracking.USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUpcomingEventsDB = UpcomingEventsDB.getInstance(getApplicationContext());
        //getting shared preferences determine if first time using app
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        //TODO IMPLEMENT register txt on first start

        if(firstStart){
            mfirstStart = firstStart;
        }

        editUsername = (EditText) findViewById(R.id.txtUsername);
        editPassword = (EditText) findViewById(R.id.txtPassword);

        mButton = findViewById(R.id.btnLogin);
        mButton.setOnClickListener(this);
    }

    //onclick for login button
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                // Read input
                mUsername = editUsername.getText().toString();
                mPassword = editPassword.getText().toString();

                //working with user object
                User aUser = new User(mUsername, mPassword);
                aUser.setUsername(mUsername);
                aUser.setPassword(mPassword);

                    requestLogin(aUser);

        }
    }

    //Adds User
    public void isNewUser(User aUser){
        mUpcomingEventsDB.addUser(aUser);
    }

    //Query for login
    public void requestLogin(User aUser){
        String username;
        username = aUser.getUsername();
        String password;
        password = aUser.getPassword();
        int result = mUpcomingEventsDB.login(username, password);

        if(result == 100){
            //Open the other activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_USER, username);
            startActivity(intent);
        }
        if(result == 400) {
            isNewUser(aUser);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_USER, username);
            startActivity(intent);
            //TODO ADD TOAST NOTIFICATION, NEW USER REGISTER
        }

        if(result == 300){
            //do some
            //TODO TOAST NOTIFICATION BAD PASSWORD
        }
    }



}