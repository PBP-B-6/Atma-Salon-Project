package com.example.atmasalon.fragments;

import android.app.Notification;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atmasalon.R;
import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentPembayaranBinding;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.entity.DataReservasi;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentPembayaran extends Fragment implements View.OnClickListener{

    private FragmentPembayaranBinding binding;
    private UserPreference userPreference;
    private ReservationPreference reservationPreference;
    private BottomNavigationView bottomNav;
    private NotificationManagerCompat notificationManager;
    private static final String CHANNEL_1 = "channel1";
    private String namaPemesan = "";

    public FragmentPembayaran() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pembayaran, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBayarPembayaranBerhasil.setOnClickListener(this);
        userPreference = new UserPreference(this.getActivity());
        reservationPreference = new ReservationPreference(this.getActivity());

        bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        notificationManager = NotificationManagerCompat.from(this.getActivity());

        double totalHarga = reservationPreference.GetTotalHarga();
        String saldoStr = "Rp. " + String.format("%.0f", totalHarga) + ",00";
        binding.inputLayoutTotalHargaBerhasil.getEditText().setText(saldoStr);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Pembayaran");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnLinkTopupPembayaran)
        {
            this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentTopup())
                .commit();
        }
        else if(view.getId() == R.id.btnBayarPembayaranBerhasil)
        {

            // kurang saldo
            User user = GetUser();
            double saldo = user.getSaldo() - reservationPreference.GetTotalHarga();
            user.setSaldo(saldo);
            UpdateUserSaldo(user);

            //Insert Data
            DataReservasi reservasi = reservationPreference.GetAllData();
            namaPemesan = reservasi.getNamaPemesan();
            Pelanggan data = new Pelanggan(userPreference.GetUserID(), reservasi.getLokasiSalon(), reservasi.getNamaPemesan(),
                    reservasi.getNoTelp(), reservasi.getModelRambut(), reservasi.getWarnaRambut(), "Lunas");
            AddDataPelanggan(data);

            //clear prefereceReserv
            reservationPreference.ClearPreference();

            //Firebase
        }
    }

    private void UpdateUserSaldo(User user) {
        class UpdatingTodo extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.GetInstance(getActivity()).GetDatabase().userDao().UpdateUser(user);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
            }
        }
        UpdatingTodo up = new UpdatingTodo();
        up.execute();
    }

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPreference.GetUserID());
    }

    private void AddDataPelanggan(Pelanggan data)
    {

        class AddingUser extends AsyncTask<Void, Void, Void>
        {
            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().dataPelangganDao().InsertDataPelanggan(data);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(getActivity(), "Berhasil menambah data", Toast.LENGTH_SHORT).show();
                SendOnChannel(getView());

                getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                        //Fargment Dashboard gant riwayat
                    .replace(R.id.layout_fragment, new FragmentDashboard())
                    .commit();

                bottomNav.setSelectedItemId(R.id.menu_beranda);
            }
        }
        AddingUser Add = new AddingUser();
        Add.execute();
    }

    public void SendOnChannel(View v)
    {
        String title = "Reservasi Berhasil!";
        String message = namaPemesan + " telah berhasil melakukan reservasi!";
        Notification notification = new NotificationCompat.Builder(this.getActivity(), CHANNEL_1)
                .setSmallIcon(R.drawable.logo)
                .setContentText(message)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(2, notification);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}