package com.example.sizzelcraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodDealDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food_deals.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_FOOD_DEALS = "food_deals";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_RES_ID = "image_res_id";

    public FoodDealDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_FOOD_DEALS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_IMAGE_RES_ID + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_DEALS);
        onCreate(db);
    }

    public void insertFoodDeal(foodmodel foodDeal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, foodDeal.getName());
        values.put(COLUMN_DESCRIPTION, foodDeal.getDescription());
        values.put(COLUMN_PRICE, foodDeal.getPrice());
        values.put(COLUMN_IMAGE_RES_ID, foodDeal.getImageResId());
        db.insert(TABLE_FOOD_DEALS, null, values);
        db.close();
    }

    public List<foodmodel> getAllFoodDeals() {
        List<foodmodel> foodDeals = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOOD_DEALS, null, null, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
            int descriptionIndex = cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION);
            int priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRICE);
            int imageResIdIndex = cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID);

            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                double price = cursor.getDouble(priceIndex);
                int imageResId = cursor.getInt(imageResIdIndex);

                foodDeals.add(new foodmodel(name, imageResId, description, price));
            }
            cursor.close();
        }

        db.close();
        return foodDeals;
    }
}
