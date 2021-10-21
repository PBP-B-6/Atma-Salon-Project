package com.example.atmasalon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.databinding.RvRiwayatBinding;
import com.example.atmasalon.entity.DataReservasi;

import java.util.ArrayList;

public class rv_riwayatAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
    private ArrayList<DataReservasi> listReservasi;

    public rv_riwayatAdapter(ArrayList<DataReservasi> listReservasi) {
        this.listReservasi = listReservasi;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View statusContainer = inflater.inflate(R.layout.rv_riwayat, parent,false);
        return new RecyclerViewHolder(statusContainer);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        DataReservasi dataReservasi = listReservasi.get(position);
        holder.bind(dataReservasi);
    }

    @Override
    public int getItemCount() {
        return listReservasi.size();
    }
}
