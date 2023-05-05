package com.example.duth;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> user_name, user_email, user_phone, user_stdNo, userName;
    TextView profileName, profileEmail, profileUsername, profileStudentNo, profilePhoneNo;
    TextView bookAppointment;
    Button editProfile, changePassword;
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");

        profileName = findViewById(R.id.profileName);
        profileEmail = findViewById(R.id.profileEmail);
        profileUsername = findViewById(R.id.profileUsername);
        profileStudentNo = findViewById(R.id.profileStudentNo);
        profilePhoneNo = findViewById(R.id.profilePhone);
        bookAppointment = findViewById(R.id.book_app_text);

        userDBHelper = new UserDBHelper(ProfileActivity.this);
        user_name = new ArrayList<>();
        user_email = new ArrayList<>();
        user_phone = new ArrayList<>();
        user_stdNo = new ArrayList<>();
        userName = new ArrayList<>();

        retrieveData();



//
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
//            }
//        });
//
        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AppointmentActivity.class));
            }
        });
//
//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
//            }
//        });
//
    }

    void retrieveData(){
        Cursor cursor = userDBHelper.readUserData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "Could not fetch data at this moment", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                profileName.setText(""+cursor.getString(1));
                profileEmail.setText(""+cursor.getString(2));
                profilePhoneNo.setText(""+cursor.getString(3));
                profileStudentNo.setText(""+cursor.getString(4));
                profileUsername.setText(""+cursor.getString(5));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        inflate menu items
        getMenuInflater().inflate(R.menu.nav_menus, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    on item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemID = item.getItemId();

        if (itemID == R.id.nav_home){
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (itemID == R.id.nav_prof) {
            startActivity(new Intent(ProfileActivity.this, ProfileActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (itemID == R.id.nav_history) {
            Intent intent = new Intent(ProfileActivity.this, HistoryActivity.class);
            startActivity(intent);
        } else if (itemID == R.id.nav_comment) {
            startActivity(new Intent(ProfileActivity.this, CommentActivity.class));
        } else if (itemID == R.id.nav_logout) {
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayDialogBox() {

//        set alert box
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Verify Email");
        builder.setMessage("Please verify your Email to save your data.");

//        open email app
        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
//        create dialog box
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}