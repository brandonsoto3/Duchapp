package com.example.brandonsoto.duchapp.Entidades;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleySingleton {

    private static VolleySingleton instancia;
    private RequestQueue requestQueue;
    private static Context contexto;

    private VolleySingleton(Context contexto) {
        VolleySingleton.contexto = contexto;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new VolleySingleton(contexto);
        }
        return instancia;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
