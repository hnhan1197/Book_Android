package com.example.book_android.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_android.APIService;
import com.example.book_android.R;
import com.example.book_android.fragments.BookFragment;
import com.example.book_android.models.Book;
import com.example.book_android.models.Token;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private final List<Book> bookList;
    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookAdapter.BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        if (book == null) return;
        Glide.with(holder.itemView.getContext()).load(book.getBookImg())
                .placeholder(R.drawable.ic_launcher_background).into(holder.imgBook);
        holder.tvBookName.setText(book.getBookName());
        holder.tvPrice.setText("Giá: " + formatter.format(book.getPrice()) + " VND");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewDialogBook = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_edit_book, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(viewDialogBook.getContext());
                builder.setView(viewDialogBook);
                AlertDialog alert = builder.create();
                alert.show();
                EditText dialogInputBookID = viewDialogBook.findViewById(R.id.dialogInputBookID);
                EditText dialogInputBookName = viewDialogBook.findViewById(R.id.dialogInputBookName);
                EditText dialogInputBookImg = viewDialogBook.findViewById(R.id.dialogInputBookImg);
                EditText dialogInputBookDesc = viewDialogBook.findViewById(R.id.dialogInputBookDesc);
                EditText dialogInputBookPrice = viewDialogBook.findViewById(R.id.dialogInputBookPrice);

                dialogInputBookID.setText(book.getId());
                dialogInputBookID.setEnabled(false);
                dialogInputBookName.setText(book.getBookName());
                dialogInputBookImg.setText(book.getBookImg());
                dialogInputBookDesc.setText(book.getBookDesc());
                dialogInputBookPrice.setText(String.valueOf(book.getPrice()));

                viewDialogBook.findViewById(R.id.dialogEditBook).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String bookID = dialogInputBookID.getText().toString();
                        String bookName = dialogInputBookName.getText().toString();
                        String bookImg = dialogInputBookImg.getText().toString();
                        String bookDesc = dialogInputBookDesc.getText().toString();
                        int bookPrice = Integer.parseInt(dialogInputBookPrice.getText().toString());
                        book.setBookName(bookName);
                        book.setBookImg(bookImg);
                        book.setBookDesc(bookDesc);
                        book.setPrice(bookPrice);
                        APIService.apiService.editABook(Token.accessToken, bookID, book).enqueue(new Callback<Book>() {
                            @Override
                            public void onResponse(Call<Book> call, Response<Book> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        notifyItemChanged(holder.getAdapterPosition());
                                        Toast.makeText(viewDialogBook.getContext(), "Sửa thông tin sách thành công", Toast.LENGTH_SHORT).show();
                                        alert.dismiss();
                                    } else {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        Toast.makeText(viewDialogBook.getContext(), jObjError.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(viewDialogBook.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Book> call, Throwable t) {
                                Toast.makeText(viewDialogBook.getContext(), "Sửa thông tin sách thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bookList != null) return bookList.size();
        return 0;
    }

    private void deleteBook(String bookID) {
        APIService.apiService.deleteABook(Token.accessToken, bookID).enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                try {
                    if (response.isSuccessful()) {

                    } else {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        //Toast.makeText(, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    //Toast.makeText(viewDialogBook.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }
        });
    }


    public static class BookViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvBookName, tvPrice;
        private final ImageView imgBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvBookName = itemView.findViewById(R.id.txtBookName);
            tvPrice = itemView.findViewById(R.id.txtBookPrice);
        }
    }
}
