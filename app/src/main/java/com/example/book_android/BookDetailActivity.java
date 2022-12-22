package com.example.book_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.book_android.fragments.HomeFragment;
import com.example.book_android.models.Book;
import com.example.book_android.models.Receipt;
import com.example.book_android.models.Token;
import com.example.book_android.requests.ReqReceipt;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailActivity extends AppCompatActivity {
    Book bookDetail;
    ImageView imgBook;
    TextView btnBackMain, txtBookName, txtBookDesc, txtBookPrice, txtUser;
    Button btnBuy;
    int price;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        String bookID = (String) bundle.get("BookID");
        imgBook =(ImageView) findViewById(R.id.imgBookDetail);
        txtBookName = findViewById(R.id.txtBookName);
        txtBookDesc = findViewById(R.id.txtDesc);
        txtBookPrice = findViewById(R.id.txtBookPrice);
        txtUser = findViewById(R.id.txtUser);
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmBuyBook(price, bookID);
//                createReceipt(29000, bookID);
            }
        });
        getBookDetail(bookID);
        btnBackMain = findViewById(R.id.btnBackMain);
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showConfirmBuyBook(int price, String bookID) {
        AlertDialog dialog = new AlertDialog.Builder(BookDetailActivity.this)
                .setTitle("Bạn có muốn đặt mua sách này?")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createReceipt(price, bookID);
                    }
                }).create();
        dialog.show();
    }
    private void createReceipt(int price, String bookID) {
        ReqReceipt receipt = new ReqReceipt(price, bookID);
        APIService.apiService.createReceipt(Token.accessToken, receipt).enqueue(new Callback<ReqReceipt>() {
            @Override
            public void onResponse(Call<ReqReceipt> call, Response<ReqReceipt> response) {
                try {
                    if (response.isSuccessful()) {
                        Toast.makeText(BookDetailActivity.this, "Mua sách thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(BookDetailActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BookDetailActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReqReceipt> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Mua sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getBookDetail(String bookID) {
        APIService.apiService.getABook(Token.accessToken, bookID).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                bookDetail = response.body();
                Glide.with(BookDetailActivity.this).load(bookDetail.getBookImg())
                        .placeholder(R.drawable.ic_launcher_background).into(imgBook);
                txtBookName.setText("Tên sách: " + bookDetail.getBookName());
                txtBookDesc.setText("Mô tả sách: " + bookDetail.getBookDesc());
                txtUser.setText("Người đăng bán: " + bookDetail.getUser().getUsername());
                txtBookPrice.setText("Giá: " + String.valueOf(bookDetail.getPrice()));
                price = bookDetail.getPrice();
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookDetailActivity.this, "Có lỗi nhá", Toast.LENGTH_SHORT).show();
            }
        });
    }
}