package com.example.brandonsoto.duchapp;

import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.brandonsoto.*;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements Response.Listener<JSONObject>, Response.ErrorListener {
    //******************AGREGADO******************
    //1)
    //Button IdEncender, IdApagar,IdDesconectar;
    //TextView IdBufferIn;
    //-------------------------------------------
    Handler bluetoothIn;
    final int handlerState = 0;
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder DataStringIN = new StringBuilder();
    private ConnectedThread MyConexionBT;
    // Identificador unico de servicio - SPP UUID
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // String para la direccion MAC
    private static String address = null;
    //-------------------------------------------
    //*********************************************

    private TextView mTextMessage;
    private TextView mTextTime;
    private Button buttonInicio,conectar,desconectar;
    Spinner spinner;
    String time;
    String progressbar;
    int inicio = 0;
    Bundle dato;
    ProgressDialog progressDialog;
    JsonObjectRequest jsonObjectRequest;
    int cancion = 0;
    double tiempo = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    mTextMessage.setText("Prepara tu baño");
                    if (inicio == 0) {
                        Toast.makeText(MainActivity.this, "Seleccione una cancion!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, detener.class);
                        intent.putExtra("tiempo", time);
                        intent.putExtra("progressbar", progressbar);
                        startActivity(intent);
                        enviarDatos();
                    }

                    return true;
                case R.id.detener:
                    MyConexionBT.write("0");

                    return true;
                case R.id.estadisticas:
                    Intent intentA = new Intent(MainActivity.this, estadisticas.class);
                    startActivity(intentA);
                    return true;
                case R.id.time:
                    Intent intentB = new Intent(MainActivity.this, top_tiempos.class);
                    startActivity(intentB);
                    return true;
                case R.id.settings:
                    Intent intentC = new Intent(MainActivity.this, ajustes.class);
                    startActivity(intentC);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //******************AGREGADO***********************
        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    DataStringIN.append(readMessage);

                    int endOfLineIndex = DataStringIN.indexOf("#");

                    if (endOfLineIndex > 0) {
                        String dataInPrint = DataStringIN.substring(0, endOfLineIndex);
                        //IdBufferIn.setText("Dato: " + dataInPrint);//<-<- PARTE A MODIFICAR >->->
                        DataStringIN.delete(0, DataStringIN.length());
                    }
                }
            }
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter(); // get Bluetooth adapter
        VerificarEstadoBT();
        //*************************************************

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextTime = (TextView) findViewById(R.id.text_time);
        buttonInicio = (Button) findViewById(R.id.button);
        conectar = (Button) findViewById(R.id.conectar);
        desconectar = (Button) findViewById(R.id.desconectar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.play);
        //navigation.findViewById(R.id.detener).setEnabled(false);
        mTextMessage.setText("Prepara tu baño");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        spinner = (Spinner) findViewById(R.id.spinner);
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
                        mTextTime.setText("00:50");
                        progressbar = "50";
                        time = "00:50";
                        inicio = 1;
                        cancion = 1;
                        tiempo = 50;
                        break;
                    case 2:
                        mTextTime.setText("05:00");
                        progressbar = "300";
                        time = "05:00";
                        inicio = 1;
                        cancion = 2;
                        tiempo = 300;
                        break;
                    case 3:
                        mTextTime.setText("00:20");
                        progressbar = "20";
                        time = "00:20";
                        inicio = 1;
                        cancion = 3;
                        tiempo = 20;
                        break;
                    default:
                        time = "00:00";
                        inicio = 0;
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
                if (inicio == 0) {
                    Toast.makeText(MainActivity.this, "Seleccione una cancion!", Toast.LENGTH_SHORT).show();
                } else {
                    MyConexionBT.write(cancion+"");
                    String dir=getIntent().getExtras().getString("direccion");
                    Intent intent = new Intent(MainActivity.this, detener.class);
                    intent.putExtra("tiempo", time);
                    intent.putExtra("progressbar", progressbar);
                    intent.putExtra("direccion", dir);
                    startActivity(intent);
                    enviarDatos();
                }

            }
        });



        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        desconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    private void enviarDatos() {
        dato = getIntent().getExtras();
        String usuario = "" + dato.getString("usuario");
        mostrarMensaje(usuario);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String URL = getString(R.string.URL_DUCHAPP) + "/ducha/crear";
        Map<String, Object> parametros = new HashMap<>();
        String caudal=getIntent().getExtras().getString("caudal");
        String precio=getIntent().getExtras().getString("precio");
        parametros.put("cantidad_agua",caudal);
        parametros.put("tiempo", tiempo);
        parametros.put("dinero_ahorrado",precio);
        parametros.put("numero_cancion", cancion);
        parametros.put("email", usuario);

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
     *
     * @param response - Error en la peticion
     */
    @Override
    public void onResponse(JSONObject response) {
        cerrarDialog();
        mostrarMensaje("Se ha registrado exitosamente");
    }

    /**
     * Metodo que responde ante un error al realizar la peticion solicitada
     *
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
            mostrarMensaje("ERROR: Envio de datos... " + json);
        } catch (UnsupportedEncodingException e) {
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
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                }
            }).start();
        }
    }

    //*******************AGREGADO*********************
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException
    {
        //crea un conexion de salida segura para el dispositivo
        //usando el servicio UUID
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }
    //************************************************

    //*******************AGREGADO*********************
    @Override
    public void onResume()
    {
        super.onResume();
        //Consigue la direccion MAC desde DeviceListActivity via intent
        Intent intent = getIntent();
        //Consigue la direccion MAC desde DeviceListActivity via EXTRA
        address = intent.getStringExtra(DispositivosBT.EXTRA_DEVICE_ADDRESS);//<-<- PARTE A MODIFICAR >->->
        //Setea la direccion MAC
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try
        {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
        // Establece la conexión con el socket Bluetooth.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {}
        }
        MyConexionBT = new ConnectedThread(btSocket);
        MyConexionBT.start();
    }
    //************************************************


    //*******************AGREGADO*********************
    @Override
    public void onPause()
    {
        super.onPause();
        try
        { // Cuando se sale de la aplicación esta parte permite
            // que no se deje abierto el socket
            btSocket.close();
        } catch (IOException e2) {}
    }
    //************************************************

    //********AGREGADO*******************
    //Comprueba que el dispositivo Bluetooth Bluetooth está disponible y solicita que se active si está desactivado
    private void VerificarEstadoBT() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    //***********************************

    //*******AGREGADO********************
    private class ConnectedThread extends Thread
    {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket)
        {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try
            {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run()
        {
            byte[] buffer = new byte[256];
            int bytes;

            // Se mantiene en modo escucha para determinar el ingreso de datos
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    // Envia los datos obtenidos hacia el evento via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //Envio de trama
        public void write(String input)
        {
            try {
                mmOutStream.write(input.getBytes());
            }
            catch (IOException e)
            {
                //si no es posible enviar datos se cierra la conexión
                Toast.makeText(getBaseContext(), "La Conexión fallo", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    //************************************

}


