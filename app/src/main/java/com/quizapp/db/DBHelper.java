package com.quizapp.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quizData.db";
//    //user
//    public static final String TABLE_USER = "tb_user";
//    public static final String COLUMN_USER_ID = "f_userid";
//    public static final String COLUMN_USER_EMAIL = "f_email";
//    public static final String COLUMN_USER_PASSWORD = "f_password";
//    public static final String COLUMN_USER_SERVERID = "f_token";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table user
//        String sqlQuery = String.format("create table %s (%s integer primary key, %s text, %s text, %s integer)",
//                TABLE_USER, COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_PASSWORD, COLUMN_USER_SERVERID);
//        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_USER));
//        onCreate(db);
    }

//    private long InsertUser(String email, String password, Long serverId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = GetContentValuesFromUser(email, password, serverId);
//        return (db.insert(TABLE_USER, null, contentValues));
//    }
//
//    private boolean UpdateUser(long id, String email, String password, Long serverId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = GetContentValuesFromUser(email, password, serverId);
//        return (db.update(TABLE_USER, contentValues, COLUMN_USER_ID + "=? ", new String[]{String.valueOf(id)}) > 0);
//    }
//
//    public int getUserRowCount() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_USER);
//        return numRows;
//    }
}