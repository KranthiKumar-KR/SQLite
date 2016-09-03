package com.example.kranthi.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText userName;
    EditText passWord;
    Button logIn, signUp;
    String password1, username1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        passWord = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.login);
        signUp = (Button) findViewById(R.id.signup);
        final CheckBox rm = (CheckBox) findViewById(R.id.rememberme);
        final Boolean checked = rm.isChecked();



        /*logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String username1 = userName.getText().toString();
                 String  password1 = passWord.getText().toString();
                Toast.makeText(getApplicationContext(),username1 + password1,Toast.LENGTH_LONG).show();
            }
        });*/

        final SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        /*database.execSQL("CREATE TABLE IF NOT EXISTS users (uname TEXT, pword TEXT, UNIQUE(uname, pword) ON CONFLICT IGNORE );");*/
        database.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1, uname VARCHAR UNIQUE, pword VARCHAR)");
       // database.execSQL("INSERT OR REPLACE INTO users(uname, pword) VALUES('default','default')");
        //database.execSQL("CREATE UNIQUE INDEX idx_something ON users (uname, pword)");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = userName.getText().toString();
                password1 = passWord.getText().toString();

                if (username1 != null && !username1.trim().isEmpty() && password1 != null && !password1.trim().isEmpty()) {
                    try {
                        //database.execSQL("INSERT OR REPLACE INTO users(uname, pword) VALUES('"+username1+"', '"+password1+"')");
                        //Toast.makeText(getApplicationContext(), "SignUp successful!!!!", Toast.LENGTH_LONG).show();
                        Cursor c = database.rawQuery("SELECT * FROM users WHERE uname= '" + username1 + "'", null);
                        if (c.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), "USER ALREADY EXITS", Toast.LENGTH_LONG).show();
                        } else {
                            database.execSQL("INSERT OR REPLACE INTO users(uname, pword) VALUES('" + username1 + "', '" + password1 + "')");
                            Toast.makeText(getApplicationContext(), "SignUp successful!!!!", Toast.LENGTH_LONG).show();

                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Toast.makeText(getApplicationContext(), passWord.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter valid username and password", Toast.LENGTH_LONG).show();
                }
            }
        });
        logIn.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         String inputUname = userName.getText().toString(); //get username from login form
                                         String savedUname = null;
                                         String savedPword = null;
                                         Intent intent = new Intent(MainActivity.this, secondscreen.class);
                                         try {
                                             //Cursor c = database.rawQuery("SELECT * FROM users", null);
                                             Cursor c = database.rawQuery("SELECT * FROM users WHERE uname= '" + inputUname + "'", null);
                                             int unameIndex = c.getColumnIndex("uname");
                                             int pwordIndex = c.getColumnIndex("pword");
                                             int idIndex = c.getColumnIndex("id");
                                             c.moveToFirst();
                                             if (c != null) {

                                                 savedUname = c.getString(unameIndex);
                                                 savedPword = c.getString(pwordIndex);
                                                 String savedId = String.valueOf(c.getInt(idIndex));
                                                 Log.i("table content", savedId + "---" + savedUname + ":" + savedPword);

                                                 {

                                                     if (savedUname.equals(userName.getText().toString())) {

                                                         if (savedPword.equals(passWord.getText().toString())) {

                                                             intent.setAction(Intent.ACTION_VIEW);
                                                             startActivity(intent);

                                                         } else {
                                                             Toast.makeText(getApplicationContext(), " Incorrect password ", Toast.LENGTH_LONG).show();
                                                         }
                                                     } else {
                                                         Toast.makeText(getApplicationContext(), " Incorrect username or password ", Toast.LENGTH_LONG).show();
                                                     }

                    /*Cursor c = database.rawQuery("SELECT * FROM users", null);
                    //Cursor d = database.rawQuery("SELECT * FROM users WHERE pword = '\"+username1+\"';", null);
                    int unameIndex = c.getColumnIndex("uname");
                    int pwordIndex = c.getColumnIndex("pword");
                    int idIndex = c.getColumnIndex("id");

                    c.moveToFirst();
                    while (c != null) {
                        {
                            String savedUname = c.getString(unameIndex);
                            String savedPword = c.getString(pwordIndex);
                            String savedId = String.valueOf(c.getInt(idIndex));
                            Log.i("table content",savedId+"---" +savedUname + ":" + savedPword);

                            if (savedUname.equals(userName.getText().toString())) {
                                if (savedPword.equals(passWord.getText().toString())) {
                                    intent.setAction(Intent.ACTION_VIEW);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), " Incorrect username or password ", Toast.LENGTH_LONG).show();
                                }
                            }*//*else {
                                Toast.makeText(getApplicationContext(), " Incorrect username or password ", Toast.LENGTH_LONG).show();

                            }*//*
                        }*/
                                                     c.moveToNext();

                                                 }

                                             }else {
                                                 Toast.makeText(getApplicationContext(), " Incorrect username or password ", Toast.LENGTH_LONG).show();
                                             }
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }

        );


    }
}
