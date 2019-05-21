package com.example.brandonsoto.duchapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private TextView mTextTime;
    private Button buttonInicio;
    Spinner spinner;
    String time;
    String progressbar;
    int inicio=0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("SetTextI18n")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:                    
                    mTextMessage.setText("Prepara tu baño");
                    if(inicio==0){
                        Toast.makeText(MainActivity.this, "Seleccione una cancion!", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent (MainActivity.this, detener.class);
                        intent.putExtra("tiempo",time);
                        intent.putExtra("progressbar",progressbar);
                        startActivity(intent);
                        
                    }
                    
                    return true;
                case R.id.detener:
                    //mTextMessage.setText("Detener");
                    Intent intent2 = new Intent (MainActivity.this, detener.class);
                    startActivityForResult(intent2, 0);
                    return true;
                case R.id.estadisticas:
                    Intent intentA = new Intent (MainActivity.this, estadisticas.class);
                    startActivity(intentA);
                    return true;
                case R.id.time:
                    Intent intentB = new Intent (MainActivity.this, top_tiempos.class);
                    startActivity(intentB);
                    return true;
                case R.id.settings:
                    Intent intentC = new Intent (MainActivity.this, ajustes.class);
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

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextTime= (TextView) findViewById(R.id.text_time);
        buttonInicio=(Button) findViewById(R.id.button);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.play);
        navigation.findViewById(R.id.detener).setEnabled(false);
        mTextMessage.setText("Prepara tu baño");
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.array_canciones, android.R.layout.simple_spinner_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                long cancionselected=parent.getItemIdAtPosition(position);
                int selectedsong=(int)cancionselected;



                switch (selectedsong) {
                    case 1:
                        mTextTime.setText("03:00");
                        progressbar="180";
                        time="03:00";
                        inicio=1;
                        break;
                    case 2:
                        mTextTime.setText("02:00");
                        progressbar="120";
                        time="02:00";
                        inicio=1;
                        break;
                    case 3:
                        mTextTime.setText("00:20");
                        progressbar="20";
                        time="00:20";
                        inicio=1;
                        break;
                    default:
                        time="00:00";
                        inicio=0;
                        Toast.makeText(parent.getContext(),"Seleccione una canción!", Toast.LENGTH_SHORT).show();
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
                if(inicio==0){
                    Toast.makeText(MainActivity.this, "Seleccione una cancion!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent (MainActivity.this, detener.class);
                    intent.putExtra("tiempo",time);
                    intent.putExtra("progressbar",progressbar);
                    startActivity(intent);

                }
            }
        });



    }





}


