package com.rosegold.pantry.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * SQLite Database
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "NAME";
    private static final String COL_3 = "PRICE";

    /**
     * Constructor method
     * @param context activity
     */
    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Creates SQLite database
     * @param db SQLite database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts task into database
     * @param model task in object form to be inserted
     */
    public void insertTask(PantryModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , model.getName());
        values.put(COL_3 , 0);
        db.insert(TABLE_NAME , null , values);
    }

    /**
     * Updates task in database
     * @param id task id to be updated
     */
    public void updateTask(int id , String name){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , name);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    /**
     * Updates task status in database. Whether task is completed or not.
     * @param id task status id to be updated
     * @param price new task status
     */
    public void updateStatus(int id , int price){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , price);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    /**
     * Removes task from database
     * @param id task id to be removed
     */
    public void deleteTask(int id ){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

    /**
     * Retrieves all to do list tasks from database
     * @return list of tasks in model form
     */
    public List<PantryModel> getAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<PantryModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        PantryModel task = new PantryModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setName(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setPrice(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
        }
        return modelList;
    }

}
