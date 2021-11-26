package com.example.atmasalon.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atmasalon.R;
import com.example.atmasalon.databinding.FragmentPembayaranGagalBinding;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;

public class FragmentPembayaranGagal extends Fragment implements View.OnClickListener{

    private FragmentPembayaranGagalBinding binding;
    private UserPreference userPreference;
    private ReservationPreference reservationPreference;

    public FragmentPembayaranGagal() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pembayaran_gagal, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreference = new UserPreference(getActivity());
        reservationPreference = new ReservationPreference(getActivity());

        double totalHarga = reservationPreference.GetTotalHarga();
        String saldoStr = "Rp. " + String.format("%.0f", totalHarga) + ",00";
        binding.inputLayoutTotalHargaGagal.getEditText().setText(saldoStr);

        binding.btnLinkTopupPembayaranGagal.setOnClickListener(this);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Pembayaran");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLinkTopupPembayaranGagal)
        {
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentTopup())
                    .commit();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}