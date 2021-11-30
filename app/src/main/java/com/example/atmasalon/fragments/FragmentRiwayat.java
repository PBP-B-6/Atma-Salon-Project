package com.example.atmasalon.fragments;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.example.atmasalon.R;
import com.example.atmasalon.api.PelangganApi;
import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.databinding.FragmentRiwayatBinding;
import com.example.atmasalon.entity.Pelanggan;
import com.example.atmasalon.entity.PelangganResponse;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.UserPreference;
import com.example.atmasalon.adapter.rv_riwayatAdapter;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentRiwayat extends Fragment implements View.OnClickListener  {

    private FragmentRiwayatBinding binding;
    private UserPreference userPreference;
    private List<Pelanggan> pelanggan;
    private RequestQueue queue;

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

        userPreference = new UserPreference(getActivity());
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        //TODO: GetAll Data diperbaiki
//        pelanggan = GetAll(userPreference.GetUserID());
        GetPelanggan();

        binding.btnMulaiReservasi.setOnClickListener(this);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Riwayat");

    }

    public void onClick(View view) {
        if(view.getId() == R.id.btnMulaiReservasi) {
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentReservation2())
                    .commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void DeletePelanggan(int id) {
        //TODO: Set Loading
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(DELETE, PelangganApi.DELETE_URL + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

//                        setLoading(false);
                        Toast.makeText(getActivity(), userResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

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

    private void GetPelanggan()
    {
        //TODO: set loding
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(GET, PelangganApi.GET_URL + userPreference.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        PelangganResponse pelangganResponse =
                                gson.fromJson(response, PelangganResponse.class);

                        pelanggan = pelangganResponse.getPelangganList();

                        if( pelanggan.isEmpty() ){
                            binding.riwayatStatus.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Data Riwayat masih kosong!", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.riwayatStatus.setVisibility(View.GONE);
                            binding.rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
                            binding.rvRiwayat.setAdapter(new rv_riwayatAdapter(pelanggan,getFragmentManager()));
                        }

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
        };

        queue.add(stringRequest);
    }


}