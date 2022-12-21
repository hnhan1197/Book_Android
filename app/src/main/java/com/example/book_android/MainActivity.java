package com.example.book_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.book_android.adapters.ViewPager2Adapter;
import com.example.book_android.fragments.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager2 viewPager2= findViewById(R.id.viewPager2);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.book:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.list:
                        viewPager2.setCurrentItem(2);
                        break;
                    case R.id.user:
                        viewPager2.setCurrentItem(3);
                        break;
                    default:
                        viewPager2.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNav.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1:
                        bottomNav.getMenu().findItem(R.id.book).setChecked(true);
                        break;
                    case 2:
                        bottomNav.getMenu().findItem(R.id.list).setChecked(true);
                        break;
                    case 3:
                        bottomNav.getMenu().findItem(R.id.user).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });
    }

}