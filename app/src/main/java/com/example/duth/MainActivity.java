package com.example.duth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView signupRedirectText, forgotPassword;
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        forgotPassword = findViewById(R.id.forgot_password_link);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login_email = loginEmail.getText().toString();
                String login_password = loginPassword.getText().toString();

                String error = "";
                if (TextUtils.isEmpty(login_email)){
                    error = "Please enter your email address";
                } else if (login_email.equals("adminhealth@gmail.com") && login_password.equals("@adminhealth01")) {
                    Toast.makeText(MainActivity.this, "Logged in as admin", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, AdminDashboard.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(login_email).matches()) {
                    error = "Please enter a valid Email address";
                } else if (TextUtils.isEmpty(login_password)) {
                    error = "Please enter your password";
                }else {
                    if (userDBHelper == null){
                        userDBHelper = new UserDBHelper(MainActivity.this);

                        Boolean isUserPassword = userDBHelper.isUsernamePasswordExist(login_email, login_password);
                        if (isUserPassword){
                            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, MenuActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }else {
                            Toast.makeText(MainActivity.this, "Invalid login details", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_LONG).show();
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

//    isUserAlreadyLoggedIn ? ProfileActivity : LoginActivity

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (firebaseAuth.getCurrentUser() != null){
//            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
//            finish();
//        }else {
//            getApplicationContext();
//        }
//    }
}