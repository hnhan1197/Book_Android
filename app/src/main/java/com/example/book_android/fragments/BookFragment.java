package com.example.book_android.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_android.APIService;
import com.example.book_android.LoginActivity;
import com.example.book_android.MainActivity;
import com.example.book_android.R;
import com.example.book_android.adapters.BookAdapter;
import com.example.book_android.adapters.HomeAdapter;
import com.example.book_android.models.Book;
import com.example.book_android.models.Token;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rcvBook;
    private List<Book> bookList;
    BookAdapter bookAdapter;
    AlertDialog alertDialog;
    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rcvBook = (RecyclerView) getView().findViewById(R.id.rcvBook);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rcvBook.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
        rcvBook.addItemDecoration(itemDecoration);

        bookList = new ArrayList<>();
        getAllBookByUser();

        view.findViewById(R.id.btnAddBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialogBook = LayoutInflater.from(getContext()).inflate(R.layout.dialog_book, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(viewDialogBook);
                alertDialog = builder.create();
                alertDialog.show();

                EditText dialogInputBookName = viewDialogBook.findViewById(R.id.dialogInputBookName);
                EditText dialogInputBookImg = viewDialogBook.findViewById(R.id.dialogInputBookImg);
                EditText dialogInputBookDesc = viewDialogBook.findViewById(R.id.dialogInputBookDesc);
                EditText dialogInputBookPrice = viewDialogBook.findViewById(R.id.dialogInputBookPrice);

                viewDialogBook.findViewById(R.id.dialogSaveBook).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String bookName = dialogInputBookName.getText().toString();
                        String bookImg = dialogInputBookImg.getText().toString();
                        String bookDesc = dialogInputBookDesc.getText().toString();
                        int bookPrice = Integer.parseInt(dialogInputBookPrice.getText().toString());
                        addNewBook(bookName, bookImg, bookDesc, bookPrice);
                    }
                });
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }
    private void addNewBook(String bookName, String bookImg, String bookDesc, int bookPrice) {
        Book book = new Book(bookName, bookImg, bookDesc, bookPrice);
        APIService.apiService.addNewBook(Token.accessToken, book).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                try {
                    if (response.isSuccessful()) {
                        bookList.add(book);
                        bookAdapter.notifyItemInserted(bookList.size() - 1);
                        Toast.makeText(BookFragment.this.getContext(), "Đăng bán sách thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(BookFragment.this.getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(BookFragment.this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(BookFragment.this.getContext(), "Đăng bán sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllBookByUser() {
        APIService.apiService.getAllBookByUser(Token.accessToken).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                bookList = response.body();
                bookAdapter = new BookAdapter(bookList);
                rcvBook.setAdapter(bookAdapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Toast.makeText(BookFragment.this.getContext(), "Có lỗi nhá", Toast.LENGTH_SHORT).show();
            }
        });
    }
}