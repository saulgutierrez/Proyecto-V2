package com.example.crud;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import java.util.ArrayList;
import android.database.Cursor;

public class daoContacto {
    SQLiteDatabase cx;
    ArrayList<Contacto> lista = new ArrayList<Contacto>();
    Contacto c;
    Context ct;
    String nombreBD = "BDContactos";
    String tabla = "create table if not exists contacto(id integer primary key autoincrement, nombre text, telefono text, email text, edad integer)";

    public daoContacto(Context c) {
        this.ct = c;
        cx = c.openOrCreateDatabase(nombreBD, c.MODE_PRIVATE, null);
        cx.execSQL(tabla);
    }

    public boolean insertar(Contacto c) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre",c.getNombre());
        contenedor.put("telefono",c.getTelefono());
        contenedor.put("email",c.getEmail());
        contenedor.put("edad",c.getEdad());
        return (cx.insert("contacto",null,contenedor)) > 0;
    }

    public boolean eliminar(int id) {

        return (cx.delete("contacto", "id="+id, null)) > 0;
    }

    public boolean editar(Contacto c) {
        ContentValues contenedor = new ContentValues();
        contenedor.put("nombre",c.getNombre());
        contenedor.put("telefono",c.getTelefono());
        contenedor.put("email",c.getEmail());
        contenedor.put("edad",c.getEdad());
        return (cx.update("contacto",contenedor, "id="+c.getId(), null)) > 0;
    }

    public ArrayList<Contacto> verTodos() {
        lista.clear();
        Cursor cursor = cx.rawQuery("select * from contacto", null);
        if (cursor != null && cursor.getCount() > 0 ) {
            cursor.moveToFirst();
            do {
                lista.add(new Contacto(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)));
            } while (cursor.moveToNext());
        }

        return lista;
    }

    public Contacto verUno(int posicion) {
        Cursor cursor = cx.rawQuery("select * from contacto", null);
        cursor.moveToPosition(posicion);
        c = new Contacto(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4));
        return c;
    }
}
