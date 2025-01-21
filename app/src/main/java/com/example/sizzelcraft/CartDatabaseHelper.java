package com.example.sizzelcraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CartDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CartDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_RES_ID = "image_res_id";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_IMAGE_RES_ID + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addItemToCart(fooditem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_IMAGE_RES_ID, item.getImageResId());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void removeItemFromCart(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{itemName});
        db.close();
    }
    public ArrayList<fooditem> getCartItems() {
        ArrayList<fooditem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch all cart items
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                // Fetch data for each row
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));

                // Add item to the list
                cartItems.add(new fooditem(imageResId, name, description, price));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return cartItems;
    }


}
