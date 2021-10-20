package com.example.atmasalon;

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

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentPembayaranBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentPembayaran extends Fragment implements View.OnClickListener{

    //FRAGMENT PEMBAYARAN SAAT BERHASIL, insert ke database DataPelanggannya
    //jangan lupa Clear ReservationPreference

    private FragmentPembayaranBinding binding;
    private UserPreference userPreference;
    private ReservationPreference reservationPreference;
    private BottomNavigationView bottomNav;

    public FragmentPembayaran() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pembayaran, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBayarPembayaranBerhasil.setOnClickListener(this);
        userPreference = new UserPreference(this.getActivity());
        reservationPreference = new ReservationPreference(this.getActivity());

        bottomNav = getActivity().findViewById(R.id.bottom_navigation);

        double totalHarga = reservationPreference.GetTotalHarga();
        String saldoStr = "Rp. " + String.format("%.0f", totalHarga) + ",00";
        binding.inputLayoutTotalHargaBerhasil.getEditText().setText(saldoStr);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Pembayaran");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLinkTopupPembayaran)
        {
            this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentTopup())
                .commit();
        }
        else if(view.getId() == R.id.btnBayarPembayaranBerhasil)
        {
            //Insert Data

            // kurang saldo
            User user = GetUser();
            double saldo = user.getSaldo() - reservationPreference.GetTotalHarga();
            user.setSaldo(saldo);
            UpdateUserSaldo(user);

            //clear prefereceReserv
            reservationPreference.ClearPreference();
        }
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

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPreference.GetUserID());
    }
}