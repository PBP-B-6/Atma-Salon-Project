package com.example.atmasalon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atmasalon.databinding.FragmentProfilBinding;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;

public class FragmentProfil extends Fragment implements View.OnClickListener{

    private FragmentProfilBinding binding;
    private UserPreference userPref;
    private ReservationPreference reservationPreference;

    public FragmentProfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        reservationPreference = new ReservationPreference(this.getActivity());
        binding.namaProfil.setText(userPref.GetNamaUser());
        binding.emailProfil.setText(userPref.GetUserLogin().getEmail());
        binding.btnKeluar.setOnClickListener(this);
        binding.btnTambahSaldo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTambahSaldo)
        {
            this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentTopup())
                .commit();
        }
        else if(view.getId() == R.id.btnKeluar)
        {
            userPref.Logout();
            reservationPreference.ClearPreference();
            Intent move = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(move);
        }
    }
}