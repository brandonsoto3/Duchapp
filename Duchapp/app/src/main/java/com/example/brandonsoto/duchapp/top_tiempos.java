package com.example.brandonsoto.duchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class top_tiempos extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.play:
                    Intent intent1 = new Intent (top_tiempos.this, MainActivity.class);
                    startActivityForResult(intent1, 0);
                    return true;

                case R.id.estadisticas:
                    Intent intent3 = new Intent (top_tiempos.this, estadisticas.class);
                    startActivityForResult(intent3, 0);
                    return true;
                case R.id.time:
                    Intent intent4 = new Intent (top_tiempos.this, top_tiempos.class);
                    startActivityForResult(intent4, 0);
                    return true;
                case R.id.settings:
                    Intent intent5 = new Intent (top_tiempos.this, ajustes.class);
                    startActivityForResult(intent5, 0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_tiempos);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.time);
        navigation.findViewById(R.id.detener).setEnabled(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
