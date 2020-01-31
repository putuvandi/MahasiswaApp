package com.example.mahasiswaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mahasiswaapp.apihelper.BaseApiService;
import com.example.mahasiswaapp.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.StringTokenizer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Biodata extends AppCompatActivity {

    EditText txtBioNim, txtBioNamaMhs, txtBioKabupatenLahir, txtBioTempatLahir, txtBioTglLahir,
            txtBioJenisKelamin, txtBioAlamatSkr, txtBioKabupatenSkr, txtBioKodePosSkr,
            txtBioAlamatAsal, txtBioKabupatenAsal, txtBioKodePosAsal, txtBioNamaAyah, txtBioEmail,
            txtBioNoHp, txtBioNISN, txtBioNIK, txtBioTglLahirAyah, txtBioNamaIbu,
            txtBioTglLahirIbu, txtBioNIKAyah, txtBioNIKIbu;

    DatePickerDialog picker;

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
        txtBioKabupatenLahir = (EditText) findViewById(R.id.txtBioKabupatenLahir);
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
        ambilNamaKabupaten(txtBioKabupatenLahir, mKodeKabLahir);

        String mTempatLahir = extras.getString("tempatLahir");
        txtBioTempatLahir.setText(mTempatLahir);

        String mTglLahir = extras.getString("tglLahir");
        txtBioTglLahir.setText(mTglLahir);
        txtBioTglLahir.setInputType(InputType.TYPE_NULL);
        txtBioTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] tgl = getTanggal(txtBioTglLahir.getText().toString());
                int tahun = tgl[0];
                int bulan = tgl[1]-1;
                int hari = tgl[2];
                Log.e("cetak: ", Integer.toString(bulan));
                picker = new DatePickerDialog(Biodata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBioTglLahir.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                }, tahun, bulan, hari);
                picker.show();
            }
        });

        String mSex = extras.getString("sex");
        ambilJenisKelamin(txtBioJenisKelamin, mSex);
        //txtBioJenisKelamin.setText(mSex);

        String mAlamatSkr = extras.getString("alamatSkr");
        txtBioAlamatSkr.setText(mAlamatSkr);

        String mKodeKabSkr = extras.getString("kodeKabSkr");
        ambilNamaKabupaten(txtBioKabupatenSkr, mKodeKabSkr);

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
        txtBioTglLahirAyah.setInputType(InputType.TYPE_NULL);
        txtBioTglLahirAyah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] tgl = getTanggal(txtBioTglLahirAyah.getText().toString());
                int tahun = tgl[0];
                int bulan = tgl[1]-1;
                int hari = tgl[2];
                Log.e("cetak: ", Integer.toString(bulan));
                picker = new DatePickerDialog(Biodata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBioTglLahirAyah.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                }, tahun, bulan, hari);
                picker.show();
            }
        });

        String mNamaIbuKandung = extras.getString("namaIbuKandung");
        txtBioNamaIbu.setText(mNamaIbuKandung);

        String mTglLahirIbuKandung = extras.getString("tglLahirIbuKandung");
        txtBioTglLahirIbu.setText(mTglLahirIbuKandung);
        txtBioTglLahirIbu.setInputType(InputType.TYPE_NULL);
        txtBioTglLahirIbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] tgl = getTanggal(txtBioTglLahirIbu.getText().toString());
                int tahun = tgl[0];
                int bulan = tgl[1]-1;
                int hari = tgl[2];
                Log.e("cetak: ", Integer.toString(bulan));
                picker = new DatePickerDialog(Biodata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBioTglLahirIbu.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                    }
                }, tahun, bulan, hari);
                picker.show();
            }
        });

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

    private int[] getTanggal(String tgl) {
        StringTokenizer st = new StringTokenizer(tgl, "-");
        int[] tanggal = new int[3];
        tanggal[0] = Integer.parseInt(st.nextToken());
        tanggal[1] = Integer.parseInt(st.nextToken());
        tanggal[2] = Integer.parseInt(st.nextToken());
        return tanggal;
    }

    private void ambilNamaKabupaten(final EditText editText, String kodeKabupaten) {
        mApiService.tampilNamaKabupatenRequest(kodeKabupaten).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("error").equals("false")) {
                            editText.setText(jsonResults.getString("kabupaten"));
                        } else {
                            // jika gagal
                            String error_msg = jsonResults.getString("error_msg");
                            Toast.makeText(mContext, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> mCall, Throwable mThrowable) {
                Log.e("debug", "onFailure: ERROR > " + mThrowable.toString());
            }
        });
    }

    private void ambilJenisKelamin(final EditText editText, String kodeSex) {
        mApiService.tampilJenisKelaminRequest(kodeSex).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("error").equals("false")) {
                            editText.setText(jsonResults.getString("jenis_kelamin"));
                        } else {
                            // jika gagal
                            String error_msg = jsonResults.getString("error_msg");
                            Toast.makeText(mContext, error_msg, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> mCall, Throwable mThrowable) {
                Log.e("debug", "onFailure: ERROR > " + mThrowable.toString());
            }
        });
    }
}
