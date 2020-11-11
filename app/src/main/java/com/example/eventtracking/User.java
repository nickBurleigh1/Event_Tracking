package com.example.eventtracking;

public class User
{
    private String mUsername;
    private String mPassword;
    private boolean mNewUser;



    //mStudyDb = StudyDatabase.getInstance(getApplicationContext());

    public User(String username, String password){
        mUsername = username;
        mPassword = password;

    }

    //getters and setters
    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setUsername(String mUsername){
        this.mUsername = mUsername;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

}
