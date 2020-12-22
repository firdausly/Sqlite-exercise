package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlite.UserContract.User;

import java.util.ArrayList;
import java.util.List;

public class UserSQLHelper extends SQLiteOpenHelper {
    public static final int DATABSE_VERSION = 1;
    public static final String DATABASE_NAME = "users.db";
    private static final String SQL_CREATE_ENTRIES=
            "CREATE TABLE " + User.TABLE_NAME + "(" +
                    User.COLUMN_PHONE + " TEXT," +
                    User.COLUMN_NAME + " TEXT," +
                    User.COLUMN_EMAIL + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +User.TABLE_NAME;

    private String[] allColumn = {
            User.COLUMN_PHONE,
            User.COLUMN_NAME,
            User.COLUMN_EMAIL
    };

    public UserSQLHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertUser(UserRecord userRecord) {

        //prepare record
        ContentValues values = new ContentValues();//container to hold the value to be inserted
        values.put(User.COLUMN_PHONE, userRecord.getPhone());
        values.put(User.COLUMN_NAME, userRecord.getName());
        values.put(User.COLUMN_EMAIL, userRecord.getEmail());

        //insert a row
        SQLiteDatabase db = this.getWritableDatabase();//getwriteable cuz wanna write to db
        db.insert(User.TABLE_NAME,null,values);

        //close db connection
        db.close();
    }

    public void updateUser(UserRecord userRecord,String nameToBeEdit) {

        String selection = User.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs={nameToBeEdit};

        //prepare record to be edited
        ContentValues values = new ContentValues();//container to hold the value to be updated
        values.put(User.COLUMN_PHONE, userRecord.getPhone());
        values.put(User.COLUMN_NAME, userRecord.getName());
        values.put(User.COLUMN_EMAIL, userRecord.getEmail());

        //update row
        SQLiteDatabase db = this.getWritableDatabase();//getwriteable cuz wanna write to db
        db.update(User.TABLE_NAME,values,selection,selectionArgs);

        //close db connection
        db.close();
    }

    public void deleteUser(String Name) {
        String selection = User.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs={Name};
        SQLiteDatabase db = this.getWritableDatabase();//getwriteable cuz wanna write to db

        int deletedRows =db.delete(User.TABLE_NAME,selection,selectionArgs);


        //close db
        db.close();


    }

    public List<UserRecord> getAllUsers(){
        List<UserRecord> records =new ArrayList<>();

        //get readable cuz only read from db,no write operation
        SQLiteDatabase db = this.getReadableDatabase();

        //cursor class to hold query result( for ez access and navigation later)
        Cursor cursor= db.query(User.TABLE_NAME,allColumn,null,
                null,
                null, null, null);

        //move to the first row of query result
        cursor.moveToFirst();

        //loop to move from row to row
        //then for each row put in arraylist
        while(!cursor.isAfterLast()){
            UserRecord userRecord = new UserRecord();

            //get the value from the query
            userRecord.setPhone(cursor.getString(0));
            userRecord.setName(cursor.getString(1));
            userRecord.setEmail(cursor.getString(2));

            //put in arraylist
            records.add(userRecord);

            cursor.moveToNext();
        }

        return records;


    }


    public UserRecord getUsers(String Name){


        String selection = User.COLUMN_NAME + " LIKE ?";
        String[] selectionArgs={Name};

        //get readable cuz only read from db,no write operation
        SQLiteDatabase db = this.getReadableDatabase();

        //cursor class to hold query result( for ez access and navigation later)
        Cursor cursor= db.query(User.TABLE_NAME,allColumn,selection, selectionArgs,
                null, null, null);

        //move to the first row of query result
        cursor.moveToFirst();


        UserRecord userRecord = new UserRecord();

        //get the value from the query
        userRecord.setPhone(cursor.getString(0));
        userRecord.setName(cursor.getString(1));
        userRecord.setEmail(cursor.getString(2));


        return userRecord;


    }
}
