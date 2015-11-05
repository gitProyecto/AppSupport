package com.miranda.luis.appsupport;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luis on 28/10/15.
 */
public class Database extends SQLiteOpenHelper {

    private static final int V_BASEDATOS = 1;

    // Nombre de nuestro archivo de base de datos
    private static final String NOMBRE_BASEDATOS = "usuarios.db";

    // Sentencia SQL para la creación de una tabla
    private static final String TABLA_USUARIOS = "CREATE TABLE usuarios(id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, correo TEXT NOT NULL UNIQUE, clave TEXT NOT NULL, regId TEXT NOT NULL UNIQUE, cargo INTEGER NOT NULL DEFAULT 0, estatus INTEGER NOT NULL DEFAULT 0);";

    private static final String TABLA_MENSAJES = "CREATE TABLE mensajes(id INTEGER PRIMARY KEY AUTOINCREMENT, serie TEXT NOT NULL UNIQUE, tipo TEXT NOT NULL, mensaje TEXT NOT NULL, datos TEXT NOT NULL, origen TEXT NOT NULL, destino TEXT NOT NULL, prioridad TEXT NOT NULL, tamaño TEXT NOT NULL, fecha TIMESTAMP " +
            " DEFAULT CURRENT_TIMESTAMP, estatus TEXT NOT NULL  );";

    // CONSTRUCTOR de la clase
    public Database(Context context) {
        super(context, NOMBRE_BASEDATOS, null, V_BASEDATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
        db.execSQL(TABLA_MENSAJES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_MENSAJES);
        onCreate(db);
    }


    public void insert(String usuarios, Object o, ContentValues nuevoRegistro) {


    }
}
