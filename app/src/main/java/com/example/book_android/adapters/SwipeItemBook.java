package com.example.book_android.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeItemBook extends ItemTouchHelper.SimpleCallback {
    BookAdapter adapter;
    public SwipeItemBook(BookAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        adapter = adapter;
    }
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull
            RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
    {
        return false;
    }
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        this.adapter.deleteItemAtPos(pos);
    }
}
