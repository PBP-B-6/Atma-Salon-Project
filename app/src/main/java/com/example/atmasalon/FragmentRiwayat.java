package com.example.atmasalon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.entity.DataPelanggan;
import com.example.atmasalon.preferences.UserPreference;

import java.util.List;

public class FragmentRiwayat extends Fragment implements View.OnClickListener  {

    private FragmentRiwayatBinding binding;
    private UserPreference userPreference;
    private List<DataPelanggan> dataPelanggan;

    public FragmentRiwayat() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_riwayat, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreference = new UserPreference(getActivity());
        dataPelanggan = GetAll(userPreference.GetUserID());

        binding.btnMulaiReservasi.setOnClickListener(this);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Riwayat");

        if( dataPelanggan.isEmpty() ){
            binding.riwayatStatus.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Data Riwayat masih kosong!", Toast.LENGTH_SHORT).show();
        } else {
            binding.riwayatStatus.setVisibility(View.GONE);
            binding.rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
            binding.rvRiwayat.setAdapter(new rv_riwayatAdapter(dataPelanggan));
        }

    }

    public void onClick(View view) {
        if(view.getId() == R.id.btnMulaiReservasi) {
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentReservation2())
                    .commit();
        }
    }

    private List<DataPelanggan> GetAll(int userId)
    {
        return DatabaseUser.GetInstance(getContext()).GetDatabase().dataPelangganDao().GetAll(userId);
    }
}