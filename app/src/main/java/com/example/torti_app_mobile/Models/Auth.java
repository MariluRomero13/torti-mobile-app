package com.example.torti_app_mobile.Models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.torti_app_mobile.Activities.HomeActivity;
import com.example.torti_app_mobile.Activities.MainActivity;
import com.example.torti_app_mobile.Classes.Enviroment;
import com.example.torti_app_mobile.Classes.VolleyS;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.torti_app_mobile.Classes.Enviroment.api_url;

public class Auth {
    private String type;
    private String token;
    private String refreshToken;
    private static Auth auth;
    public static Boolean HasAuth = true;

    public Auth(String type, String token, String refreshToken) {
        this.type = type;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
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
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = p.edit();
        edit.putString("auth", new Gson().toJson(auth));
        edit.apply();
        Auth.auth = auth;
    }

    private static void clearAuth (Context context) {
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = p.edit();
        edit.remove("auth");
        edit.commit();
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

    static public void resetPassword (String password, String newPassword, final Context context, final Activity activity) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("password", password);
        params.put("new_password", newPassword);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                api_url + "/update-password", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG:ResetPassword", response.toString());
                        Toast.makeText(context, "La contraseña se ha cambiado exitosamente", Toast.LENGTH_SHORT).show();
                        activity.finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG:ResetPassword-error", error.toString());
                Toast.makeText(context, "Verifica la contraseña actual", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "bearer " +
                        Auth.getAuth(context).getToken());
                return headers;
            }
        };
        VolleyS.getInstance(context).getQueue().add(request);
    }

    static public void logout (final Context context, final Activity activity) throws JSONException {
        JSONObject params = new JSONObject();
        params.put("refresh_token", Auth.getAuth(context).getRefreshToken());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                api_url + "/logout", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG:Logout", response.toString());
                        Auth.clearAuth(context);
                        HasAuth = false;
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                        activity.finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG:Logout-error", error.toString());
            }
        });
        VolleyS.getInstance(context).getQueue().add(request);
    }
}
