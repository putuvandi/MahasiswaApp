package com.example.mahasiswaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mahasiswaapp.apihelper.BaseApiService;
import com.example.mahasiswaapp.apihelper.UtilsApi;

public class Biodata extends AppCompatActivity {

    EditText txtBioNim, txtBioNamaMhs, txtBioTempatLahir, txtBioTglLahir, txtBioJenisKelamin,
            txtBioAlamatSkr, txtBioKabupatenSkr, txtBioKodePosSkr, txtBioAlamatAsal,
            txtBioKabupatenAsal, txtBioKodePosAsal, txtBioNamaAyah, txtBioEmail,
            txtBioNoHp, txtBioNISN, txtBioNIK, txtBioTglLahirAyah, txtBioNamaIbu,
            txtBioTglLahirIbu, txtBioNIKAyah, txtBioNIKIbu;

    Button btnUbahBiodata;

    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        txtBioNim = (EditText) findViewById(R.id.txtBioNim);
        txtBioNamaMhs = (EditText) findViewById(R.id.txtBioNamaMhs);
        txtBioTempatLahir = (EditText) findViewById(R.id.txtBioTempatLahir);
        txtBioTglLahir = (EditText) findViewById(R.id.txtBioTglLahir);
        txtBioJenisKelamin = (EditText) findViewById(R.id.txtBioJenisKelamin);
        txtBioAlamatSkr = (EditText) findViewById(R.id.txtBioAlamatSkr);
        txtBioKabupatenSkr = (EditText) findViewById(R.id.txtBioKabupatenSkr);
        txtBioKodePosSkr = (EditText) findViewById(R.id.txtBioKodePosSkr);
        txtBioAlamatAsal = (EditText) findViewById(R.id.txtBioAlamatAsal);
        txtBioKabupatenAsal = (EditText) findViewById(R.id.txtBioKabupatenAsal);
        txtBioKodePosAsal = (EditText) findViewById(R.id.txtBioKodePosAsal);
        txtBioNamaAyah = (EditText) findViewById(R.id.txtBioNamaAyah);
        txtBioEmail = (EditText) findViewById(R.id.txtBioEmail);
        txtBioNoHp = (EditText) findViewById(R.id.txtBioNoHp);
        txtBioNISN = (EditText) findViewById(R.id.txtBioNISN);
        txtBioNIK = (EditText) findViewById(R.id.txtBioNIK);
        txtBioTglLahirAyah = (EditText) findViewById(R.id.txtBioTglLahirAyah);
        txtBioNamaIbu = (EditText) findViewById(R.id.txtBioNamaIbu);
        txtBioTglLahirIbu = (EditText) findViewById(R.id.txtBioTglLahirIbu);
        txtBioNIKAyah = (EditText) findViewById(R.id.txtBioNIKAyah);
        txtBioNIKIbu = (EditText) findViewById(R.id.txtBioNIKIbu);

        btnUbahBiodata = (Button) findViewById(R.id.btnUbahBiodata);

        sharedPrefManager = new SharedPrefManager(this);
        txtBioNim.setText(sharedPrefManager.getSPNim());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String mNamaMhs = extras.getString("namaMhs");
        txtBioNamaMhs.setText(mNamaMhs);

        String mKodeKabLahir = extras.getString("kodeKabLahir");
        txtBioTempatLahir.setText(mKodeKabLahir);

        //String mTempatLahir = extras.getString("tempatLahir");
        //txtBioTempatLahir.setText(mTempatLahir);

        String mTglLahir = extras.getString("tglLahir");
        txtBioTglLahir.setText(mTglLahir);

        String mSex = extras.getString("sex");
        txtBioJenisKelamin.setText(mSex);

        String mAlamatSkr = extras.getString("alamatSkr");
        txtBioAlamatSkr.setText(mAlamatSkr);

        String mKodeKabSkr = extras.getString("kodeKabSkr");
        txtBioKabupatenSkr.setText(mKodeKabSkr);

        String mKodePosSkr = extras.getString("kodePosSkr");
        txtBioKodePosSkr.setText(mKodePosSkr);

        String mAlamatAsal = extras.getString("alamatAsal");
        txtBioAlamatAsal.setText(mAlamatAsal);

        String mKodeKabAsal = extras.getString("kodeKabAsal");
        txtBioKabupatenAsal.setText(mKodeKabAsal);

        String mKodePosAsal = extras.getString("kodePosAsal");
        txtBioKodePosAsal.setText(mKodePosAsal);

        String mNamaAyah = extras.getString("namaAyah");
        txtBioNamaAyah.setText(mNamaAyah);

        String mEmail = extras.getString("email");
        txtBioEmail.setText(mEmail);

        String mNoHp = extras.getString("noHp");
        txtBioNoHp.setText(mNoHp);

        String mNisn = extras.getString("nisn");
        txtBioNISN.setText(mNisn);

        String mNik = extras.getString("nik");
        txtBioNIK.setText(mNik);

        String mTglLahirAyah = extras.getString("tglLahirAyah");
        txtBioTglLahirAyah.setText(mTglLahirAyah);

        String mNamaIbuKandung = extras.getString("namaIbuKandung");
        txtBioNamaIbu.setText(mNamaIbuKandung);

        String mTglLahirIbuKandung = extras.getString("tglLahirIbuKandung");
        txtBioTglLahirIbu.setText(mTglLahirIbuKandung);

        String mNikAyah = extras.getString("nikAyah");
        txtBioNIKAyah.setText(mNikAyah);

        String mNikIbuKandung = extras.getString("nikIbuKandung");
        txtBioNIKIbu.setText(mNikIbuKandung);

        btnUbahBiodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                //requestLogin();
            }
        });
    }
}
