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

    public void RealizarPost() {

        String url = "http://duch-app-ace2.herokuapp.com/api/auth/login?email="+usuario.getText().toString()+"&password="+pass.getText().toString();
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        for (int i =0; i < jsonArray.length();i++) {
                            try{
                                JSONObject user = jsonArray.getJSONObject(i);
                                String username = user.getString("email");
                                String password = user.getString("password");

                                Toast.makeText(login_official.this,username+"**",Toast.LENGTH_LONG).show();

                                Toast.makeText(login_official.this,password+"**",Toast.LENGTH_LONG).show();

                                if (usuario.getText().toString().equals("") || pass.getText().toString().equals("")){
                                    Toast.makeText(login_official.this,"Debe ingresar sus datos",Toast.LENGTH_LONG).show();
                                }else if (username.equals(usuario.getText().toString()) && password.equals(pass.getText().toString())){
                                    Toast.makeText(login_official.this,"Datos",Toast.LENGTH_LONG).show();
                                    muser = username;
                                    Intent intent = new Intent(login_official.this,
                                            Principal.class);
                                    intent.putExtra("email",muser);
                                    startActivity(intent);
                                }
                            }catch(JSONException e){
                                Toast.makeText(login_official.this,"Debe ingresar sus datos",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(login_official.this,"Error: "+volleyError.getMessage(),Toast.LENGTH_LONG).show();
                        volleyError.printStackTrace();
                    }
                });



        //Toast.makeText(Login.this,jsonObject.toString(),Toast.LENGTH_LONG).show();
        //Log.e("Rest Response:", jsonObject.toString());
        requestQueue.add(arrayRequest);
    }
}
