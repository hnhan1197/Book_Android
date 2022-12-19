package com.example.book_android.adapters;

import android.net.Uri;
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
import com.example.book_android.models.User;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
    private final List<Book> bookList;

    public HomeAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Book book = bookList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        if (book == null) return;
        Glide.with(holder.itemView.getContext()).load(book.getBookImg())
                        .placeholder(R.drawable.ic_launcher_background).into(holder.imgBook);
        holder.tvBookName.setText(book.getBookName());
        holder.tvPrice.setText("Giá: " + formatter.format(book.getPrice()) + " VND");
        holder.tvUser.setText("Người đăng bán: " + book.getUser().getUsername());
    }

    @Override
    public int getItemCount() {
        if (bookList != null) return bookList.size();
        return 0;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvBookName, tvPrice, tvUser;
        private final ImageView imgBook;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvBookName = itemView.findViewById(R.id.txtBookName);
            tvPrice = itemView.findViewById(R.id.txtBookPrice);
            tvUser = itemView.findViewById(R.id.txtUser);
        }
    }
}
