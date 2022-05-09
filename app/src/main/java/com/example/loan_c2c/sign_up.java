package com.example.loan_c2c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

public class sign_up extends AppCompatActivity {
    String usernamestring, userpimage;
    EditText username, emailSg, passwordSg;
    ProgressBar progressBarS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.username1);

        emailSg = findViewById(R.id.emailsg1);
        passwordSg = findViewById(R.id.passwords1);
        progressBarS = findViewById(R.id.progressBarS);
    }

    public void LogIN(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        String emailtext = emailSg.getText().toString().trim();
        String passwordtext = passwordSg.getText().toString().trim();
        usernamestring = username.getText().toString().trim();
        // ALL THE VALIDATIONS-----


        int check = usernamestring.indexOf('.');
        if (check != -1) {
            this.username.setError("Invalid Format(.)");
            this.username.requestFocus();
            return;
        }

        int check1 = usernamestring.indexOf('$');
        if (check1 != -1) {
            this.username.setError("Invalid Format($)");
            this.username.requestFocus();
            return;
        }

        int check2 = usernamestring.indexOf('[');
        if (check2 != -1) {
            this.username.setError("Invalid Format([)");
            this.username.requestFocus();
            return;
        }

        int check3 = usernamestring.indexOf(']');
        if (check3 != -1) {
            this.username.setError("Invalid Format(])");
            this.username.requestFocus();
            return;
        }

        int check4 = usernamestring.indexOf('#');
        if (check4 != -1) {
            this.username.setError("Invalid Format(#)");
            this.username.requestFocus();
            return;
        }
        int check5 = usernamestring.indexOf('/');
        if (check5 != -1) {
            this.username.setError("Invalid Format(/)");
            this.username.requestFocus();
            return;
        }
        //EMAIL VALIDATIONS--
        if (emailtext.isEmpty()) {
            this.emailSg.setError("Field can't be empty");
            this.emailSg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()) {
            this.emailSg.setError("Please Enter a Valid Email Id");
            this.emailSg.requestFocus();
            return;
        }
        //PASSWORD VALIDATIONS----

        if (passwordtext.isEmpty()) {
            passwordSg.setError("field required");
            passwordSg.requestFocus();
            return;
        }

        if (passwordtext.length() < 6) {
            passwordSg.setError("minimum length of password is 6 leter");
            passwordSg.requestFocus();
            return;
        }
        Intent intent=new Intent(this,profile_signup.class);
        intent.putExtra("email",emailtext);
        intent.putExtra("username",usernamestring);
        intent.putExtra("password",passwordtext);
        startActivity(intent);
    }
}