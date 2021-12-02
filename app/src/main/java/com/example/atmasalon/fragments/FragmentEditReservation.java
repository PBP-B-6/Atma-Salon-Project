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
import android.view.WindowManager;
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
import com.example.atmasalon.entity.PelangganFromJson;
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
            }
        });
    }

    private void UpdatePesanan()
    {
        setLoading(true);

        data.setLokasiSalon(binding.inputLayoutLokasiSalonEdit.getEditText().getText().toString());
        data.setNamaPemesan(binding.inputLayoutNamaReservasiEdit.getEditText().getText().toString());
        data.setNoTelp(binding.inputLayoutTelpReservasiEdit.getEditText().getText().toString());

        final StringRequest stringRequest = new StringRequest(PUT, PelangganApi.UPDATE_URL + data.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
//                        PelangganResponse pelangganResponse =
//                                gson.fromJson(response, PelangganResponse.class);
                        PelangganFromJson pelangganFromJson = gson.fromJson(response, PelangganFromJson.class);

                        Toast.makeText(getActivity(), "Berhasil Mengubah Reservasi",
                                Toast.LENGTH_SHORT).show();

                        setLoading(false);

                        getActivity()
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layout_fragment, new FragmentRiwayat())
                                .commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

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
                String requestBody = gson.toJson(data);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    private boolean Validation()
    {
        String lokasi, nama, telp;
        String regexPhone = "08+[0-9]{8,11}";
        lokasi = binding.inputLayoutLokasiSalonEdit.getEditText().getText().toString();
        nama = binding.inputLayoutNamaReservasiEdit.getEditText().getText().toString();
        telp = binding.inputLayoutTelpReservasiEdit.getEditText().getText().toString();
        if(lokasi.isEmpty())
        {
            Toast.makeText(getActivity(), "Lokasi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(nama.isEmpty())
        {
            Toast.makeText(getActivity(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(telp.isEmpty())
        {
            Toast.makeText(getActivity(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(telp.length() < 10 || telp.length() > 13)
        {
            Toast.makeText(getActivity(), "Nomor Telepon tidak boleh < 10 dan > 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!telp.matches(regexPhone))
        {
            Toast.makeText(getActivity(), "Nomor Telepon tidak sesuai format", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(telp.length() < 10 || telp.length() > 13)
        {
            Toast.makeText(getActivity(), "Nomor Telepon tidak boleh < 10 dan > 13 digit", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            binding.layoutLoading.setVisibility(View.GONE);
        }
    }
}