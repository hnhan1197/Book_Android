package com.example.book_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_android.R;
import com.example.book_android.models.Book;

import java.text.DecimalFormat;
import java.util.List;

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
    }

    @Override
    public int getItemCount() {
        if (bookList != null) return bookList.size();
        return 0;
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
