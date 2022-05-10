package com.example.loan_c2c.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loan_c2c.MainActivity;
import com.example.loan_c2c.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class login extends AppCompatActivity {

    EditText username, password;
    Button login;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
        if (pref.contains("token")) {
            startActivity(new Intent(login.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        login = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    progressBar.setVisibility(View.VISIBLE);
                    postDataUsingVolley(username.getText().toString(), password.getText().toString());
                }
            }
        });

    }

    private boolean validate() {
        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            Toasty.error(this, "Please enter all the fields").show();
            return false;
        }
        return true;
    }

    private void postDataUsingVolley(String name, String pass) {

        String url = "https://loan-app3.herokuapp.com/api/loan/auth/login";
        HashMap<String, String> params = new HashMap<>();
        params.put("username", name);
        params.put("password", pass);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    if (response.getBoolean("success")) {
                        String token = response.getString("token");
                        username.setText("");
                        password.setText("");

                        SharedPreferences sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.apply();
                        startActivity(new Intent(login.this, MainActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toasty.error(login.this, obj.getString("msg")).show();
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return params;
            }
        };

        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject respObj = null;
//                try {
//                    respObj = new JSONObject(response);
//                    String msg = respObj.getString("msg");
//                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//                    //  String p=respObj.getString("password");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//        queue.add(request);


    }

    public void gotoRegisterPage(View view) {
        startActivity(new Intent(login.this, sign_up.class));
    }
}