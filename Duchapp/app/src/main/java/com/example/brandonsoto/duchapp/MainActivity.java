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
        mTextTime= (TextView) findViewById(R.id.text_time);
        buttonInicio=(Button) findViewById(R.id.btn_inicio);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.play);
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
                        break;
                    case 2:
                        mTextTime.setText("02:00");
                        break;
                    case 3:
                        mTextTime.setText("01:50");
                        break;
                    default:
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
                Intent intent = new Intent (v.getContext(), detener.class);
                startActivityForResult(intent, 0);
            }
        });



    }





}


