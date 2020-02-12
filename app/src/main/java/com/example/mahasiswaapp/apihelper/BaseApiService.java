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

    // Fungsi untuk memanggil API http://localhost/sia/UbahBiodata.php
    @FormUrlEncoded
    @POST("UbahBiodata.php")
    Call<ResponseBody> ubahBiodataRequest(@Field("nim") String nim,
                                       @Field("kodeKabLahir") String kodeKabLahir,
                                       @Field("tempatLahir") String tempatLahir,
                                       @Field("tglLahir") String tglLahir,
                                          @Field("alamatSkr") String alamatSkr,
                                          @Field("kodeKabSkr") String kodeKabSkr,
                                          @Field("kodePosSkr") String kodePosSkr,
                                          @Field("alamatAsal") String alamatAsal,
                                          @Field("kodeKabAsal") String kodeKabAsal,
                                          @Field("kodePosAsal") String kodePosAsal,
                                          @Field("namaAyah") String namaAyah,
                                          @Field("email") String email,
                                          @Field("noHp") String noHp,
                                          @Field("nisn") String nisn,
                                          @Field("nik") String nik,
                                          @Field("tglLahirAyah") String tglLahirAyah,
                                          @Field("namaIbu") String namaIbu,
                                          @Field("tglLahirIbu") String tglLahirIbu,
                                          @Field("nikAyah") String nikAyah,
                                          @Field("nikIbu") String nikIbu);

    // Fungsi untuk memanggil API http://localhost/sia/TampilBiodata.php
    @GET("TampilBiodata.php")
    Call<ResponseBody> tampilBiodataRequest(@Query("nim") String nim);

    // Fungsi untuk memanggil API http://localhost/sia/TampilAllKabupaten.php
    @GET("TampilAllKabupaten.php")
    Call<ResponseBody> getAllKabupaten();
}
