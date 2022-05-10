package com.example.loan_c2c.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.loan_c2c.MainActivity;
import com.example.loan_c2c.R;

import es.dmoral.toasty.Toasty;

public class sign_up extends AppCompatActivity {
    EditText username, email, password;
    ProgressBar progressBar;
    String emailtext, passwordtext, usernamestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.signup_username);
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        progressBar = findViewById(R.id.progressBar1);
    }


    public void Continue(View view) {
        emailtext = email.getText().toString().trim();
        passwordtext = password.getText().toString().trim();
        usernamestring = username.getText().toString().trim();
        if (validate()) {
            progressBar.setVisibility(View.VISIBLE);
            Intent intent = new Intent(sign_up.this, bank_details.class);
            intent.putExtra("email", emailtext);
            intent.putExtra("password", passwordtext);
            intent.putExtra("username", usernamestring);
            startActivity(intent);
        } else
            Toasty.error(this, "Recheck all the Input Fields").show();
    }

    private boolean validate() {
        boolean isCheck = true;

        if (username.getText().toString().isEmpty()) {
            this.username.setError("Field cannot be empty!");
            this.username.requestFocus();
            isCheck = false;
        }
        int check = usernamestring.indexOf('.');
        if (check != -1) {
            this.username.setError("Invalid Format(.)");
            this.username.requestFocus();
            isCheck = false;
        }

        int check1 = usernamestring.indexOf('$');
        if (check1 != -1) {
            this.username.setError("Invalid Format($)");
            this.username.requestFocus();
            isCheck = false;
        }

        int check2 = usernamestring.indexOf('[');
        if (check2 != -1) {
            this.username.setError("Invalid Format([)");
            this.username.requestFocus();
            isCheck = false;
        }

        int check3 = usernamestring.indexOf(']');
        if (check3 != -1) {
            this.username.setError("Invalid Format(])");
            this.username.requestFocus();
            isCheck = false;
        }

        int check4 = usernamestring.indexOf('#');
        if (check4 != -1) {
            this.username.setError("Invalid Format(#)");
            this.username.requestFocus();
            isCheck = false;
        }
        int check5 = usernamestring.indexOf('/');
        if (check5 != -1) {
            this.username.setError("Invalid Format(/)");
            this.username.requestFocus();
            isCheck = false;
        }

        //EMAIL VALIDATIONS--
        if (emailtext.isEmpty()) {
            this.email.setError("Field cannot be empty!");
            this.email.requestFocus();
            isCheck = false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailtext).matches()) {
            this.email.setError("Please Enter a Valid Email Id");
            this.email.requestFocus();
            isCheck = false;
        }
        //PASSWORD VALIDATIONS----

        if (passwordtext.isEmpty()) {
            password.setError("Field cannot be empty!");
            password.requestFocus();
            isCheck = false;
        }

        if (passwordtext.length() < 6) {
            password.setError("Minimum length of password must be 6");
            password.requestFocus();
            isCheck = false;
        }
        return isCheck;
    }

    public void gotoLoginPage(View view) {
        startActivity(new Intent(sign_up.this, login.class));
    }
}