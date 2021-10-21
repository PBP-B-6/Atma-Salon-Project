package com.example.atmasalon;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.atmasalon.databinding.RvRiwayatBinding;
import com.example.atmasalon.entity.DataPelanggan;

import java.util.List;

public class rv_riwayatAdapter extends RecyclerView.Adapter<rv_riwayatAdapter.MyViewHolder>{
    private List<DataPelanggan> listReservasi;

    public rv_riwayatAdapter(List<DataPelanggan> listReservasi) {
        this.listReservasi = listReservasi;
    }


    @NonNull
    @Override
    public rv_riwayatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvRiwayatBinding rvRiwayatBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.rv_riwayat, parent,false);
        return new MyViewHolder(rvRiwayatBinding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataPelanggan data = listReservasi.get(position);
        holder.binding.setVariable(BR.data, data);
    }

    @Override
    public int getItemCount() {
        return listReservasi.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private RvRiwayatBinding binding;

        public MyViewHolder(@NonNull RvRiwayatBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}