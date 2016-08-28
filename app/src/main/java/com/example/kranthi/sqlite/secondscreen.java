package com.example.kranthi.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class secondscreen extends AppCompatActivity {
    String line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondscreen);
        TextView tv = (TextView) findViewById(R.id.textView);
        tv.setText(readTextFile());
        tv.setMovementMethod(new ScrollingMovementMethod());

    }

    public  String readTextFile() {

        InputStream inputstream = getResources().openRawResource(R.raw.kk);
        InputStreamReader inputreader = new InputStreamReader(inputstream);
        BufferedReader bufferedreader = new BufferedReader(inputreader);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while ((line = bufferedreader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            return null;
        }

        return stringBuilder.toString();
    }
}
