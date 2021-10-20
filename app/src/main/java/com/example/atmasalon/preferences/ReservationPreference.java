package com.example.atmasalon.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.atmasalon.entity.DataReservasi;
import com.example.atmasalon.entity.UserLogin;

public class ReservationPreference {
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;
    Context con;

    public static final String IS_FILLED = "false";
    private static final String KEY_LOKASI = "lokasi";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_NOTELP = "telp";
    private static final String KEY_MODELRAMBUT = "model";
    private static final String KEY_WARNARAMBUT = "warna";
    private static final String KEY_TOTALHARGA = "22000";

    public ReservationPreference(Context C)
    {
        con = C;
        sharedPreference = C.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        editor = sharedPreference.edit();
    }

    public void SetFilled()
    {
        editor.putBoolean(IS_FILLED, true);
        editor.commit();
    }

    public void FillDataPage2(String lokasi, String nama, String telp, String model, String warna, double totalHarga)
    {
        editor.putString(KEY_LOKASI, lokasi);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_NOTELP, telp);
        editor.putString(KEY_MODELRAMBUT, model);
        editor.putString(KEY_WARNARAMBUT, warna);
        editor.putString(KEY_TOTALHARGA, String.valueOf(totalHarga));
        editor.commit();
    }

    public DataReservasi GetAllData()
    {
        String lokasi, nama, telp, model, warna;
        lokasi = sharedPreference.getString(KEY_LOKASI, "");
        nama = sharedPreference.getString(KEY_NAMA, "");
        telp = sharedPreference.getString(KEY_NOTELP, "");
        model = sharedPreference.getString(KEY_MODELRAMBUT, "");
        warna = sharedPreference.getString(KEY_WARNARAMBUT, "");

        double total = Double.parseDouble(sharedPreference.getString(KEY_TOTALHARGA, "0"));
        return new DataReservasi(lokasi, nama, telp, model, warna, total);
    }

    public double GetTotalHarga()
    {
        return Double.parseDouble(sharedPreference.getString(KEY_TOTALHARGA, "0"));
    }

    public boolean GetIsFilled()
    {
        return sharedPreference.getBoolean(IS_FILLED, false);
    }

    public String GetLokasi()
    {
        return sharedPreference.getString(KEY_LOKASI, null);
    }

    public void ClearPreference()
    {
        editor.putString(KEY_LOKASI, "");
        editor.putString(KEY_NAMA, "");
        editor.putString(KEY_NOTELP, "");
        editor.putString(KEY_MODELRAMBUT, "");
        editor.putString(KEY_WARNARAMBUT, "");
        editor.putString(KEY_TOTALHARGA, "0");
        editor.putBoolean(IS_FILLED, false);
        editor.commit();
    }
}
