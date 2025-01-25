package com.example.sizzelcraft;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
public class CartDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "CartDatabase.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_NAME = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMAGE_RES_ID = "image_res_id";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // Store context for accessing SharedPreferences
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

    // Add item to cart and update item count
    public void addItemToCart(fooditem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_IMAGE_RES_ID, item.getImageResId());

        db.insert(TABLE_NAME, null, values);
        db.close();

        // Update cart item count in SharedPreferences
        int currentCount = getCartItemCount();
        setCartItemCount(currentCount + 1); // Increment count
    }

    // Remove item from cart and update item count
    public void removeItemFromCart(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME + " = ?", new String[]{itemName});
        db.close();

        // Update cart item count in SharedPreferences
        int currentCount = getCartItemCount();
        setCartItemCount(currentCount - 1); // Decrement count
    }

    // Get all items in cart
    public ArrayList<fooditem> getCartItems() {
        ArrayList<fooditem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
                int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID));

                cartItems.add(new fooditem(imageResId, name, description, price));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return cartItems;
    }

    // Get cart item count from SharedPreferences
    int getCartItemCount() {
        SharedPreferences preferences = context.getSharedPreferences("Cart", MODE_PRIVATE);
        return preferences.getInt("item_count", 0); // Default to 0 if no items are in the cart
    }

    // Set cart item count in SharedPreferences
    private void setCartItemCount(int itemCount) {
        SharedPreferences preferences = context.getSharedPreferences("Cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("item_count", itemCount);
        editor.apply();
    }
}
