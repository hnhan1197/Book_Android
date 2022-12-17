package com.example.book_android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_android.models.Token;
import com.example.book_android.requests.ReqLogin;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    String email, password;
    EditText txtEmail, txtPassword;
    Button btnLogin;
    TextView btnDontHaveAccount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnDontHaveAccount = (TextView) findViewById(R.id.btnDontHaveAccount);
        btnDontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(LoginActivity.this, RegisterActivity.class)
                );
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                login(email, password);
            }
        });
    }

    private void login(String email, String password) {
        ReqLogin reqLogin = new ReqLogin(email, password);
        APIService.apiService.login(reqLogin).enqueue(new Callback<ReqLogin>() {
            @Override
            public void onResponse(Call<ReqLogin> call, Response<ReqLogin> response) {
                try {
                    if (response.isSuccessful()) {
                        Token.accessToken = "Bearer " + response.body().getToken();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(
                                new Intent(LoginActivity.this, MainActivity.class)
                        );
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReqLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}