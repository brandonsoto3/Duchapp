package com.example.brandonsoto.duchapp;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class login_official extends AppCompatActivity {

    private EditText usuario, pass;
    public String usuario2, pass2;
    public static String muser = "";
    RequestQueue requestQueue;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_official);
        usuario = (EditText) findViewById(R.id.txt_loginUsuario);
        pass = (EditText) findViewById(R.id.txt_loginPass);

        usuario2 = usuario.getText().toString();
        pass2 = pass.getText().toString();

        btnLogin = (Button)findViewById(R.id.btnLogin);

        requestQueue = Volley.newRequestQueue(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(login_official.this,usuario.getText().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText(login_official.this,pass.getText().toString(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent (v.getContext(), Principal.class);
                startActivityForResult(intent, 0);
               //RealizarPost();
            }
        });

    }

    private void loguear() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        // Muestra un dialogo
        progressDialog = new ProgressDialog(login_official.this);
        progressDialog.setMessage("Iniciando Sesion....");
        progressDialog.show();

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
        cerrarDialog();
        mostrarMensaje(response.optString("mensaje"));
        Intent intent = new Intent (login_official.this, MainActivity.class);
        intent.putExtra("usuario",txtEmail.getText().toString());
        txtPassword.setText("");
        txtEmail.setText("");
        startActivity(intent);
    }



        //Toast.makeText(Login.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
        //Log.e("Rest Response:", jsonObject.toString());
        requestQueue.add(arrayRequest);
    }
}
