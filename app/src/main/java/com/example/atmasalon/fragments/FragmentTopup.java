package com.example.atmasalon.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmasalon.R;

import com.example.atmasalon.databinding.FragmentTopupBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentTopup extends Fragment implements View.OnClickListener{

    private FragmentTopupBinding binding;
    private BottomNavigationView bottomNav;
    private UserPreference userPref;
    private User userToUpdate;

    public FragmentTopup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topup, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        binding.btnTopupSaldo.setOnClickListener(this);
        //TODO: UserToUpdate
//        userToUpdate = GetUser();
        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Tambah Saldo");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTopupSaldo)
        {
            if(Validasi())
            {
                //Update Saldo
                float saldoBaru = userToUpdate.getSaldo() + Float.parseFloat(binding.inputLayoutTambahSaldo.getEditText().getText().toString());
                userToUpdate.setSaldo(saldoBaru);
                //TODO: UpdateUser
//                UpdateUserSaldo(userToUpdate);
            }
        }
    }

    private boolean Validasi()
    {
        try
        {
            String text = binding.inputLayoutTambahSaldo.getEditText().getText().toString();
            if(text.isEmpty())
            {
                Toast.makeText(this.getActivity(), "Silahkan isi data!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(Double.parseDouble(text) < 1)
            {
                Toast.makeText(this.getActivity(), "Masukkan jumlah lebih dari 0!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this.getActivity(), "Silahkan isi dengan angka!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}