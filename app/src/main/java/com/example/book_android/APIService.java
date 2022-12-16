package com.example.book_android;

import com.example.book_android.models.UserRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    APIService apiService = (APIService) new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:5000/")
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
            .build()
            .create(APIService.class);
    @GET("api/user")
    Call<List<User>> getAllUsers();

    @POST("api/auth/register")
    Call<UserRegister> register(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password);
}
