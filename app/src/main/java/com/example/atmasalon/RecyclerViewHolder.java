package com.example.atmasalon;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.atmasalon.databinding.RvRiwayatBinding;
import com.example.atmasalon.entity.DataReservasi;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private RvRiwayatBinding binding;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public void bind(DataReservasi dataReservasi) {
        binding.setData(dataReservasi);
    }
}
