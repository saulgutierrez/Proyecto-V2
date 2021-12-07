package com.example.crud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> lista;
    daoContacto dao;
    Contacto c;
    Activity a;
    int id = 0;

    public Adaptador(Activity a, ArrayList<Contacto> lista, daoContacto dao) {
        this.lista = lista;
        this.a = a;
        this.dao = dao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Contacto getItem(int i) {
        c = lista.get(i);
        return null;
    }

    @Override
    public long getItemId(int i) {
        c = lista.get(i);
        return c.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater li = (LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.item, null);
        }
        c = lista.get(posicion);
        TextView nombre = (TextView)v.findViewById(R.id.t_nombre);
        TextView tel = (TextView)v.findViewById(R.id.t_telefono);
        TextView email = (TextView)v.findViewById(R.id.t_email);
        TextView edad = (TextView)v.findViewById(R.id.t_edad);
        Button editar = (Button)v.findViewById(R.id.editar);
        Button eliminar = (Button)v.findViewById(R.id.eliminar);
        nombre.setText(c.getNombre());
        tel.setText(c.getTelefono());
        email.setText(c.getEmail());
        edad.setText(""+c.getEdad());
        editar.setTag(posicion);
        eliminar.setTag(posicion);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialogo de editar dialogo.xml
                int pos = Integer.parseInt(view.getTag().toString());
                final Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre);
                final EditText tel = (EditText)dialogo.findViewById(R.id.telefono);
                final EditText email = (EditText)dialogo.findViewById(R.id.email);
                final EditText edad = (EditText)dialogo.findViewById(R.id.edad);
                Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Guardar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);
                c = lista.get(pos);
                setId(c.getId());
                nombre.setText(c.getNombre());
                tel.setText(c.getTelefono());
                email.setText(c.getEmail());
                edad.setText(""+c.getEdad());
                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c = new Contacto(getId(), nombre.getText().toString(), tel.getText().toString(), email.getText().toString(), Integer.parseInt(edad.getText().toString()));
                            dao.editar(c);
                            lista = dao.verTodos();
                            notifyDataSetChanged();
                            dialogo.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(a, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dialogo para confirmar SI/NO
                int pos = Integer.parseInt(view.getTag().toString());
                c = lista.get(pos);
                setId(c.getId());
                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("Estas seguro de eliminar contacto?");
                del.setCancelable(false);
                del.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.eliminar(getId());
                        lista = dao.verTodos();
                        notifyDataSetChanged();
                    }
                });
                del.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                del.show();
            }
        });
        return v;
    }
}
