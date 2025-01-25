package com.example.sizzelcraft;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

    public class MydbHelper extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "userinfo";
        public static final int DATABASE_VERSION = 1;

        private static final String TABLE_NAME = "user";
        private static final String USER_ID = "id";
        private static final String NAME = "name";
        private static final String USERNAME = "username";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";

        public MydbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT, " +
                    USERNAME + " TEXT, " +
                    PASSWORD + " TEXT, " +
                    EMAIL + " TEXT UNIQUE)";
            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean adduser(String name, String username, String email, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, name);
            values.put(USERNAME, username);
            values.put(EMAIL, email);
            values.put(PASSWORD, password);

            try {
                long result = db.insert(TABLE_NAME, null, values);
                return result != -1;
            } catch (Exception e) {
                Log.e("DB_ERROR", "Error inserting user: " + e.getMessage());
                return false;
            } finally {
                db.close();
            }
        }

        public ArrayList<usermodel> fetchuser() {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<usermodel> arruser = new ArrayList<>();
            Cursor cursor = null;

            try {
                cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
                while (cursor.moveToNext()) {
                    usermodel model = new usermodel();
                    model.id = cursor.getInt(0);
                    model.name = cursor.getString(1);
                    model.username = cursor.getString(2);
                    model.password = cursor.getString(3);
                    model.email = cursor.getString(4);
                    arruser.add(model);
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Error fetching users: " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return arruser;
        }
        public boolean validateUser(String username, String password) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = null;
            try {
                // Query to check if a user exists with the provided username and password
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = ? AND " + PASSWORD + " = ?";
                cursor = db.rawQuery(query, new String[]{username, password});

                // If the cursor has at least one row, the user is valid
                if (cursor != null && cursor.moveToFirst()) {
                    return true;
                }
            } catch (Exception e) {
                Log.e("DB_ERROR", "Error validating user: " + e.getMessage());
            } finally {
                if (cursor != null) {
                    cursor.close(); // Close cursor to prevent memory leaks
                }
                db.close(); // Close database
            }
            return false; // Return false if no user found
        }
        // Get user data from the database
        public String[] getUserData() {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] userData = new String[2]; // Array to hold name and email

            String query = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                userData[0] = cursor.getString(cursor.getColumnIndexOrThrow(USERNAME));
                userData[1] = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL));
            }

            cursor.close();
            db.close();
            return userData;
        }

    }


