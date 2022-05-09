package com.example.loan_c2c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profile_signup extends AppCompatActivity {
    EditText Aadhar_no,Pan_no,Bank_no;
    String Aadhar_no_string,Pan_no_string,Bank_no_string;
    String username,email,password;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_signup);
         username=getIntent().getStringExtra("username");
         email=getIntent().getStringExtra("email");
         password=getIntent().getStringExtra("password");
         Aadhar_no=findViewById((R.id.aadhar_no));
         Pan_no=findViewById((R.id.pan_no));
         Bank_no=findViewById((R.id.bank_no_));
         pb=findViewById(R.id.progressBarS);
         Aadhar_no_string=Aadhar_no.getText().toString().trim();
         Bank_no_string=Bank_no.getText().toString().trim();
         Pan_no_string=Pan_no.getText().toString().trim();
    }

    public void Register(View view) {
        validate_Aadhar();
        validate_pan();
        postDataUsingVolley(email,password,username,Aadhar_no_string,Bank_no_string,Pan_no_string);
    }

    private void postDataUsingVolley(String semail,String spassword,String susername,String sAadhar,String sBank,String sPan) {
    String url="https://loan-app1.herokuapp.com/api/loan/auth/register";
    pb.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(profile_signup.this);
        StringRequest request=new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pb.setVisibility(View.GONE);
                Aadhar_no.setText("");
                Bank_no.setText("");
                Pan_no.setText("");
                Toast.makeText(profile_signup.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject respObj = new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(profile_signup.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("account_no", sBank);
                params.put("aadhar_number", sAadhar);
                params.put("pan_number", sPan);
                params.put("email", semail);
                params.put( "password", spassword);
                params.put("username", susername);
                params.put("ifsc", "00");
              //  params.put("avatar", "00");

                // at last we are
                // returning our params.
                return params;
            }
        };
        queue.add(request);
        }


    private void validate_pan() {

    }

    private void validate_Aadhar() {
boolean res=verhoeff.validateVerhoeff(Aadhar_no_string);
String msg=String.valueOf(res);
if(msg!="true"){
    Aadhar_no.setError("Enter a Valid Aadhar No.");
    Aadhar_no.requestFocus();
    return;
}
else{
    Toast.makeText(this, "true", Toast.LENGTH_SHORT).show();
}
    }

    public void profchange(View view) {
    }
}