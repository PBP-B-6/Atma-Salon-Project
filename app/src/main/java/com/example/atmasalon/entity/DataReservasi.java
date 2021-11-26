package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class DataReservasi extends BaseObservable {
    //DataReservasi merupakan Kelas yang digunakan sebagai kelas temp / penyimpanan sementara
    //  untuk data reservasi pengguna
    private String lokasiSalon;
    private String namaPemesan;
    private String noTelp;
    private String modelRambut;
    private String warnaRambut;
    private float totalHarga;

    public DataReservasi(){}


    public DataReservasi(String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, float totalHarga) {
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
        this.totalHarga = totalHarga;
    }

    @Bindable
    public String getLokasiSalon() {
        return lokasiSalon;
    }

    public void setLokasiSalon(String lokasiSalon) {
        this.lokasiSalon = lokasiSalon;
    }

    @Bindable
    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {
        this.namaPemesan = namaPemesan;
    }

    @Bindable
    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    @Bindable
    public String getModelRambut() {
        return modelRambut;
    }

    public void setModelRambut(String modelRambut) {
        this.modelRambut = modelRambut;
    }

    @Bindable
    public String getWarnaRambut() {
        return warnaRambut;
    }

    public void setWarnaRambut(String warnaRambut) {
        this.warnaRambut = warnaRambut;
    }

    @Bindable
    public float getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(float totalHarga) {
        this.totalHarga = totalHarga;
    }
}
