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

public class SignUpActivity extends AppCompatActivity {

    EditText signupName, signupUsername, signupEmail, phoneNumber, studentNumber, signupPassword;
    TextView loginRedirectText;
    Button signupButton, verifyButton;
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign up");

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        phoneNumber = findViewById(R.id.phone_no);
        studentNumber = findViewById(R.id.student_no);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signupButton = findViewById(R.id.signup_button);
        verifyButton = findViewById(R.id.verify_button);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = signupName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String phoneNo = phoneNumber.getText().toString().trim();
                String studentNo = studentNumber.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();

//                check if all fields are filled
                String error = "";
                if (TextUtils.isEmpty(name)){
                    error = "Name is required";
                } else if (TextUtils.isEmpty(email)) {
                    error = "Email address is required";
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    error = "Please enter a valid Email address";
                } else if (TextUtils.isEmpty(phoneNo)) {
                    error = "Phone number is required";
                } else if (phoneNo.length() != 10) {
                    error = "Phone number must be 10 digit long";
                } else if (TextUtils.isEmpty(studentNo)) {
                    error = "Student number is required";
                } else if (studentNo.length() != 8) {
                    error = "Student number must be 8 characters long";
                } else if (TextUtils.isEmpty(username)) {
                    error = "Username is required";
                } else if (TextUtils.isEmpty(password)) {
                    error = "Password is required";
                } else if (password.length() < 8) {
                    error = "Password must be at least 8 characters long";
                }else {
                    userDBHelper = new UserDBHelper(SignUpActivity.this);
                    userDBHelper.addNewUser(name, email, phoneNo, studentNo, username, password);
                    Toast.makeText(SignUpActivity.this, "Registration complete", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                }

                Toast.makeText(SignUpActivity.this, ""+ error, Toast.LENGTH_SHORT).show();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

//    private void verifyEmail() {
//
//        // generate a 4 long random number
//        Random randomCode = new Random();
//        verificationCode = randomCode.nextInt(8999) + 1000;
//
//        String url = "https://verifyemail.infinityfreeapp.com/emailVerification.php";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
//                findViewById(R.id.signup_card).setVisibility(View.GONE);
//                findViewById(R.id.verify_card).setVisibility(View.VISIBLE);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SignUpActivity.this, ""+error, Toast.LENGTH_LONG).show();
//                Log.e("", error.toString());
//            }
//        }){
//            @NonNull
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> mapParams = new HashMap<>();
//                mapParams.put("email", signupEmail.getText().toString());
//                mapParams.put("code", String.valueOf(verificationCode));
//                return mapParams;
//            }
//        };
//        requestQueue.add(stringRequest);
//    }
}