package com.example.atmasalon.fragments;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.LoginActivity;
import com.example.atmasalon.R;
import com.example.atmasalon.RegisterActivity;
import com.example.atmasalon.api.PelangganApi;
import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.databinding.FragmentPembayaranBinding;
import com.example.atmasalon.entity.DataReservasi;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.entity.PelangganFromJson;
import com.example.atmasalon.entity.PelangganResponse;
import com.example.atmasalon.entity.User;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FragmentPembayaran extends Fragment implements View.OnClickListener{

    //TODO: nanti GetUser itu dari userPreferencenya
    private FragmentPembayaranBinding binding;
    private UserPreference userPref;
    private ReservationPreference reservationPreference;
    private BottomNavigationView bottomNav;
    private NotificationManagerCompat notificationManager;
    private String namaPemesan = "";
    private RequestQueue queue;
    private UserFromJson userLogin;
    private static final String CHANNEL_1 = "channel1";

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
        userPref = new UserPreference(this.getActivity());
        reservationPreference = new ReservationPreference(this.getActivity());
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());

        GetUserNowFromApi();

        bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        notificationManager = NotificationManagerCompat.from(this.getActivity());

        double totalHarga = reservationPreference.GetTotalHarga();
        String saldoStr = "Rp. " + String.format("%.0f", totalHarga) + ",00";
        binding.inputLayoutTotalHargaBerhasil.getEditText().setText(saldoStr);
        binding.btnBayarPembayaranBerhasil.setOnClickListener(this);

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

            // Kurang Saldo
            //TODO: 2 baris kode dibawah, dicut, dipindah didalam CreateOrder yoo
//

            //Insert Data
            DataReservasi reservasi = reservationPreference.GetAllData();
            namaPemesan = reservasi.getNamaPemesan();
            //TODO: ID diisi, id Pelanggannya, uncomment
//            Pelanggan data = new Pelanggan(reservasi.getLokasiSalon(), reservasi.getNamaPemesan(), reservasi.getNoTelp(), reservasi.getModelRambut(), reservasi.getWarnaRambut(), reservasi.getTotalHarga(), "Lunas");
            //TODO: AddData
//            AddDataPelanggan(data);
        }
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

    private void UpdateUser(float saldo) {
        //TODO: SetLoading
//        setLoading(true);

        userLogin.setSaldo(saldo);

        final StringRequest stringRequest = new StringRequest(PUT, UserApi.UPDATE_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse produkResponse =
                                gson.fromJson(response, UserResponse.class);

                        Toast.makeText(getActivity(), produkResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent();
                        getActivity().setResult(Activity.RESULT_OK, returnIntent);

                        userPref.SetSaldo(saldo);

                        //clear prefereceReserv
                        reservationPreference.ClearPreference();

                        //Firebase
                        SendOnChannel(FragmentPembayaran.this.getView());

                        ChangeToDashboard();

//                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setLoading(false);

                try {
                    String responseBody =
                            new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getActivity(), errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(userLogin);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    private void GetUserNowFromApi()
    {
        //TODO: set loding
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(GET, UserApi.GET_BY_ID_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

                        SetUserLogin(userResponse.getUser());

//                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setLoading(false);

                try {
                    String responseBody =
                            new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getActivity(), errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }
        };

        queue.add(stringRequest);
    }

    private void SetUserLogin(UserFromJson user)
    {
        this.userLogin = user;
    }

    private void ChangeToDashboard()
    {
        this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentDashboard())
                .commit();

        BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);
        nav.setSelectedItemId(R.id.menu_beranda);
    }

    private void CreateOrder() {
        //TODO: Mau ada loading nda?
//        setLoading(true);
        DataReservasi reservasi = reservationPreference.GetAllData();
        PelangganFromJson data = new PelangganFromJson(userPref.GetUserID(), reservasi.getLokasiSalon(), reservasi.getNamaPemesan(), reservasi.getNoTelp(), reservasi.getModelRambut(), reservasi.getWarnaRambut());

        final StringRequest stringRequest = new StringRequest(POST, PelangganApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PelangganResponse pelangganResponse =
                                gson.fromJson(response, PelangganResponse.class);

                        Toast.makeText(getContext(), pelangganResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        Intent returnIntent = new Intent();
                        getActivity().setResult(Activity.RESULT_OK, returnIntent);

                        float saldoNow = userLogin.getSaldo() - (float) reservationPreference.GetTotalHarga();
                        UpdateUser(saldoNow);
//                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setLoading(false);

                try {
                    String responseBody =
                            new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(getContext(), errors.getString("message"),
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(data);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }
}