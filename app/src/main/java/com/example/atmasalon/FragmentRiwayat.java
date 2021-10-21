package com.example.atmasalon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.entity.DataPelanggan;
import com.example.atmasalon.entity.DataReservasi;
import com.example.atmasalon.preferences.UserPreference;

import java.util.ArrayList;
import java.util.List;

public class FragmentRiwayat extends Fragment {

    private FragmentRiwayatBinding binding;
    private UserPreference userPreference;
    public List<DataPelanggan> dataPelanggan;

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

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Riwayat");

        binding.rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        binding.rvRiwayat.setAdapter(new rv_riwayatAdapter(dataPelanggan));

    }

    private List<DataPelanggan> GetAll(int userId)
    {
        return DatabaseUser.GetInstance(getContext()).GetDatabase().dataPelangganDao().GetAll(userId);
    }
}