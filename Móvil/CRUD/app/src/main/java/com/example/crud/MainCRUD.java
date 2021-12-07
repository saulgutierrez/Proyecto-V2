package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.app.IntentService;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Dialog;
import android.widget.Toast;

import java.util.ArrayList;

public class MainCRUD extends AppCompatActivity {
    daoContacto dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_crud);
        dao = new daoContacto(this);
        lista = dao.verTodos();
        adapter = new Adaptador(this, lista, dao);
        final ListView list = (ListView)findViewById(R.id.lista);
        Button agregar = (Button)findViewById(R.id.agregar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Dialogo para ver vista previa de registro vista.xml
            }
        });
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialogo de agregar dialogo.xml
                final Dialog dialogo = new Dialog(MainCRUD.this);
                dialogo.setTitle("Nuevo registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                final EditText nombre = (EditText)dialogo.findViewById(R.id.nombre);
                final EditText tel = (EditText)dialogo.findViewById(R.id.telefono);
                final EditText email = (EditText)dialogo.findViewById(R.id.email);
                final EditText edad = (EditText)dialogo.findViewById(R.id.edad);
                final Button guardar = (Button)dialogo.findViewById(R.id.d_agregar);
                guardar.setText("Agregar");
                Button cancelar = (Button)dialogo.findViewById(R.id.d_cancelar);

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            c = new Contacto(nombre.getText().toString(), tel.getText().toString(), email.getText().toString(), Integer.parseInt(edad.getText().toString()));
                            dao.insertar(c);
                            lista = dao.verTodos();
                            adapter.notifyDataSetChanged();
                            dialogo.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
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
    }
}
