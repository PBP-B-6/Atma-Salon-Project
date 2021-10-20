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

import com.example.atmasalon.database.DatabaseUser;
import com.example.atmasalon.databinding.FragmentDashboardBinding;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.preferences.UserPreference;

public class FragmentDashboard extends Fragment {

    private FragmentDashboardBinding binding;
    private UserPreference userPref;
    private User userNow;

    public FragmentDashboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        userNow = GetUser();
        binding.dashboardProfileName.setText(userNow.getNama());
        double saldo = userNow.getSaldo();
        String saldoStr = "Rp. " + String.format("%.0f", saldo) + ",00";
        binding.dashboardProfileSaldo.setText(saldoStr);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Dashboard");

    }

    private User GetUser()
    {
        return DatabaseUser.GetInstance(getActivity().getApplicationContext()).GetDatabase().userDao().GetUser(userPref.GetUserID());
    }
}