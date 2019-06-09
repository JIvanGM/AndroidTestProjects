package com.ivan.garcia.sqlitetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText codigoTxt, descripcionTxt, precioTxt, descuentoTxt;
    private AdminSQLiteOpenHelper adminSQLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codigoTxt = findViewById(R.id.txt_codigo);
        descripcionTxt = findViewById(R.id.txt_decripcion);
        precioTxt = findViewById(R.id.txt_precio);
        descuentoTxt = findViewById(R.id.txt_descuento);

        adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(this);
    }

    @Override
    protected void onDestroy() {
        adminSQLiteOpenHelper.close();
        super.onDestroy();
    }

    public void registrar(View view) {
        SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

        String codigo = codigoTxt.getText().toString();
        String desc = descripcionTxt.getText().toString();
        String precio = precioTxt.getText().toString();
        String descuento = descuentoTxt.getText().toString();

        if (!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()) {
            ContentValues registro = new ContentValues();

            registro.put(DatabaseContract.Table1.CODIGO_COL, codigo);
            registro.put(DatabaseContract.Table1.DESCRIPCION_COL, desc);
            registro.put(DatabaseContract.Table1.PRECIO_COL, precio);
            registro.put(DatabaseContract.Table1.DESCUENTO_COL, descuento);

            sqLiteDatabase.insert(DatabaseContract.Table1.TABLE_NAME, null, registro);

            sqLiteDatabase.close();

            codigoTxt.setText("");
            descripcionTxt.setText("");
            precioTxt.setText("");
            descuentoTxt.setText("");

            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Capture todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar(View view) {
        String codigo = codigoTxt.getText().toString();

        if (!codigo.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

            Cursor fila = sqLiteDatabase.rawQuery("select descripcion, precio from articulos where codigo=" + codigo, null);

            if (fila.moveToFirst()) {
                descripcionTxt.setText(fila.getString(0));
                precioTxt.setText(fila.getString(1));
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
            }

            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Capture el código a buscar", Toast.LENGTH_SHORT).show();
        }
    }

    public void buscar2(View view) {
        String codigo = codigoTxt.getText().toString();

        SQLiteDatabase db = adminSQLiteOpenHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                DatabaseContract.Table1.CODIGO_COL,
                DatabaseContract.Table1.DESCRIPCION_COL,
                DatabaseContract.Table1.PRECIO_COL,
                DatabaseContract.Table1.DESCUENTO_COL
        };

        // Filter results WHERE "codigo" = codigo
        String selection = DatabaseContract.Table1.CODIGO_COL + " = ?";
        String[] selectionArgs = {codigo};
        /*String selection = DatabaseContract.Table1.DESCRIPCION_COL + " LIKE ?";
        String[] selectionArgs = {"%Pizza%"};*/

        // How you want the results sorted in the resulting Cursor
        String sortOrder = DatabaseContract.Table1.PRECIO_COL + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.Table1.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while (cursor.moveToNext()) {
            descripcionTxt.setText(cursor.getString(2));
            precioTxt.setText(cursor.getString(3));
            descuentoTxt.setText(cursor.getString(4));

            Log.i("BUSCAR2", "Cursor " +
                    cursor.getString(0) + " / " +
                    cursor.getString(1) + " / " +
                    cursor.getString(2) + " / " +
                    cursor.getString(3) + " / " +
                    cursor.getString(4));
        }

        cursor.close();
        db.close();
    }

    public void eliminar(View view) {

        String codigo = codigoTxt.getText().toString();

        if (!codigo.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

            int cantidad = sqLiteDatabase.delete(DatabaseContract.Table1.TABLE_NAME, DatabaseContract.Table1.CODIGO_COL + "=" + codigo, null);

            if (cantidad > 0) {
                Toast.makeText(this, "Articulo eliminado", Toast.LENGTH_SHORT).show();


                codigoTxt.setText("");
                descripcionTxt.setText("");
                precioTxt.setText("");
            } else {
                Toast.makeText(this, "El artiulo no existe", Toast.LENGTH_SHORT).show();
            }

            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Capture el codigo a eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminar2(View view) {
        String codigo = codigoTxt.getText().toString();

        if (!codigo.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

            // Define 'where' part of query.
            String selection = DatabaseContract.Table1.CODIGO_COL + " = ?";

            // Specify arguments in placeholder order.
            String[] selectionArgs = {codigo};

            int cantidad = sqLiteDatabase.delete(DatabaseContract.Table1.TABLE_NAME, selection, selectionArgs);

            if (cantidad > 0) {
                Toast.makeText(this, "Articulo eliminado", Toast.LENGTH_SHORT).show();

                codigoTxt.setText("");
                descripcionTxt.setText("");
                precioTxt.setText("");
            } else {
                Toast.makeText(this, "El artiulo no existe", Toast.LENGTH_SHORT).show();
            }

            sqLiteDatabase.close();
        } else {
            Toast.makeText(this, "Capture el codigo a eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    public void modificar(View view) {
        String codigo = codigoTxt.getText().toString();
        String desc = descripcionTxt.getText().toString();
        String precio = precioTxt.getText().toString();

        if (!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

            ContentValues registro = new ContentValues();

            registro.put(DatabaseContract.Table1.CODIGO_COL, codigo);
            registro.put(DatabaseContract.Table1.DESCRIPCION_COL, desc);
            registro.put(DatabaseContract.Table1.PRECIO_COL, precio);

            int cantidad = sqLiteDatabase.update(DatabaseContract.Table1.TABLE_NAME, registro, DatabaseContract.Table1.CODIGO_COL + "=" + codigo, null);

            if (cantidad > 0) {
                Toast.makeText(this, "Articulo modificado", Toast.LENGTH_SHORT).show();

                codigoTxt.setText("");
                descripcionTxt.setText("");
                precioTxt.setText("");
            } else
                Toast.makeText(this, "No de modifico articulo", Toast.LENGTH_SHORT).show();

            sqLiteDatabase.close();
        } else
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_SHORT).show();
    }

    public void modificar2(View view) {
        String codigo = codigoTxt.getText().toString();
        String desc = descripcionTxt.getText().toString();
        String precio = precioTxt.getText().toString();

        if (!codigo.isEmpty() && !desc.isEmpty() && !precio.isEmpty()) {
            SQLiteDatabase sqLiteDatabase = adminSQLiteOpenHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.Table1.DESCRIPCION_COL, desc);
            values.put(DatabaseContract.Table1.PRECIO_COL, precio);

            String selection = DatabaseContract.Table1.CODIGO_COL + " = ?";
            String[] selectionArgs = {codigo};

            int cantidad = sqLiteDatabase.update(
                    DatabaseContract.Table1.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);

            if (cantidad > 0) {
                Toast.makeText(this, "Articulo modificado", Toast.LENGTH_SHORT).show();

                codigoTxt.setText("");
                descripcionTxt.setText("");
                precioTxt.setText("");
            } else
                Toast.makeText(this, "No de modifico articulo", Toast.LENGTH_SHORT).show();

            sqLiteDatabase.close();
        } else
            Toast.makeText(this, "Debes de llenar todos los campos", Toast.LENGTH_SHORT).show();


    }
}
