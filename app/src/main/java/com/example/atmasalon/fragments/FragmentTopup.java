package com.example.atmasalon.fragments;


import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.databinding.FragmentTopupBinding;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FragmentTopup extends Fragment implements View.OnClickListener{

    private FragmentTopupBinding binding;
    private BottomNavigationView bottomNav;
    private UserPreference userPref;
    private UserFromJson userLogin;
    private RequestQueue queue;

    public FragmentTopup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_topup, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());

        GetUserNowFromApi();
        bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        binding.btnTopupSaldo.setOnClickListener(this);

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Tambah Saldo");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTopupSaldo)
        {
            if(Validasi())
            {
                //Update Saldo
                float saldoBaru = userLogin.getSaldo() + Float.parseFloat(binding.inputLayoutTambahSaldo.getEditText().getText().toString());
                UpdateUser(saldoBaru);
            }
        }
    }

    private boolean Validasi()
    {
        try
        {
            String text = binding.inputLayoutTambahSaldo.getEditText().getText().toString();
            if(text.isEmpty())
            {
                Toast.makeText(this.getActivity(), "Silahkan isi data!", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if(Double.parseDouble(text) < 1)
            {
                Toast.makeText(this.getActivity(), "Masukkan jumlah lebih dari 0!", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this.getActivity(), "Silahkan isi dengan angka!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        MenuItem item = nav.getMenu().findItem(R.id.menu_beranda);
        item.setChecked(true);
    }
}