package com.example.hotelalgarrobo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de la tabla 'reservas' para el CRUD
        db.execSQL("CREATE TABLE reservas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cliente TEXT, " +
                "fechaEntrada TEXT, " +
                "fechaSalida TEXT, " +
                "personas INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reservas");
        onCreate(db);
    }
}
