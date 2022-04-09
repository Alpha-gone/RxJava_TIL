package com.example.rxandroid.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class LocalVolley {
    private static RequestQueue requestQueue;

    private LocalVolley(){

    }

    public static void init(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue() {
        if(requestQueue != null){
            return requestQueue;
        }else{
            throw new IllegalStateException("Not inited");
        }
    }
}
