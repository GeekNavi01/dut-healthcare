package com.example.duth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    Context context;

//  database name
    private static final String DB_NAME = "duthealth.db";

//    database version
    private static final int DB_VERSION = 1;

    //    database fields for users table
    private static final String TABLE_NAME = "users";
    private static final String ID_COL = "user_id";
    private static final String FULLNAME_COL = "fullname";
    private static final String EMAIL_COL = "email";
    private static final String PHONENO_COL = "phone_number";
    private static final String STUDENTNO_COL = "student_number";
    private static final String USERNAME_COL = "username";
    private static final String PASSWORD_COL = "password";

//    database fields for appointments table
    private static final String TABLE_NAME1 = "appointments";
    private static final String ID_COL1 = "id";
    private static final String SERVICE_COL1 = "service";
    private static final String VISIT_REASON_COL1 = "reason";
    private static final String SYMPTOMS_COL1 = "symptoms";
    private static final String DATE_COL1 = "app_date";
    private static final String TIME_COL1 = "app_time";

    // creating a constructor for our database handler.
    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public UserDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//        create users table
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + FULLNAME_COL + " TEXT NOT NULL, "
                + EMAIL_COL + " TEXT UNIQUE NOT NULL, "
                + PHONENO_COL + " TEXT UNIQUE NOT NULL, "
                + STUDENTNO_COL + " TEXT UNIQUE NOT NULL, "
                + USERNAME_COL + " TEXT UNIQUE NOT NULL, "
                + PASSWORD_COL + " TEXT NOT NULL);";

        //        create appointment table
        String query1 = "CREATE TABLE " + TABLE_NAME1 + " ("
                + ID_COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + SERVICE_COL1 + " TEXT NOT NULL, "
                + SYMPTOMS_COL1 + " TEXT NOT NULL, "
                + DATE_COL1 + " TEXT NOT NULL, "
                + TIME_COL1 + " TEXT UNIQUE NOT NULL);";

        db.execSQL(query);
        db.execSQL(query1);
    }

//    create addNewUser method
    public boolean addNewUser(String fullName, String userEmail, String phoneNo, String studentNo, String username, String password) {

//        add/put data into our database
        SQLiteDatabase db = this.getWritableDatabase();

//        variable to store our set of values
        ContentValues values = new ContentValues();

//        passing all values with their keys and value-pair
        values.put(FULLNAME_COL, fullName);
        values.put(EMAIL_COL, userEmail);
        values.put(PHONENO_COL, phoneNo);
        values.put(STUDENTNO_COL, studentNo);
        values.put(USERNAME_COL, username);
        values.put(PASSWORD_COL, password);

//        insert our values into a database
        long users = db.insert(TABLE_NAME, null, values);

        if (users == -1){
            return true;
        }else {
            return false;
        }
    }

//    create addNweAppointment method

//    public Boolean addNewAppointment(String blood_type, String service, String visit_reason, String symptoms, String appDate, String appTime)
    public Boolean addNewAppointment(String service, String symptoms, String appDate, String appTime) {

        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues values1 = new ContentValues();

        values1.put(SERVICE_COL1, service);
//        values1.put(VISIT_REASON_COL1, visit_reason);
        values1.put(SYMPTOMS_COL1, symptoms);
        values1.put(DATE_COL1, appDate);
        values1.put(TIME_COL1, appTime);

        long appointment = db1.insert(TABLE_NAME1, null, values1);

        if (appointment == -1){
            return true;
        }else {
            return false;
        }
    }

    public Boolean isUsernameExist(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where username=?", new String[] {username});

        return cursor.getCount() > 0;
    }

    public Boolean isUsernamePasswordExist(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where email=? and password=?", new String[] {email, password});

        return cursor.getCount() > 0;
    }

//    create readUserData method
    Cursor readUserData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

//    create readAllAppointments method
    Cursor readAllAppointmentData(){
        String query1 = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor cursor1 = null;
        if(db1 != null){
            cursor1 = db1.rawQuery(query1, null);
        }
        return cursor1;
    }

//    admin appointments
Cursor adminReadAllAppointmentData(){
    String query1 = "SELECT * FROM " + TABLE_NAME1;
    SQLiteDatabase db1 = this.getReadableDatabase();

    Cursor cursor1 = null;
    if(db1 != null){
        cursor1 = db1.rawQuery(query1, null);
    }
    return cursor1;
}


//    create updateUser method
    public void updateUser(String currentFullName, String currentEmail, String currentPhone,
                             String currentStudentNo, String currentUsername, String currentPassword) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FULLNAME_COL, currentFullName);
        values.put(EMAIL_COL, currentEmail);
        values.put(PHONENO_COL, currentPhone);
        values.put(STUDENTNO_COL, currentStudentNo);
        values.put(USERNAME_COL, currentUsername);
        values.put(PASSWORD_COL, currentPassword);

//        update the table in our database
        db.update(TABLE_NAME, values, "username=?", new String[]{currentUsername});
        db.close();
    }

//      create updateAppointment method
//    void updateAppointment(String id, String service, String blood_type, String reason, String symptoms, String set_date, String set_time)
    void updateAppointment(String id, String service, String symptoms, String set_date, String set_time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_COL1, service);
//        values.put(VISIT_REASON_COL1, reason);
        values.put(SYMPTOMS_COL1, symptoms);
        values.put(DATE_COL1, set_date);
        values.put(TIME_COL1, set_time);

        long result = db.update(TABLE_NAME1, values, "id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Could not update appointment", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Appointment updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

//    create deleteUser method
    public void deleteUser(String username) {

        SQLiteDatabase db = this.getWritableDatabase();

//        delete user from our database
        db.delete(TABLE_NAME, "username=?", new String[]{username});
        db.close();
    }

//    create deleteAppointment method
    public void deleteAppointment() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME1);

//        long deleteApp = db.delete(TABLE_NAME, "id=?", new String[]{id});
//        if (deleteApp == -1){
//            Toast.makeText(context, "Failed to delete appointment", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(context, "Appointment deleted", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }
}
