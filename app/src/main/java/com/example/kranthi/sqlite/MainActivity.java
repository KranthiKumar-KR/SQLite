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



        /*logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String username1 = userName.getText().toString();
                 String  password1 = passWord.getText().toString();
                Toast.makeText(getApplicationContext(),username1 + password1,Toast.LENGTH_LONG).show();
            }
        });*/

        final SQLiteDatabase database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS users (uname VARCHAR UNIQUE, pword VARCHAR UNIQUE)");
        //database.execSQL("CREATE UNIQUE INDEX idx_something ON users (uname, pword)");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username1 = userName.getText().toString();
                String password1 = passWord.getText().toString();
                if (username1 != null && !username1.trim().isEmpty() && password1 != null && !password1.trim().isEmpty()) {
                    try {
                        database.execSQL("INSERT OR REPLACE INTO users(uname, pword) VALUES('" + username1 + "', '" + password1 + "')");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), passWord.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter details", Toast.LENGTH_LONG).show();
                }
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
                        {
                            String savedUname = c.getString(unameIndex);
                            String savedPword = c.getString(pwordIndex);

                            if (savedUname.equals(userName.getText().toString())) {
                                if (savedPword.equals(passWord.getText().toString())) {
                                    intent.setAction(Intent.ACTION_VIEW);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), " Incorrect username or password ", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        c.moveToNext();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }
}
