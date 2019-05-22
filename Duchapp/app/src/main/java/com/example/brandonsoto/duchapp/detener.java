package com.example.brandonsoto.duchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class detener extends AppCompatActivity {

    private TextView mTextMessage;
    private Button finalizar;
    private TextView txtC;
    long tiempotranscurrido=0;
    Chronometer chronometer;
    ProgressBar progreso;
    int conteo;
    int bandera=1;
    int stop=0;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    Intent intent1 = new Intent (detener.this, MainActivity.class);
                    startActivityForResult(intent1, 0);
                    return true;
                case R.id.detener:
                    //mTextMessage.setText("Detener");

                    if (bandera==1) {
                        chronometer.stop();
                        tiempotranscurrido=chronometer.getBase()-SystemClock.elapsedRealtime();    // The toggle is disabled
                        bandera=0;
                        stop=1;
                    } else  if (bandera==0) {
                        chronometer.setBase(SystemClock.elapsedRealtime()+tiempotranscurrido);
                        chronometer.start();
                        bandera=1;
                        stop=0;
                    }


                    return true;
                case R.id.estadisticas:
                    Intent intent3 = new Intent (detener.this, estadisticas.class);
                    startActivityForResult(intent3, 0);
                    return true;
                case R.id.time:
                    Intent intent4 = new Intent (detener.this, top_tiempos.class);
                    startActivityForResult(intent4, 0);
                    return true;
                case R.id.settings:
                    Intent intent5 = new Intent (detener.this, ajustes.class);
                    startActivityForResult(intent5, 0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detener);

        mTextMessage = (TextView) findViewById(R.id.message);
        finalizar=(Button)findViewById(R.id.finalizar);
        txtC = (TextView) findViewById(R.id.txt_crono);
        progreso=(ProgressBar)findViewById(R.id.progressbar);
        String valorprogress=getIntent().getExtras().getString("progressbar");
        final int resultprogress = Integer.parseInt(valorprogress)*10000;
        conteo=resultprogress+(int)tiempotranscurrido;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //
        navigation.setSelectedItemId(R.id.detener);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        chronometer = (Chronometer)findViewById(R.id.chronometerExample);
        chronometer.setBase(SystemClock.elapsedRealtime()+tiempotranscurrido);
        chronometer.start();
        stop=0;


        /*Button buttonStart = (Button)findViewById(R.id.buttonStartChronometer);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime()+tiempotranscurrido);
                chronometer.start();

            }
        });

        Button buttonStop = (Button)findViewById(R.id.buttonStopChronometer);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
                tiempotranscurrido=chronometer.getBase()-SystemClock.elapsedRealtime();
            }
        });

      Button buttonRestart = (Button)findViewById(R.id.buttonRestartChronometer);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long systemCurrTime = SystemClock.elapsedRealtime();

                chronometer.setBase(systemCurrTime);

            }
        });*/

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), login_official.class);
                //intent.putExtra("tiempo",time);
                // intent.putExtra("progressbar",progressbar);
                startActivity(intent);


                //ESTE ES EL BOTON FINAL

            }
        });


        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long systemCurrTime = SystemClock.elapsedRealtime();
                long chronometerBaseTime = chronometer.getBase();
                long deltaTime = systemCurrTime - chronometerBaseTime;


                    String valor_recibido=getIntent().getExtras().getString("tiempo");
                    if(chronometer.getText().toString().equals(valor_recibido)){
                        Toast.makeText(detener.this, "Se acabo su tiempo!", Toast.LENGTH_SHORT).show();
                        chronometer.stop();
                        tiempotranscurrido=chronometer.getBase()-SystemClock.elapsedRealtime();
                        //finalizar.setVisibility(View.VISIBLE);
                    }


                    if(stop==0){
                        progreso.setProgress(map(conteo,0,resultprogress,0,100));
                        conteo=conteo-10000;
                    }



                    //chronometer.setBase(systemCurrTime);

            }


        });

    }

    public int map(int x, int in_min, int in_max,int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }


}
