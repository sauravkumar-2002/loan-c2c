package com.example.loan_c2c.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loan_c2c.R;

import org.json.JSONException;
import org.json.JSONObject;

public class loginScreen extends AppCompatActivity {

    EditText username,password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        username=findViewById(R.id.loginEmail);
        password=findViewById(R.id.loginPassword);
        login=findViewById(R.id.button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataUsingVolley(username.getText().toString(), password.getText().toString());
            }
        });

    }

    private void postDataUsingVolley(String name, String pass) {

        String url="https://loan-app1.herokuapp.com/api/loan/auth/login";
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                username.setText("");
                password.setText("");

                JSONObject respObj = null;
                try {
                    respObj = new JSONObject(response);
                    String msg = respObj.getString("msg");
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    //  String p=respObj.getString("password");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
           queue.add(request);


    }
}