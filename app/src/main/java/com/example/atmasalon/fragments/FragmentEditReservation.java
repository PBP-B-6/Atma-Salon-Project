package com.example.atmasalon.fragments;

import static com.android.volley.Request.Method.PUT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.R;
import com.example.atmasalon.api.PelangganApi;
import com.example.atmasalon.databinding.FragmentEditReservationBinding;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.entity.PelangganResponse;
import com.example.atmasalon.entity.UserResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FragmentEditReservation extends Fragment {
    private Pelanggan data;
    private FragmentEditReservationBinding binding;
    private RequestQueue queue;

    public FragmentEditReservation(Pelanggan data) {
        // Required empty public constructon
        this.data = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_reservation, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        binding.inputLayoutLokasiSalonEdit.getEditText().setText(data.getLokasiSalon());
        binding.inputLayoutNamaReservasiEdit.getEditText().setText(data.getNamaPemesan());
        binding.inputLayoutTelpReservasiEdit.getEditText().setText(data.getNoTelp());
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Validation())
                    UpdatePesanan();
                else
                    Toast.makeText(getActivity(), "Silahkan isi semua field!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdatePesanan()
    {
        //TODO: set loding
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(PUT, PelangganApi.UPDATE_URL + data.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PelangganResponse pelangganResponse =
                                gson.fromJson(response, PelangganResponse.class);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layout_fragment, new FragmentRiwayat())
                                .commit();
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

    private boolean Validation()
    {
        //Validasi untuk model dan warna juga
        String lokasi, nama, telp;
        lokasi = binding.inputLayoutLokasiSalonEdit.getEditText().getText().toString();
        nama = binding.inputLayoutNamaReservasiEdit.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasiEdit.getEditText().getText().toString();
        if(lokasi.isEmpty())
        {
            return false;
        }
        else if(nama.isEmpty())
        {
            return false;
        }
        else if(telp.isEmpty())
        {
            return false;
        }

        return true;
    }
}