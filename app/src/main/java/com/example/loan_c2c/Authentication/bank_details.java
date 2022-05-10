package com.example.loan_c2c.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loan_c2c.MainActivity;
import com.example.loan_c2c.R;
import com.example.loan_c2c.verhoeff;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class bank_details extends AppCompatActivity {
    EditText Aadhar_no, Pan_no, Account_no, IFSC_Code;
    String Aadhar_no_string, Pan_no_string, Bank_no_string;
    String username, email, password;
    ProgressBar pb;
    String ifsc_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        Aadhar_no = findViewById((R.id.aadhar_no));
        Pan_no = findViewById((R.id.pan_no));
        Account_no = findViewById((R.id.account_no));
        IFSC_Code = findViewById((R.id.ifsc_code));
        pb = findViewById(R.id.progressBar2);
    }

    public void Register(View view) {
        Aadhar_no_string = Aadhar_no.getText().toString().trim();
        Pan_no_string = Pan_no.getText().toString().trim();
        Bank_no_string = Account_no.getText().toString().trim();
        ifsc_string = IFSC_Code.getText().toString().trim();
        if (validate_Aadhar() && validate_pan() && checkFields()) {
            pb.setVisibility(View.VISIBLE);
            postDataUsingVolley(email, password, username, Aadhar_no_string, Bank_no_string, Pan_no_string, ifsc_string);
        }
    }

    private void postDataUsingVolley(String email, String password, String username, String aadhar_no_string, String bank_no_string, String pan_no_string, String ifsc_string) {
        String url = "https://loan-app3.herokuapp.com/api/loan/auth/register";
        Map<String, String> params = new HashMap<String, String>();
        params.put("account_no", bank_no_string);
        params.put("aadhar_number", aadhar_no_string);
        params.put("pan_number", pan_no_string);
        params.put("email", email);
        params.put("password", password);
        params.put("username", username);
        params.put("ifsc", ifsc_string);
        //  params.put("avatar", "00");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pb.setVisibility(View.GONE);
                try {
                    if (response.getBoolean("success")) {
                        String token = response.getString("token");
                        Aadhar_no.setText("");
                        Account_no.setText("");
                        Pan_no.setText("");
                        IFSC_Code.setText("");
                        SharedPreferences sharedPreferences = getSharedPreferences("auth", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.apply();
                        Toasty.success(bank_details.this, "User Registered Successfully!").show();
                        startActivity(new Intent(bank_details.this, MainActivity.class));
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
                        Toasty.error(bank_details.this, obj.getString("msg")).show();
                        pb.setVisibility(View.GONE);
                    } catch (JSONException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                        pb.setVisibility(View.GONE);
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
//        RequestQueue queue = Volley.newRequestQueue(profile_signup.this);
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                pb.setVisibility(View.GONE);
//                Toast.makeText(profile_signup.this, "Data added to API", Toast.LENGTH_SHORT).show();
//                try {
//                    JSONObject respObj = new JSONObject(response);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(profile_signup.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
//            }
//        });
//        queue.add(request);


    }


    private boolean checkFields() {
        if (Aadhar_no_string.isEmpty() || Pan_no_string.isEmpty() || Bank_no_string.isEmpty() || ifsc_string.isEmpty()) {
            Toasty.error(this, "Please fill all the necessary details!").show();
            return false;
        }
        return true;
    }

    private boolean validate_pan() {
        return true;
    }

    private boolean validate_Aadhar() {
        boolean res = verhoeff.validateVerhoeff(Aadhar_no_string);
        String msg = String.valueOf(res);
        if (msg != "true") {
            Aadhar_no.setError("Enter a Valid Aadhar No.");
            Aadhar_no.requestFocus();
            return false;
        }
        return true;
    }

}