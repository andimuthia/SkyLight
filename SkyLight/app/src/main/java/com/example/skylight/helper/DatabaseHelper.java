package com.example.skylight.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.skylight.models.Airline;
import com.example.skylight.models.Destination;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "airline.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "airline";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_FOUNDED = "founded";
    public static final String COLUMN_DESTINATIONS = "destinations";
    public static final String COLUMN_WEBSITE = "website";
    private static final String TAG = "dbHelper";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_COUNTRY + " TEXT, " +
                COLUMN_FOUNDED + " TEXT, " +
                COLUMN_DESTINATIONS + " TEXT, " +
                COLUMN_WEBSITE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addAirline(Airline airline) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, airline.getName());
            values.put(COLUMN_COUNTRY, airline.getCountry());
            values.put(COLUMN_FOUNDED, airline.getFounded());
            values.put(COLUMN_DESTINATIONS, String.join(", ", airline.getDestinationString()));
            values.put(COLUMN_WEBSITE, airline.getWebsite());

            long result = db.insert(TABLE_NAME, null, values);

            if (result == -1) {
                Log.e(TAG, "Failed to insert book");
            } else {
                Log.i(TAG, "Book inserted successfully");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public void removeAirline(Airline airline) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String whereClause = COLUMN_NAME + " = ?";
            String[] whereArgs = { airline.getName() };

            int rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);

            if (rowsDeleted == 0) {
                Log.e(TAG, "Failed to delete airline");
            } else {
                Log.i(TAG, "Airline deleted successfully");
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            db.close();
        }
    }



    public boolean isAirlineSaved(Airline airline) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean exists = false;

        try {
            String selection = COLUMN_NAME + " = ?";
            String[] selectionArgs = { airline.getName() };

            cursor = db.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
            exists = cursor != null && cursor.getCount() > 0;
        } catch (SQLException e) {
            Log.e(TAG, "SQLException: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return exists;
    }


    public List<Airline> getAllAirlines() {
        List<Airline> airlineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT * FROM " + TABLE_NAME;
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        Cursor cursor = null;

        try{
            cursor =  db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do{
                    Airline airline = new Airline();
                    airline.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                    airline.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    airline.setCountry(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COUNTRY)));
                    airline.setFounded(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOUNDED)));
                    airline.setDestinationString(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESTINATIONS)));
                    airline.setWebsite(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WEBSITE)));
                    airlineList.add(airline);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e){
            Log.e(TAG,"SQLException: " + e.getMessage());
        } finally {
            if (cursor != null){
                cursor.close();
            }
            db.close();
        }
        return airlineList;
    }
}
