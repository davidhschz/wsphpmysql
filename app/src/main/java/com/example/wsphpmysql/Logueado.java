package com.example.wsphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Logueado extends AppCompatActivity {
    public static final String nombre= "nombre";
    public static final String correo= "correo";
    TextView nombrel, emaill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logueado);

        nombrel = findViewById(R.id.tvnombrel);
        emaill = findViewById(R.id.tvemaill);
        nombrel.setText(getIntent().getStringExtra("nombre"));
        emaill.setText(getIntent().getStringExtra("correo"));
    }
}