package com.ivan.garcia.sqlitetest;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase.execSQL("create table articulos(codigo int primary key, descripcion text, precio real)");
        sqLiteDatabase.execSQL(DatabaseContract.Table1.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            if (newVersion == 2)
                sqLiteDatabase.execSQL(DatabaseContract.Table1.DATABASE_ALTER_DESCUENTO);
            else if (newVersion == 3)
                sqLiteDatabase.execSQL(DatabaseContract.Table1.DATABASE_ALTER_IMPUESTO);
        }

        /*sqLiteDatabase.execSQL(DatabaseContract.Table1.DELETE_TABLE);
        onCreate(sqLiteDatabase);*/
    }

    @Override // Method is called during an upgrade of the database
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
