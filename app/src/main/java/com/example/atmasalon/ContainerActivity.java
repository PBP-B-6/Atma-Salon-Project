package com.example.atmasalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityContainerBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserLogin;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ActivityContainerBinding binding;
    private UserPreference userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        binding.bottomNavigation.setOnItemSelectedListener(this);

        userPref = new UserPreference(this);

        changeFragment(new FragmentDashboard());

        CheckLogin();
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
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, fragment)
                .commit();
    }

    private void CheckLogin()
    {
        if(!userPref.CheckLogin())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show();
        }
    }

}