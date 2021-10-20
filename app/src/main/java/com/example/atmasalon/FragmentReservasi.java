package com.example.atmasalon;

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

import com.example.atmasalon.databinding.FragmentReservasiBinding;
import com.example.atmasalon.preferences.ReservationPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentReservasi extends Fragment implements View.OnClickListener{
    private FragmentReservasiBinding binding;
    private ReservationPreference reservationPreference;

    public FragmentReservasi() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservasi, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnReservasi1.setOnClickListener(this);
        reservationPreference = new ReservationPreference(this.getActivity());
        CheckFill();

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Reservasi");
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnReservasi1)
        {
            if(!binding.inputLayoutLokasiSalonss.getEditText().getText().toString().isEmpty())
            {
                this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentReservation2())
                    .commit();
            }
            else
            {
                Toast.makeText(getActivity(), "Silahkan isi semua field!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CheckFill()
    {
        binding.inputLayoutLokasiSalonss.getEditText().setText(reservationPreference.GetLokasi());

    }

}