package com.example.casper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHelloWorld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHelloWorld=findViewById(R.id.textViewHelloWorld);
        textViewHelloWorld.setText("你好，编程实现的Hello world!");
    }
}
