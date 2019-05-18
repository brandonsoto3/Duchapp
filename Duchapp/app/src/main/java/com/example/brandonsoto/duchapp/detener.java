package com.example.brandonsoto.duchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class detener extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    mTextMessage.setText("Prepara tu baño");
                    Intent intent1 = new Intent (detener.this, MainActivity.class);
                    startActivityForResult(intent1, 0);
                    return true;
                case R.id.detener:
                    //mTextMessage.setText("Detener");
                    Intent intent2 = new Intent (detener.this, detener.class);
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
        setContentView(R.layout.activity_detener);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
