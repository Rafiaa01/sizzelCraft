package com.example.sizzelcraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDealDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fooddeals.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "FoodDeals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_IMAGE_RES_ID = "imageResId";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    public FoodDealDatabaseHelper(Context context) {
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
        insertFoodDeal(db, R.drawable.burgerdeals, "Burger & Fries Combo", "Combo deal with fries and drink", "$7.99");
        insertFoodDeal(db, R.drawable.pizzahdeals, "Family Pizza Pack", "Large pizza with drinks for the family", "$19.99");
        insertFoodDeal(db, R.drawable.twopersondeal, "Lunch Special", "Daily lunch combo with dessert", "$9.99");
        insertFoodDeal(db, R.drawable.friendsdeals, "Dessert Deal", "Buy 1 get 1 free ice cream", "$2.99");
        insertFoodDeal(db, R.drawable.onepersondeal, "Taco Tuesday", "Special taco deal available on Tuesdays", "$4.99");
    }

    private void insertFoodDeal(SQLiteDatabase db, int imageResId, String name, String description, String price) {
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

    public Cursor getAllFoodDeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
