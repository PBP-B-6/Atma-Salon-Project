package com.example.atmasalon.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.atmasalon.BR;
import com.example.atmasalon.ContainerActivity;
import com.example.atmasalon.R;
import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.databinding.RvRiwayatBinding;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.fragments.FragmentDashboard;
import com.example.atmasalon.fragments.FragmentEditReservation;
import com.example.atmasalon.fragments.FragmentRiwayat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class rv_riwayatAdapter extends RecyclerView.Adapter<rv_riwayatAdapter.MyViewHolder>{
    private List<Pelanggan> listReservasi;
    private FragmentManager fm;

    public rv_riwayatAdapter(List<Pelanggan> listReservasi, FragmentManager fm) {
        this.listReservasi = listReservasi;
        this.fm = fm;
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
        Pelanggan data = listReservasi.get(position);
        holder.binding.setVariable(BR.data, data);
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(view.getContext());
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus data mahasiswa ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FragmentManager fragmentManager  = fm;
                                Fragment currentFragment = fragmentManager.findFragmentById(R.id.layout_fragment);

                                if(currentFragment instanceof FragmentRiwayat) {
                                    ((FragmentRiwayat) currentFragment).DeletePelanggan(data.getId(), data);
                                    notifyItemRemoved(position);
                                }

                            }
                        })
                        .show();
            }
        });
        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentEditReservation(data))
                    .commit();

            }
        });
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
