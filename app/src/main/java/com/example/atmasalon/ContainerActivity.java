package com.example.atmasalon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ContainerActivity extends Activity {

    private BottomNavigationView nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        nav_bar = findViewById(R.id.bottom_navigation);
        nav_bar.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) this);

//        changeFragment(new DashboardFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menu_beranda){
            changeFragment(new FragmentDashboard());
        }else if(item.getItemId() == R.id.menu_riwayat) {
            changeFragment(new FragmentRiwayat());
        }else if(item.getItemId() == R.id.menu_reservasi) {
            changeFragment(new FragmentReservation2());
        }else{
            changeFragment(new FragmentProfil());
        }

        return true;

    }

    public void changeFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment,fragment)
                .commit();
    }
}