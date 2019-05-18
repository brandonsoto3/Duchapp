package com.example.brandonsoto.duchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.brandonsoto.duchapp.Entidades.VolleySingleton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class login_official extends AppCompatActivity
        implements Response.Listener<JSONObject>, Response.ErrorListener{

    EditText txtEmail, txtPassword;
    Button btnLogin;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_official);

        // Capturando los EditTexts
        txtEmail = findViewById(R.id.txt_loginUsuario);
        txtPassword = findViewById(R.id.txt_loginPass);

        // Configuracion del Boton del Login
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loguear();
            }
        });


    }

    private void loguear() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // Muestra un dialogo
//        progressDialog = new ProgressDialog(login_official.this);
//        progressDialog.setMessage("Iniciando Sesion....");
//        progressDialog.show();

        String URL = getString(R.string.URL_DUCHAPP) + "/auth/login";

        // Estructurando cuerpo del json
        Map<String, String> parametros = new HashMap<>();
        parametros.put("email", email);
        parametros.put("password", password);

        JSONObject json = new JSONObject(parametros);
        Log.i("JSON", json.toString());

        // Construyendo peticion
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, json, this, this
        );

        // Realizando peticion
        VolleySingleton.getInstancia(this).addToRequestQueue(jsonObjectRequest);
    }

    public void onClick(View v) {
        Intent intent = new Intent (v.getContext(), Register.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onResponse(JSONObject response) {
//        if (progressDialog != null) progressDialog.dismiss();
        mostrarMensaje(response.optString("mensaje"));
        txtPassword.setText("");
        txtEmail.setText("");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        if (progressDialog != null) progressDialog.dismiss();
        try {
            String json = new String(
                    error.networkResponse.data,
                    HttpHeaderParser.parseCharset(
                            error.networkResponse.headers
                    ));
            mostrarMensaje("Error al iniciar sesion: " + json);
        } catch (UnsupportedEncodingException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    /**
     * Metodo que muestra un mensaje en pantalla
     * @param mensaje - cadena a mostrar en pantalla
     */
    private void mostrarMensaje(String mensaje) {
        Toast.makeText(login_official.this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
