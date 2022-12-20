package com.example.book_android;

import com.example.book_android.models.Book;
import com.example.book_android.models.Token;
import com.example.book_android.requests.ReqLogin;
import com.example.book_android.requests.ReqRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("api/user")
    Call<List<ReqRegister>> getAllUsers();

    @POST("api/auth/register")
    @Headers("Content-Type: application/json")
    Call<ReqRegister> register(@Body ReqRegister reqRegister);

    @POST("api/auth/login")
    @Headers("Content-Type: application/json")
    Call<ReqLogin> login(@Body ReqLogin reqLogin);

    @GET("api/book")
    @Headers("Content-Type: application/json")
    Call<List<Book>> getAllBook(@Header("token") String token);

    @GET("api/book/getByUser")
    @Headers("Content-Type: application/json")
    Call<List<Book>> getAllBookByUser(@Header("token") String token);
}
