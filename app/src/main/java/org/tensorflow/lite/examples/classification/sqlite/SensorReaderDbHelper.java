package org.tensorflow.lite.examples.classification.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.tensorflow.lite.examples.classification.model.SensorDataObject;


public class SensorReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SensorReader.db";

    public SensorReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SensorReaderContract.SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SensorReaderContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long insertSensorData(SensorDataObject data){

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_ACC_X, data.getAccx());
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_ACC_Y, data.getAccy());
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_ACC_Z, data.getAccz());
        values.put(SensorReaderContract.SensorEntry.COLUMN_NAME_LIGHT, data.getLight());

        SQLiteDatabase db = getWritableDatabase();
        try {
            // Insert the new row, returning the primary key value of the new row
            return db.insert(SensorReaderContract.SensorEntry.TABLE_NAME, null, values);
        } finally {
            db.close();
        }
    }
}