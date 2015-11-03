package com.move10x.totem.services;

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
    public void onCreate(SQLiteDatabase db){
        String Create_Current_Profile_Table = "CREATE TABLE TABLE_CurrentProfile ("
                + "Key TEXT Unique, "
                + "Value TEXT )";
        db.execSQL(Create_Current_Profile_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public SQLiteDatabase getDBInstance(){
        return super.getWritableDatabase();
    }

}

