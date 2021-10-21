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

import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.entity.DataReservasi;

import java.util.ArrayList;

public class FragmentRiwayat extends Fragment {
    private FragmentRiwayatBinding binding;
    private rv_riwayatAdapter adapter;
    public ArrayList<DataReservasi> dataReservasi;
    private RecyclerView recyclerView;

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

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Riwayat");

        binding = DataBindingUtil.setContentView(this.getActivity(), R.layout.fragment_riwayat);

        dataReservasi = new ArrayList<>();
        binding.rvRiwayat.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false));
        binding.rvRiwayat.setAdapter(new rv_riwayatAdapter(dataReservasi));

    }


}