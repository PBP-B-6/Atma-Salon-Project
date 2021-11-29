package com.example.atmasalon.fragments;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.databinding.FragmentEditUserBinding;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FragmentEditUser extends Fragment implements View.OnClickListener{
    private FragmentEditUserBinding binding;
    private UserPreference userPref;
    private RequestQueue queue;
    private UserFromJson userLogin;

    public FragmentEditUser() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());

        GetUserNowFromApi();
        binding.btnEdit.setOnClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void UpdateUser() {
        //TODO: SetLoading
//        setLoading(true);

        userLogin.setNama(binding.inputLayoutNamaEdit.getEditText().getText().toString());
        userLogin.setNoTelp(binding.inputLayoutPhoneEdit.getEditText().getText().toString());

        if(GetKelamin())
        {
            userLogin.setJenisKelamin(1);
        }
        else
        {
            userLogin.setJenisKelamin(0);
        }


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

                        userPref.SetUserName(userLogin.getNama());

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

        SetBindingToText();
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

    private void SetBindingToText()
    {
        binding.inputLayoutNamaEdit.getEditText().setText(userLogin.getNama());
        binding.inputLayoutPhoneEdit.getEditText().setText(userLogin.getNoTelp());
        if(binding.radioGroupKelaminEdit.getCheckedRadioButtonId() == binding.radioPriaEdit.getId())
        {
            binding.radioPriaEdit.setChecked(true);
            binding.radioWanitaEdit.setChecked(false);
        }
        else
        {
            binding.radioPriaEdit.setChecked(false);
            binding.radioWanitaEdit.setChecked(true);
        }
    }

    private boolean GetKelamin()
    {
        if(binding.radioGroupKelaminEdit.getCheckedRadioButtonId() == binding.radioPriaEdit.getId())
        {
            //pria
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnEdit)
        {
            UpdateUser();


        }
    }
}