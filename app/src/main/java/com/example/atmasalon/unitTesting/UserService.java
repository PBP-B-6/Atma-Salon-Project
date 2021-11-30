package com.example.atmasalon.unitTesting;

import static com.android.volley.Request.Method.POST;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atmasalon.api.UserApi;
import com.example.atmasalon.entity.UserFromJson;
import com.example.atmasalon.entity.UserResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserService
{
    public void CreateUser(UserFromJson user, UserView view, Context con, UserCallback callback) {
        //TODO: Mau ada loading nda?
//        setLoading(true);

        final StringRequest stringRequest = new StringRequest(POST, UserApi.ADD_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        UserResponse userResponse =
                                gson.fromJson(response, UserResponse.class);

//                        Toast.makeText(RegisterActivity.this, userResponse.getMessage(),
//                                Toast.LENGTH_SHORT).show();


//                        Intent returnIntent = new Intent();
//                        setResult(Activity.RESULT_OK, returnIntent);

                        callback.onSuccess(true, user);

//                        setLoading(false);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                setLoading(false);

                try {
                    String responseBody =
                            new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    view.showUserError(error.getMessage());
                    callback.onError();

                } catch (Exception e) {
                    view.showErrorResponse(e.getMessage());
                    callback.onError();
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
                String requestBody = gson.toJson(user);


                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
        };

        RequestQueue queue = Volley.newRequestQueue(con);
        queue.add(stringRequest);
    }

    public boolean getValid(final UserView view, UserFromJson user, Context con)
    {
        final boolean[] bool = new boolean[1];
        CreateUser(user, view, con, new UserCallback() {
            @Override
            public void onSuccess(boolean value, UserFromJson user) {
                bool[0] = true;
            }

            @Override
            public void onError() {
                bool[0] = false;
            }
        });
        return bool[0];
    }
}
