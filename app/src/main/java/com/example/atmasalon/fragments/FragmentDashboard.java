package com.example.atmasalon.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmasalon.AboutActivity;
import com.example.atmasalon.R;
import com.example.atmasalon.databinding.FragmentDashboardBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentDashboard extends Fragment implements View.OnClickListener {

    //TODO: nanti GetUser itu dari userPreferencenya
    private FragmentDashboardBinding binding;
    private UserPreference userPref;
    private User userNow;
    private BottomNavigationView nav;

    public FragmentDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity().getApplicationContext());
        userNow = userPref.GetUserNow();

        float saldo = userNow.getSaldo();
        String saldoStr = "Rp. " + String.format("%.0f", saldo) + ",00";

        binding.dashboardProfileName.setText(userNow.getNama());
        binding.dashboardProfileSaldo.setText(saldoStr);
        binding.btnLihatProfil.setOnClickListener(this);
        binding.btnTambahSaldo.setOnClickListener(this);
        binding.btnReservasi.setOnClickListener(this);
        binding.btnTentangKami.setOnClickListener(this);
        nav = getActivity().findViewById(R.id.bottom_navigation);


        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Dashboard");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLihatProfil){
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentProfil())
                    .commit();
//            userPref.SetSwitch(4);
            MenuItem item = nav.getMenu().findItem(R.id.menu_profil);
            item.setChecked(true);
        } else if(view.getId() == R.id.btnTambahSaldo){
            this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentTopup())
                .commit();
            MenuItem item = nav.getMenu().findItem(R.id.menu_profil);
            item.setChecked(true);
        } else if(view.getId() == R.id.btnReservasi){
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentReservation2())
                    .commit();
//            userPref.SetSwitch(3);
            MenuItem item = nav.getMenu().findItem(R.id.menu_reservasi);
            item.setChecked(true);
        } else if(view.getId() == R.id.btnTentangKami){
                Intent move = new Intent(this.getActivity(), AboutActivity.class);
                startActivity(move);
                getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}