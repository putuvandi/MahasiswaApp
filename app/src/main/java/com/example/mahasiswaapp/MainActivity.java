package com.example.mahasiswaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResultNim;

    Button btnLogout, btnBiodata, btnUbahPassword;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResultNim = (TextView) findViewById(R.id.tvResultNim);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnBiodata = (Button) findViewById(R.id.btnBiodata);
        btnUbahPassword = (Button) findViewById(R.id.btnUbahPassword);

        sharedPrefManager = new SharedPrefManager(this);
        tvResultNim.setText(sharedPrefManager.getSPNim());

        btnLogout.setOnClickListener(this);
        btnBiodata.setOnClickListener(this);
        btnUbahPassword.setOnClickListener(this);

        /*btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                konfirmasiLogout();
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if (v == btnBiodata) {
            Intent intent = new Intent(this, Biodata.class);
            startActivity(intent);
        }
        if (v == btnLogout) {
            konfirmasiLogout();
        }
        if (v == btnUbahPassword) {
            Intent intent = new Intent(this, UbahPassword.class);
            startActivity(intent);
        }
    }

    private void konfirmasiLogout() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage("Apakah yakin ingin keluar?");

        alertBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        startActivity(new Intent(MainActivity.this, Login.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                });

        alertBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
