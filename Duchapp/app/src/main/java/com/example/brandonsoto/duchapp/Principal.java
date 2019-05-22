package com.example.brandonsoto.duchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    Button Siguiente;
    EditText caudal,precio;
    String caudal_txt,precio_txt;
    Bundle dato;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Siguiente = (Button)findViewById(R.id.btn_siguiente);

        dato = getIntent().getExtras();
        usuario = "" + dato.getString("usuario");
        caudal=(EditText) findViewById(R.id.precio);
        precio=(EditText) findViewById(R.id.litros);

        Siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(caudal.getText().equals("")&&precio.getText().equals("")){
                    Toast.makeText(Principal.this, "Ingrese Datos!", Toast.LENGTH_SHORT).show();
                }else{
                    caudal_txt=caudal.getText().toString();
                    precio_txt=precio.getText().toString();
                    Intent intent = new Intent (v.getContext(), DispositivosBT.class);
                    intent.putExtra("caudal",caudal_txt);
                    intent.putExtra("precio",precio_txt);
                    intent.putExtra("usuario",usuario);
                    startActivityForResult(intent, 0);

                }


            }
        });




    }

}
