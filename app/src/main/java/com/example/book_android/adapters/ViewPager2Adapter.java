package com.example.book_android.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.book_android.fragments.BookFragment;
import com.example.book_android.fragments.HomeFragment;
import com.example.book_android.fragments.PaymentHistoryFragment;
import com.example.book_android.fragments.UserFragment;

public class ViewPager2Adapter extends FragmentStateAdapter {
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 3)
            return new UserFragment();
        else if (position == 2)
            return new PaymentHistoryFragment();
        else if (position == 1)
            return new BookFragment();
        else return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
