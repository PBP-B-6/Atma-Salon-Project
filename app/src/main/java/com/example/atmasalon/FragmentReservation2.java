package com.example.atmasalon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.atmasalon.databinding.FragmentReservation2Binding;
import com.example.atmasalon.preferences.ReservationPreference;

public class FragmentReservation2 extends Fragment implements View.OnClickListener{
    private FragmentReservation2Binding binding;
    private ReservationPreference reservationPreference;
    //private double totalHarga; jangan lupa

    public FragmentReservation2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reservationPreference = new ReservationPreference(this.getActivity());
        binding.btnBayarReservasi.setOnClickListener(this);
        CheckFilled();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnBayarReservasi)
        {
            //nanti cek saldo user, kalo saldo < total bayar pindahe ke yang gagal
            //kalo berhasil ndaa
            //disini juga nyimpen reservationpreverence
            if(Validation())
            {
                //disini if else saldo
                this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentPembayaran())
                    .commit();
            }
            else
            {
                Toast.makeText(getActivity(), "Silahkan isi semua field!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //jangan lupa Get radio buton
        String nama, telp;
        nama = binding.inputLayoutNamaReservasi.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasi.getEditText().getText().toString();

        //ini masih hardcode di model warna harga
        reservationPreference.FillDataPage2(nama, telp, "", "", 0);
    }

    private boolean Validation()
    {
        //Validasi untuk model dan warna juga
        String nama, telp;
        nama = binding.inputLayoutNamaReservasi.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasi.getEditText().getText().toString();
        if(nama.isEmpty())
        {
            return false;
        }
        else if(telp.isEmpty())
        {
            return false;
        }

        return true;
    }

    private void CheckFilled()
    {
        binding.inputLayoutNamaReservasi.getEditText().setText(reservationPreference.GetAllData().getNamaPemesan());
        binding.inputLayoutTelpReservasi.getEditText().setText(reservationPreference.GetAllData().getNoTelp());

        //JANGAN LUPA ISI JUGA BUAT YANG RADIO
        //nanti juga tiap radio klao dicheck mereturnkan harga yg berbeda biar variatif
    }
}