package com.example.kranthi.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
 

    EditText userName;
    EditText passWord;
    Button logIn, signUp;

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


        final String username1 = userName.getText().toString();
      String  password1 = passWord.getText().toString();

        final SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS users (uname VARCHAR, pword VARCHAR)");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), username1 , Toast.LENGTH_LONG).show();
                /*if (username1 != "" && password1 != "") {*/
                try {
                    database.execSQL("INSERT INTO users(uname, pword) VALUES('kkk', '123')");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), passWord.getText().toString(), Toast.LENGTH_LONG).show();
               /* } else {
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                }
            }*/


            }

        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, secondscreen.class);
                try {
                    Cursor c = database.rawQuery("SELECT * FROM users", null);
                    int unameIndex = c.getColumnIndex("uname");
                    int pwordIndex = c.getColumnIndex("pword");
                    c.moveToFirst();
                    while (c != null) {
                        String savedUname = c.getString(unameIndex);
                        String savedPword = c.getString(pwordIndex);
                        Toast.makeText(getApplicationContext(), savedPword , Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), savedUname, Toast.LENGTH_LONG).show();


                        if (savedUname.equals(userName.getText().toString())) {
                            Toast.makeText(getApplicationContext(), " correct username ", Toast.LENGTH_LONG).show();
                            if (savedPword.equals(passWord.getText().toString())) {
                                Toast.makeText(getApplicationContext(), " correct password ", Toast.LENGTH_LONG).show();
                                intent.setAction(Intent.ACTION_VIEW);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), " Incorrect password ", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), " Incorrect Username and Password ", Toast.LENGTH_LONG).show();
                        }
                        c.moveToNext();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });


    }
}
