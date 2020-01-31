package com.example.mahasiswaapp.apihelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {

    // Fungsi untuk memanggil API http://localhost/sia/Login.php
    @FormUrlEncoded
    @POST("Login.php")
    Call<ResponseBody> loginRequest(@Field("nim") String nim,
                                    @Field("password") String password);


    // Fungsi untuk memanggil API http://localhost/sia/UbahPassword.php
    @FormUrlEncoded
    @POST("UbahPassword.php")
    Call<ResponseBody> ubahPassRequest(@Field("nim") String nim,
                                    @Field("passwordlama") String passwordlama,
                                       @Field("passwordbaru") String passwordbaru,
                                       @Field("konfpassword") String konfpassword);

    // Fungsi untuk memanggil API http://localhost/sia/TampilBiodata.php
    @GET("TampilBiodata.php")
    Call<ResponseBody> tampilBiodataRequest(@Query("nim") String nim);

    // Fungsi untuk memanggil API http://localhost/sia/TampilNamaKabupaten.php
    @GET("TampilNamaKabupaten.php")
    Call<ResponseBody> tampilNamaKabupatenRequest(@Query("kodeKabupaten") String kodeKabupaten);

    // Fungsi untuk memanggil API http://localhost/sia/TampilJenisKelamin.php
    @GET("TampilJenisKelamin.php")
    Call<ResponseBody> tampilJenisKelaminRequest(@Query("kodeSex") String kodeSex);
}
