package com.move10x.totem.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Ravi on 10/9/2015.
 * This class will handle all database CRUD Operations.
 */
public class SQLLiteService extends SQLiteOpenHelper {

    public SQLLiteService(Context context) {
        super(context, "CrmVrmDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Ceate profile table.
        String Create_Current_Profile_Table =
                "CREATE TABLE TABLE_CurrentProfile ( Key TEXT Unique, Value TEXT )";
        db.execSQL(Create_Current_Profile_Table);

        //Create appError table.
        String Create_AppErorr_Table =
                "CREATE TABLE TABLE_AppError( message TEXT , activity TEXT, errorDetails TEXT, additionalInfo TEXT)";
        db.execSQL(Create_AppErorr_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getDBInstance() {
        return super.getWritableDatabase();
    }

    public boolean isTableCreated(String tableName) {
        Cursor cursor = getDBInstance().rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }


}

