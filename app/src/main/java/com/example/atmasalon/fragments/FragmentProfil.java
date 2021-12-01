package com.example.atmasalon.fragments;

import static android.app.Activity.RESULT_OK;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.atmasalon.api.TestimoniApi;
import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.databinding.FragmentProfilBinding;
import com.example.atmasalon.entity.Testimoni;
import com.example.atmasalon.entity.TestimoniFromJson;
import com.example.atmasalon.entity.TestimoniResponse;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.example.atmasalon.preferences.ReservationPreference;
import com.example.atmasalon.preferences.UserPreference;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentProfil extends Fragment implements View.OnClickListener
{
    private FragmentProfilBinding binding;
    private UserPreference userPref;
    private ReservationPreference reservationPreference;
    private Bitmap bitmap = null;
    private RequestQueue queue;
    private UserFromJson userLogin;

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private static final int CAMERA_REQUEST = 2;

    private Testimoni testimoni;
    private TestimoniFromJson testimoniFJ;

        //TODO:Delete User jgn lupa
    public FragmentProfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPref = new UserPreference(this.getActivity());
        queue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        reservationPreference = new ReservationPreference(this.getActivity());

        GetUserNowFromApi();

        testimoni = new Testimoni();
        binding.setTestimoni(testimoni);

        binding.namaProfil.setText(userPref.GetNamaUser());
        binding.emailProfil.setText(userPref.GetUserLogin().getEmail());
        binding.btnKeluar.setOnClickListener(this);
        binding.btnTambahSaldo.setOnClickListener(this);
        binding.profileImage.setOnClickListener(this);
        binding.btnEditProfil.setOnClickListener(this);
        binding.btnDeleteUser.setOnClickListener(this);

        binding.btnTambahTestimoni.setOnClickListener(this);
        binding.btnEditTestimoni.setOnClickListener(this);
        binding.btnHapusTestimoni.setOnClickListener(this);

        //TODO: Cek apakah ada bug disini? inni dilakukan setelah backend jalan
        if(userPref.GetURLProfilePic() != null)
        {
            Bitmap img = null;
            img = Base64ToBitmap(userPref.GetURLProfilePic());
            if(img != null)
            {
                binding.profileImage.setImageBitmap(img);
            }
        }

        TextView text = getActivity().findViewById(R.id.page_name);
        text.setText("Profil");
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnTambahSaldo)
        {
            this.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, new FragmentTopup())
                .commit();
        }
        else if(view.getId() == R.id.btnEditProfil)
        {
            this.getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, new FragmentEditUser())
                    .commit();
        }
        else if(view.getId() == R.id.btnKeluar)
        {
            Logout();
        }
        else if(view.getId() == R.id.profile_image)
        {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED) {
                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_REQUEST_CAMERA);
            }

            if(getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Fragment frag = this;
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                frag.startActivityForResult(intent, CAMERA_REQUEST);
            }
        }
        else if(view.getId() == R.id.btnDeleteUser)
        {
            MaterialAlertDialogBuilder materialAlertDialogBuilder =
                    new MaterialAlertDialogBuilder(getActivity());
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menutup akun anda?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DeleteUser();
                        }
                    })
                    .show();
        }
        else if(view.getId() == R.id.btnTambahTestimoni)
        {
            if(Validasi())
            {
                CreateTestimoni();
            }
        }
        else if(view.getId() == R.id.btnEditTestimoni)
        {
            if(Validasi())
            {
                UpdateTestimoni();
            }
        }
        else if(view.getId() == R.id.btnHapusTestimoni)
        {
            if(Validasi())
            {
                DeleteTestimoni();
            }
        }
    }

    private boolean Validasi()
    {
        if(CekKosong())
        {
            return true;
        }
        else
        {
            Toast.makeText(getContext(), "Testimoni belum ditambahkan!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean CekKosong()
    {
        if(binding.inputLayoutTestimoni.getEditText().getText().toString().isEmpty())
        {
            return false;
        }

        return true;
    }

    private void CreateTestimoni() {
        setLoading(true);
        Testimoni data = binding.getTestimoni();

        TestimoniFromJson testimoni = new TestimoniFromJson(userPref.GetUserID(), data.getTestimoni());

        final StringRequest stringRequest = new StringRequest(POST, TestimoniApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
//                        TestimoniResponse testimoniResponse =
//                                gson.fromJson(response, TestimoniResponse.class);

                        TestimoniFromJson testi =
                                gson.fromJson(response, TestimoniFromJson.class);

                        Toast.makeText(getContext(), "Testimoni berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();

                        binding.btnTambahTestimoni.setVisibility(View.GONE);
                        binding.wrapButton.setVisibility(View.VISIBLE);

                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

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
                String requestBody = gson.toJson(testimoni);


                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    private void UpdateTestimoni() {
        setLoading(true);

        testimoni.setTestimoni(binding.inputLayoutTestimoni.getEditText().getText().toString());

        final StringRequest stringRequest = new StringRequest(PUT, TestimoniApi.UPDATE_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
//                        TestimoniResponse testimoniResponse =
//                                gson.fromJson(response, TestimoniResponse.class);

                        TestimoniFromJson testi =
                                gson.fromJson(response, TestimoniFromJson.class);

                        Toast.makeText(getActivity(), "Update Testimoni berhasil",
                                Toast.LENGTH_SHORT).show();

                        setLoading(false);
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
                String requestBody = gson.toJson(testimoni);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    public void DeleteTestimoni() {
        setLoading(true);

        final StringRequest stringRequest = new StringRequest(DELETE, TestimoniApi.DELETE_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        TestimoniFromJson testi =
                                gson.fromJson(response, TestimoniFromJson.class);

                        Toast.makeText(getActivity(), "Delete Testimoni berhasil",
                                Toast.LENGTH_SHORT).show();

                        binding.inputLayoutTestimoni.getEditText().setText("");
                        binding.btnTambahTestimoni.setVisibility(View.VISIBLE);
                        binding.wrapButton.setVisibility(View.GONE);
                        setLoading(false);

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
        };

        queue.add(stringRequest);
    }

    private void GetTestimoni()
    {
        final StringRequest stringRequest = new StringRequest(GET, TestimoniApi.GET_BY_ID_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        TestimoniResponse testimoniResponse =
                                gson.fromJson(response, TestimoniResponse.class);

//                        TestimoniFromJson newTesti = gson.fromJson(response, TestimoniFromJson.class);
//                        String helo = newTesti.getTesti();

                        if( testimoniResponse != null && testimoniResponse.getTestimoni().size() != 0 ){
                            testimoniFJ = testimoniResponse.getTestimoni().get(0);
                            if( testimoniFJ.getTesti().isEmpty() ){
                                binding.btnTambahTestimoni.setVisibility(View.VISIBLE);
                                binding.wrapButton.setVisibility(View.GONE);
                            } else {
                                binding.btnTambahTestimoni.setVisibility(View.GONE);
                                binding.wrapButton.setVisibility(View.VISIBLE);
                                binding.inputLayoutTestimoni.getEditText().setText(testimoniFJ.getTesti());
                            }
                        } else {
                            binding.btnTambahTestimoni.setVisibility(View.VISIBLE);
                            binding.wrapButton.setVisibility(View.GONE);
                        }

                        setLoading(false);
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
        };

        queue.add(stringRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this.getActivity(), "Permission denied.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if (data == null)
                return;

            if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
                bitmap = (Bitmap) data.getExtras().get("data");
            }

            bitmap = getResizedBitmap(bitmap, 512);
            String stringBitmap = bitmapToBase64(bitmap);

            userPref.SetURLProfilePic(stringBitmap);
            binding.profileImage.setImageBitmap(bitmap);
            UpdateUser(stringBitmap);

            CircleImageView activity = getActivity().findViewById(R.id.profile_image_cont);
            activity.setImageBitmap(bitmap); // Setiap kali update, foto di container activity juga diupdate
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        if (bitmap == null)
            return null;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    private Bitmap Base64ToBitmap(String encodedImage)
    {
        if(encodedImage.isEmpty() || encodedImage == null){return null;}
        try
        {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void UpdateUser(String url) {
        setLoading(true);

        userLogin.setUrlGambar(url);

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

                        setLoading(false);
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
                String requestBody = gson.toJson(userLogin);

                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        queue.add(stringRequest);
    }

    private void GetUserNowFromApi()
    {
        setLoading(true);

        final StringRequest stringRequest = new StringRequest(GET, UserApi.GET_BY_ID_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

                        SetUserLogin(userResponse.getUser());
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
        };

        queue.add(stringRequest);
    }

    private void SetUserLogin(UserFromJson user)
    {
        this.userLogin = user;
        GetTestimoni();
    }

    public void DeleteUser() {
        setLoading(true);

        final StringRequest stringRequest = new StringRequest(DELETE, UserApi.DELETE_URL + userPref.GetUserID(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

                        setLoading(false);
                        Toast.makeText(getActivity(), userResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        Logout();
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
        };

        queue.add(stringRequest);
    }

    private void Logout()
    {
        userPref.Logout();
        reservationPreference.ClearPreference();
        Intent move = new Intent(this.getActivity(), LoginActivity.class);
        startActivity(move);
        getActivity().finish();
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