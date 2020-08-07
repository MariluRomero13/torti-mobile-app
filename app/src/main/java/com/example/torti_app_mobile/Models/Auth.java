package com.example.torti_app_mobile.Models;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Classes.Enviroment;

import org.json.JSONException;
import org.json.JSONObject;

public class Auth {
    String type;
    String token;
    String refreshToken;

    public Auth(String type, String token, String refreshToken) {
        this.type = type;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void login (String email, String password) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest json = new JsonObjectRequest(
                Request.Method.POST,
                Enviroment.api_url + "/login",
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG::LOGIN", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG::LOGIN-ERROR", error.toString());
            }
        });
    }
}
