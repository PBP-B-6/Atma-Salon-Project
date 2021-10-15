package com.example.atmasalon;

import android.icu.number.NumberFormatter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentProfilBinding;
import com.example.atmasalon.databinding.FragmentTopupBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.UserPreference;

public class FragmentTopup extends Fragment implements View.OnClickListener{

    private FragmentTopupBinding binding;
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
        binding.btnTopupSaldo.setOnClickListener(this);
        userToUpdate = GetUser();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTopupSaldo)
        {
            if(Validasi())
            {
                //Update Saldo
                Toast.makeText(getActivity(), "Update Saldo", Toast.LENGTH_SHORT).show();
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
                return false;
            }
            else if(Double.parseDouble(text) < 1)
            {
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

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPref.GetUserID());
    }
}