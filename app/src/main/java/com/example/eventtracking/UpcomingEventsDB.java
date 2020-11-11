package com.example.eventtracking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;

public class UpcomingEventsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "upcomingEvents.db";
    private static final int VERSION = 1;
    private static final String TAG = "record";
    private String mUsername;
    private String mPassword;
    private Context context;

    private static UpcomingEventsDB mUpcomingEventsDB;

    private UpcomingEventsDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    public static UpcomingEventsDB getInstance(Context context) {
        if ( mUpcomingEventsDB == null) {
            mUpcomingEventsDB = new  UpcomingEventsDB(context);
        }
        return  mUpcomingEventsDB;
    }



    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_username = "Username";
        private static final String COL_password = "Password";

    }

    private static final class EventTable {
        private static final String TABLE = "event";
        private static final String COL_ID = "_id";
        private static final String COL_EventName = "Event_Name";
        private static final String COL_EventDescription = "Event_Description";
        private static final String COL_EventDate = "Event_Date";


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " integer primary key autoincrement, " +
                UserTable.COL_username + " text, " +
                UserTable.COL_password + " text)");

        db.execSQL("create table " + EventTable.TABLE + " (" +
                EventTable.COL_ID + " integer primary key autoincrement, " +
                EventTable.COL_EventName + " text, " +
                EventTable.COL_EventDescription + " text, " +
                EventTable.COL_EventDate + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + UserTable.TABLE + EventTable.TABLE);
        onCreate(db);
    }

    public long addEvent(String event_name, String event_description, String event_date){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EventTable.COL_EventName, event_name);
        values.put(EventTable.COL_EventDescription, event_description);
        values.put(EventTable.COL_EventDate, event_date);


        long eventId = db.insert(EventTable.TABLE, null, values);
        return eventId;
    }

    public long addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.COL_username, user.getUsername());
        values.put(UserTable.COL_password,user.getPassword());

        long userId = db.insert(UserTable.TABLE, null, values);
        return userId;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + EventTable.TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public int login(String mUsername, String mPassword){
       SQLiteDatabase db = getReadableDatabase();
       int login_status = 0;
       int i = 0;
       String sql = "select * from " + UserTable.TABLE + " where username = ?" + " and password = ?";
       Cursor cursor = db.rawQuery(sql, new String[] { mUsername, mPassword });
       if (cursor.moveToFirst()) {
          do {
             long id = cursor.getLong(0);
             String username = cursor.getString(1);
             String password = cursor.getString(2);
             Log.d(TAG, "Record = " + id + ", " + username + ", " + password);
             if(username.equalsIgnoreCase(mUsername)){
                 //success
                 login_status = 100;
             }
             ++i;

          } while (cursor.moveToNext());

       }
       else if(i == 0){
           sql = "select * from " + UserTable.TABLE + " where username = ?";
           cursor = db.rawQuery(sql, new String[] { mUsername });
           if (cursor.moveToFirst()) {
               do {
                   long id = cursor.getLong(0);
                   String username = cursor.getString(1);
                   String password = cursor.getString(2);
                   Log.d(TAG, "Record = " + id + ", " + username + ", " + password);
                   if(username.equalsIgnoreCase(mUsername)){
                       //success
                       login_status = 300;
                   }
                    ++i;
               } while (cursor.moveToNext());

           }
       }

       if(i == 0) {
           login_status = 400;
       }
        Log.d(TAG, "Login Status " + login_status);

       return login_status;
    }

    void updateData(String row_id, String name, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EventTable.COL_EventName, name);
        cv.put(EventTable.COL_EventDescription, description);
        cv.put(EventTable.COL_EventDate, date);

        long result = db.update(EventTable.TABLE, cv, "_id=?", new String[]{row_id});

    }


    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(EventTable.TABLE, "_id=?", new String[]{row_id});

    }


}
