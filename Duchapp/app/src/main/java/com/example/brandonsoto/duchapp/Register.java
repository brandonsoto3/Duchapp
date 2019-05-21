package com.example.brandonsoto.duchapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.brandonsoto.duchapp.Entidades.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity
        implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText txtNombre, txtEmail, txtPassword, txtConfirmarPassword;
    Button btnRegistrar;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Capturando campos
        txtNombre = findViewById(R.id.txtNombre);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmarPassword = findViewById(R.id.txtConfirmarPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    /**
     * Manda una peticion POST para crear un nuevo usuario
     */
    private void registrar() {
        String nombre = txtNombre.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmarPassword = txtConfirmarPassword.getText().toString();

        // Muestra un dialogo
        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setMessage("Registrando....");
        progressDialog.show();

        if (!password.equals(confirmarPassword)) {
            cerrarDialog();
            mostrarMensaje("Error la contraseña y la confirmacion no coinciden.");
            txtConfirmarPassword.setText("");
            return;
        }

        // Se realiza la peticion
        String URL = getString(R.string.URL_DUCHAPP) + "/auth/registro";

        // Estructurando cuerpo del json
        Map<String, String> parametros = new HashMap<>();
        parametros.put("nombre", nombre);
        parametros.put("email", email);
        parametros.put("password", password);

        JSONObject json = new JSONObject(parametros);

        // Construyendo peticion
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, json, this, this
        );

        // Realizando peticion
        VolleySingleton.getInstancia(Register.this).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Metodo que responde a una peticion correcta
     * @param response - Respuesta
     */
    @Override
    public void onResponse(JSONObject response) {
        cerrarDialog();
        mostrarMensaje("Se ha registrado exitosamente");
        txtNombre.setText("");
        txtPassword.setText("");
        txtEmail.setText("");
        txtConfirmarPassword.setText("");
    }

    /**
     * Metodo que responde ante un error al realizar la peticion solicitada
     * @param error - Error en la peticion
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        cerrarDialog();
        mostrarMensaje("No se ha podido registrar: " + error);
        Log.e("ERROR", error.toString());
    }

    /**
     * Metodo que muestra un mensaje en pantalla
     * @param mensaje - cadena a mostrar en pantalla
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(Register.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void cerrarDialog() {
        if (progressDialog != null) {
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            progressDialog.dismiss();
                        }
                    });
                }
            }).start();
        }
    }
}
