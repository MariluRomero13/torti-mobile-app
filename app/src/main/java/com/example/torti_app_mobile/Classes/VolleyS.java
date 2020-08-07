package com.example.torti_app_mobile.Classes;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyS {
    private static VolleyS volley = null;

    private RequestQueue Queue;

    private  VolleyS(Context context) {Queue = Volley.newRequestQueue(context);}

    public static VolleyS getInstance(Context context){
        if(volley == null)
        {
            volley = new VolleyS(context);
        }
        return volley;
    }

    public RequestQueue getQueue() { return Queue; }
}
