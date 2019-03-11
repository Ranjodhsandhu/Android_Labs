package com.hanson.myadapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "StudentData";
    public static final String col0 = "id";
    public static final String col1 = "name";
    public static final String col2 = "gender";
    public static final String col3 = "campus";
    public static final String col4 = "semester";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("+col0+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col1 + " TEXT, "+col2+ " TEXT, "+ col3+" TEXT, "+col4+" INTEGER)" ;
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addPrimaryDetails(String name,String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,name);
        contentValues.put(col2,gender);
        long r = db.insert(TABLE_NAME,null,contentValues);
        // db.close();
        if(r == -1){
            return  false;
        }else {
            return true;
        }
    }



    public boolean updateDetails(int id, String campus,int semester){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col3,campus);
        contentValues.put(col4,semester);
        //Log.d(TAG, "addData: Adding " + item + " to " + TABLE_NAME);
        String query = "";
        long result = db.update(TABLE_NAME,contentValues,""+col0 +" = "+id,null);
        //long result = db.insert(TABLE_NAME, null, contentValues);
        //db.close();
        //if data was inserted incorrectly it will return -1
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getStudentData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        //db.close();
        return data;
    }

    public int getStudentID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + col0 + " FROM " + TABLE_NAME +
                " WHERE " + col1 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        //int i = data.getInt(0);
        //db.close();
        int id = -1;
        while(data.moveToNext()){
            id = data.getInt(0);
        }
        return id;
    }

    public int getSemester(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+col4 + " FROM "+TABLE_NAME+
                " WHERE "+ col0 + " = "+id;
        Cursor data = db.rawQuery(query,null);
       // db.close();
        int sem = 0;
        while(data.moveToNext()){
            sem = data.getInt(0);
        }

        return sem;
    }
    public String getCampus(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+col3 + " FROM "+TABLE_NAME+
                " WHERE "+ col0 + " = "+id;
        Cursor data = db.rawQuery(query,null);
       // db.close();
        String cam = "";
        while(data.moveToNext()){
            cam = data.getString(0);
        }
        return cam;
    }
    public String getName(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+col1 + " FROM "+TABLE_NAME+
                " WHERE "+ col0 + " = "+id;
        Cursor data = db.rawQuery(query,null);
      //  db.close();
        String name = "";
        while(data.moveToNext()){
            name = data.getString(0);
        }return name;
    }

    public boolean getGender(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT "+col2 + " FROM "+TABLE_NAME+
                " WHERE "+ col0 + " = "+id;
        Cursor data = db.rawQuery(query,null);
       // db.close();
        String gender = "";
        while(data.moveToNext()){
            gender = data.getString(0);
        }
        if(gender.equals("M"))
            return true;
        else if(gender.equals("F"))
            return false;

        return true;
    }
    /*
    public void updateName(String newName, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + col2 +
                " = '" + newName + "' WHERE " + col1 + " = '" + id + "'";

        // Log.d(TAG, "updateNmae: query: " + query);
       // Log.d(TAG, "updateName: Setting name to " + newName);

        db.execSQL(query);
    }
       */
    public void deleteName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + col1 + " = '" + id + "'" +
                " AND " + col2 + " = '" + name + "'";
        //Log.d(TAG, "deleteName: query: " + query);
        //Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
       // db.close();
    }


}
