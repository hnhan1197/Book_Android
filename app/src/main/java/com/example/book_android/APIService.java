package com.example.book_android;

import com.example.book_android.models.Book;
import com.example.book_android.models.Receipt;
import com.example.book_android.models.Token;
import com.example.book_android.models.User;
import com.example.book_android.requests.ReqLogin;
import com.example.book_android.requests.ReqReceipt;
import com.example.book_android.requests.ReqRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.10:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("api/user")
    @Headers("Content-Type: application/json")
    Call<User> getUserInfo(@Header("token") String token);

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

    @POST("api/book/")
    @Headers("Content-Type: application/json")
    Call<Book> addNewBook(@Header("token") String token, @Body Book book);

    @PUT("api/book/{BookId}")
    @Headers("Content-Type: application/json")
    Call<Book> editABook(@Header("token") String token, @Path("BookId") String BookId, @Body Book book);

    @DELETE("api/book/{BookId}")
    @Headers("Content-Type: application/json")
    Call<Book> deleteABook(@Header("token") String token, @Path("BookId") String BookId);

    @GET("api/book/{BookId}")
    @Headers("Content-Type: application/json")
    Call<Book> getABook(@Header("token") String token, @Path("BookId") String BookId);

    @POST("api/receipt")
    @Headers("Content-Type: application/json")
    Call<ReqReceipt> createReceipt(@Header("token") String token, @Body ReqReceipt receipt);

    @GET("api/receipt")
    @Headers("Content-Type: application/json")
    Call<List<Receipt>> getAllReceipt(@Header("token") String token);

}
