package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicapp.Fragment_Cua_Home.DanhSachPhatFragment;
import com.example.musicapp.Fragment_Cua_Home.HomeFragment;
import com.example.musicapp.Fragment_Cua_Home.TimKiemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView menu_app;
    HomeFragment homeFragment;
    TimKiemFragment timKiemFragment;
    DanhSachPhatFragment danhSachPhatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menu_app = findViewById((R.id.menu_of_app));
        homeFragment = new HomeFragment();
        timKiemFragment = new TimKiemFragment();
        danhSachPhatFragment = new DanhSachPhatFragment();
        tai_fragment(homeFragment);

        menu_app.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    tai_fragment(homeFragment);
                } else if (id == R.id.menu_tim_kiem) {
                    tai_fragment(timKiemFragment);
                } else if (id == R.id.menu_danh_sach_phat) {
                    tai_fragment(danhSachPhatFragment);
                }

                return false;
            }
        });
    }

    public void tai_fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.menu_layout, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

}