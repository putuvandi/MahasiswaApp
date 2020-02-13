package com.example.mahasiswaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mahasiswaapp.apihelper.BaseApiService;
import com.example.mahasiswaapp.apihelper.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText txtNim, txtPassword;
    Spinner loginSebagai;
    Button btnLogin;
    ProgressDialog loading;

    List<String> listLogin;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper

        txtNim = (EditText) findViewById(R.id.txtNim);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        loginSebagai = (Spinner) findViewById(R.id.loginSebagai);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        listLogin = new ArrayList<>();
        listLogin.add("-- Pilih login sebagai --");
        listLogin.add("Mahasiswa");
        listLogin.add("Dosen");

        sharedPrefManager = new SharedPrefManager(this);
        // skip login jika user sudah login
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(Login.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listLogin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loginSebagai.setAdapter(adapter);

        loginSebagai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String pilihan = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(), "Memilih: " + pilihan, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                if (loginSebagai.getSelectedItem().equals("Mahasiswa")) {
                    loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                    requestLogin();
                } else if (loginSebagai.getSelectedItem().equals("Dosen")) {
                    Toast.makeText(mContext, "Masuk ke menu dosen", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext, "Silahkan pilih peran login", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void requestLogin() {
        mApiService.loginRequest(txtNim.getText().toString(), txtPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonResults = new JSONObject(response.body().string());
                                if (jsonResults.getString("error").equals("false")) {
                                    // jika login berhasil maka data nim yang ada di response API
                                    // akan diparsing ke activity selanjutnya
                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                    String mNim = jsonResults.getJSONObject("user").getString("nim");

                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_NIM, mNim);
                                    // Shared Pref berfungsi untuk menjadi trigger session login
                                    sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                    startActivity(new Intent(mContext, MainActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    finish();

                                    //Intent intent = new Intent(mContext, MainActivity.class);
                                    //intent.putExtra("result_nim", mNim);
                                    //startActivity(intent);
                                } else {
                                    // jika login gagal
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
