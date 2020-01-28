package com.example.mahasiswaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahasiswaapp.apihelper.BaseApiService;
import com.example.mahasiswaapp.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResultNim;

    Button btnLogout, btnBiodata, btnUbahPassword;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;
    ProgressDialog loading;

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

        mContext = this;
        mApiService = UtilsApi.getAPIService();

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
            loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
            requestTampilBiodata();
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

    private void requestTampilBiodata() {
        mApiService.tampilBiodataRequest(sharedPrefManager.getSPNim()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    try {
                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("error").equals("false")) {

                            Intent intent = new Intent(mContext, Biodata.class);
                            Bundle extras = new Bundle();

                            String mNamaMhs = jsonResults.getJSONObject("mahasiswa").getString("nama_mahasiswa");
                            extras.putString("namaMhs", mNamaMhs);

                            String mKodeKabLahir = jsonResults.getJSONObject("mahasiswa").getString("kode_kabupaten_lahir");
                            extras.putString("kodeKabLahir", mKodeKabLahir);

                            String mTempatLahir = jsonResults.getJSONObject("mahasiswa").getString("tempat_lahir");
                            extras.putString("tempatLahir", mTempatLahir);

                            String mTglLahir = jsonResults.getJSONObject("mahasiswa").getString("tgl_lahir");
                            extras.putString("tglLahir", mTglLahir);

                            String mSex = jsonResults.getJSONObject("mahasiswa").getString("jenis_kelamin");
                            extras.putString("sex", mSex);

                            String mAlamatSkr = jsonResults.getJSONObject("mahasiswa").getString("alamat_skr");
                            extras.putString("alamatSkr", mAlamatSkr);

                            String mKodeKabSkr = jsonResults.getJSONObject("mahasiswa").getString("kode_kabupaten_skr");
                            extras.putString("kodeKabSkr", mKodeKabSkr);

                            String mKodePosSkr = jsonResults.getJSONObject("mahasiswa").getString("kode_pos_skr");
                            extras.putString("kodePosSkr", mKodePosSkr);

                            String mAlamatAsal = jsonResults.getJSONObject("mahasiswa").getString("alamat_asal");
                            extras.putString("alamatAsal", mAlamatAsal);

                            String mKodeKabAsal = jsonResults.getJSONObject("mahasiswa").getString("kode_kabupaten_asal");
                            extras.putString("kodeKabAsal", mKodeKabAsal);

                            String mKodePosAsal = jsonResults.getJSONObject("mahasiswa").getString("kode_pos_asal");
                            extras.putString("kodePosAsal", mKodePosAsal);

                            String mNamaAyah = jsonResults.getJSONObject("mahasiswa").getString("nama_ayah");
                            extras.putString("namaAyah", mNamaAyah);

                            String mEmail = jsonResults.getJSONObject("mahasiswa").getString("email");
                            extras.putString("email", mEmail);

                            String mNoHp = jsonResults.getJSONObject("mahasiswa").getString("no_hp");
                            extras.putString("noHp", mNoHp);

                            String mNisn = jsonResults.getJSONObject("mahasiswa").getString("nisn");
                            extras.putString("nisn", mNisn);

                            String mNik = jsonResults.getJSONObject("mahasiswa").getString("nik");
                            extras.putString("nik", mNik);

                            String mTglLahirAyah = jsonResults.getJSONObject("mahasiswa").getString("tgl_lahir_ayah");
                            extras.putString("tglLahirAyah", mTglLahirAyah);

                            String mNamaIbuKandung = jsonResults.getJSONObject("mahasiswa").getString("nama_ibu_kandung");
                            extras.putString("namaIbuKandung", mNamaIbuKandung);

                            String mTglLahirIbuKandung = jsonResults.getJSONObject("mahasiswa").getString("tgl_lahir_ibu_kandung");
                            extras.putString("tglLahirIbuKandung", mTglLahirIbuKandung);

                            String mNikAyah = jsonResults.getJSONObject("mahasiswa").getString("nik_ayah");
                            extras.putString("nikAyah", mNikAyah);

                            String mNikIbuKandung = jsonResults.getJSONObject("mahasiswa").getString("nik_ibu_kandung");
                            extras.putString("nikIbuKandung", mNikIbuKandung);

                            intent.putExtras(extras);
                            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
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
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                loading.dismiss();
            }
        });
    }
}
