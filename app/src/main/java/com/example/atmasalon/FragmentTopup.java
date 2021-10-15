package com.example.atmasalon;

import android.content.Context;
import android.icu.number.NumberFormatter;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.room.Update;

import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.ActivityContainerBinding;
import com.example.atmasalon.databinding.FragmentProfilBinding;
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
        userToUpdate = GetUser();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTopupSaldo)
        {
            if(Validasi())
            {
                //Update Saldo
                double saldoBaru = userToUpdate.getSaldo() + Double.parseDouble(binding.inputLayoutTambahSaldo.getEditText().getText().toString());
                userToUpdate.setSaldo(saldoBaru);
                UpdateUserSaldo(userToUpdate);
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

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPref.GetUserID());
    }

    private void UpdateUserSaldo(User user) {
        class UpdatingTodo extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.GetInstance(getActivity()).GetDatabase().userDao().UpdateUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(getActivity(), "Tambah Saldo Berhasil!", Toast.LENGTH_SHORT).show();
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment, new FragmentDashboard())
                        .commit();

                bottomNav.setSelectedItemId(R.id.menu_beranda);
            }
        }
        UpdatingTodo up = new UpdatingTodo();
        up.execute();
    }
}