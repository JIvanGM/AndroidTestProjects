package com.ivan.garcia.sqlitetest;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final int DATABASE_VERSION = 2;

    public static final String DATABASE_NAME = "administracion";
    private static final String INT_TYPE = " INT";
    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {
    }

    public static abstract class Table1 implements BaseColumns {
        public static final String TABLE_NAME = "articulos";
        public static final String CODIGO_COL = "codigo";
        public static final String DESCRIPCION_COL = "descripcion";
        public static final String PRECIO_COL = "precio";
        public static final String DESCUENTO_COL = "descuento";
        public static final String IMPUESTO_COL = "impuesto";


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                CODIGO_COL + INT_TYPE + COMMA_SEP +
                DESCRIPCION_COL + TEXT_TYPE + COMMA_SEP +
                PRECIO_COL + REAL_TYPE + " )";

        public static final String DATABASE_ALTER_DESCUENTO = "ALTER TABLE "
                + TABLE_NAME + " ADD COLUMN " + DESCUENTO_COL + REAL_TYPE;

        public static final String DATABASE_ALTER_IMPUESTO = "ALTER TABLE "
                + TABLE_NAME + " ADD COLUMN " + IMPUESTO_COL + REAL_TYPE;

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
