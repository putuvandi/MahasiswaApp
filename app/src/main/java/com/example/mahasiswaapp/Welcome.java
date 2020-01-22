package com.example.mahasiswaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    TextView txtResultNim;
    Button btnPrev;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        txtResultNim = (TextView) findViewById(R.id.txtResultNim);
        btnPrev = (Button) findViewById(R.id.btnPrev);

        sharedPrefManager = new SharedPrefManager(this);
        txtResultNim.setText(sharedPrefManager.getSPNim());

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
