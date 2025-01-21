package com.example.sizzelcraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "menu.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "FoodItems";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE_RES_ID = "imageResId";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IMAGE_RES_ID + " INTEGER, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " TEXT)";
        db.execSQL(createTableQuery);

        // Insert sample data
        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {

    }

    private void insertFoodItem(SQLiteDatabase db, int imageResId, String name, String description, String price) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_IMAGE_RES_ID, imageResId);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllFoodItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}