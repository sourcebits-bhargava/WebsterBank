package com.sourcebits.webster.websterbank;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends Activity implements View.OnClickListener {
    // User name
    EditText mUsername;
    // Password
    EditText mPassword;
    // Sign In
    Button mSignIn;
    // Message
    TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_Signin);
        mSignIn = (Button) findViewById(R.id.SignIn);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignIn:
                mUsername = (EditText) findViewById(R.id.username_Et);
                mPassword = (EditText) findViewById(R.id.password_Et);

                String uname = mUsername.getText().toString();
                String pass = mPassword.getText().toString();
                if (uname.equals("") || uname == null) {
                    Toast.makeText(getApplicationContext(), "Username Empty", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("") || pass == null) {
                    Toast.makeText(getApplicationContext(), "Password Empty", Toast.LENGTH_SHORT).show();
                } else {
                    boolean validLogin = validateLogin(uname, pass, SignIn.this);
                    if (validLogin) {
                        System.out.println("In Valid");
                        Intent i = new Intent(SignIn.this, SavingsActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
                break;

            case R.id.buttonNewUser:
                Intent i = new Intent(SignIn.this, NewUserActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.buttonShowAll:
                Intent i_admin = new Intent(SignIn.this, AdminPage.class);
                startActivity(i_admin);
                finish();
                break;
        }


    }

    public boolean validateLogin(String uname, String pass, Context context) {

        mydb = new DbHelper(context);
        SQLiteDatabase db = mydb.getReadableDatabase();
        //SELECT
        String[] columns = {"_id"};

        //WHERE clause
        String selection = "username=? AND password=?";

        //WHERE clause arguments
        String[] selectionArgs = {uname, pass};

        Cursor cursor = null;
        try {
            //SELECT _id FROM login WHERE username=uname AND password=pass
            cursor = db.query(DbHelper.WEBSTER_TABLE_NAME, columns, selection, selectionArgs, null, null, null);

            startManagingCursor(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int numberOfRows = cursor.getCount();

        if (numberOfRows <= 0) {

            Toast.makeText(getApplicationContext(), "Login Failed..\nTry Again", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        mydb.close();
    }
}

