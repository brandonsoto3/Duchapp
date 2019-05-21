package com.example.brandonsoto.duchapp;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.brandonsoto.*;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.brandonsoto.duchapp.Entidades.VolleySingleton;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements Response.Listener<JSONObject>, Response.ErrorListener{

    private TextView mTextMessage;
    private TextView mTextTime;
    private Button buttonInicio;
    Spinner spinner;
    Bundle dato;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;
    int cancion=0;
    double tiempo=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    mTextMessage.setText("Prepara tu baño");
                    Intent intent1 = new Intent (MainActivity.this, MainActivity.class);
                    startActivityForResult(intent1, 0);
                    return true;
                case R.id.detener:
                    //mTextMessage.setText("Detener");
                    Intent intent2 = new Intent (MainActivity.this, detener.class);
                    startActivityForResult(intent2, 0);
                    return true;
                case R.id.estadisticas:
                    mTextMessage.setText("Estadisticas");
                    return true;
                case R.id.time:
                    mTextMessage.setText("TOP TIEMPOS");
                    return true;
                case R.id.settings:
                    mTextMessage.setText("Ajustes");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextTime = (TextView) findViewById(R.id.text_time);
        buttonInicio = (Button) findViewById(R.id.btn_inicio);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.play);
        mTextMessage.setText("Prepara tu baño");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_canciones, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                long cancionselected = parent.getItemIdAtPosition(position);
                int selectedsong = (int) cancionselected;


                switch (selectedsong) {
                    case 1:
                        mTextTime.setText("03:00");
                        cancion = 1;
                        tiempo = 180;
                        break;
                    case 2:
                        mTextTime.setText("02:00");
                        cancion = 2;
                        tiempo = 120;
                        break;
                    case 3:
                        mTextTime.setText("01:50");
                        cancion = 1;
                        tiempo = 110;
                        break;
                    default:
                        Toast.makeText(parent.getContext(), "Seleccione una canción!", Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buttonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatos();
                /*Intent intent = new Intent(v.getContext(), detener.class);
                startActivityForResult(intent, 0);*/
            }
        });


        mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.play:
                        mTextMessage.setText("Prepara tu baño");
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivityForResult(intent1, 0);
                        return true;
                    case R.id.detener:
                        //mTextMessage.setText("Detener");
                        Intent intent2 = new Intent(MainActivity.this, detener.class);
                        startActivityForResult(intent2, 0);
                        return true;
                    case R.id.estadisticas:
                        mTextMessage.setText("Estadisticas");
                        return true;
                    case R.id.time:
                        mTextMessage.setText("TOP TIEMPOS");
                        return true;
                    case R.id.settings:
                        mTextMessage.setText("Ajustes");
                        return true;
                }
                return false;
            }
        };
    }

    private void enviarDatos(){
        dato = getIntent().getExtras();
        String usuario = ""+dato.getString("usuario");
        mostrarMensaje(usuario);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String URL = getString(R.string.URL_DUCHAPP) + "/ducha/crear";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cantidad_agua",1000);
        parametros.put("tiempo",tiempo);
        parametros.put("dinero_ahorrado",15);
        parametros.put("numero_cancion",cancion);
        parametros.put("email",usuario);

        JSONObject json = new JSONObject(parametros);

        // Construyendo peticion
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, URL, json, this, this
        );

        // Realizando peticion
        VolleySingleton.getInstancia(MainActivity.this).addToRequestQueue(jsonObjectRequest);

    }
    /**
     * Metodo que responde ante un error al realizar la peticion solicitada
     * @param response - Error en la peticion
     */
    @Override
    public void onResponse(JSONObject response) {
        cerrarDialog();
        mostrarMensaje("Se ha registrado exitosamente");
    }

    /**
     * Metodo que responde ante un error al realizar la peticion solicitada
     * @param error - Error en la peticion
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        cerrarDialog();
        /*mostrarMensaje("No se mandaron los datos: " + error);
        Log.e("ERROR", error.toString());*/
        try {
            String json = new String(
                    error.networkResponse.data,
                    HttpHeaderParser.parseCharset(
                            error.networkResponse.headers
                    )
            );
            mostrarMensaje("ERROR: Envio de datos... "+json);
        }catch (UnsupportedEncodingException e){
            Log.e("ERROR", e.getMessage());
        }
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
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

