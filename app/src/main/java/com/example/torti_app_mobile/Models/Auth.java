package com.example.torti_app_mobile.Models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Activities.HomeActivity;
import com.example.torti_app_mobile.Classes.Enviroment;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Auth {
    private String type;
    private String token;
    private String refreshToken;
    private static Auth auth;

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

    public static Auth getAuth(Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        String json = p.getString("auth",null);
        if(json != null){
           Auth.auth = new Gson().fromJson(json, Auth.class);
        }
        return Auth.auth;
    }

    private static void setAuth(Context context, Auth auth) {
        Log.d("TAG:SetAuth", auth.getToken());
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("auth", new Gson().toJson(auth));
        edit.apply();
        Auth.auth = auth;
    }

    static public void login (String email, String password, final Context context) throws JSONException {
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
                        Log.d("TAG:Login", response.toString());
                        setAuth(context, new Gson().fromJson(response.toString(), Auth.class));
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG::Login-error", error.toString());
            }
        });

        VolleyS.getInstance(context).getQueue().add(json);
    }
}
