package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

public class PelangganFromJson extends BaseObservable {
    @SerializedName("id")
    private int id = 0;

    @SerializedName("idUser")
    private int idUser = 0;

    @SerializedName("lokasiSalon")
    private String lokasiSalon;

    @SerializedName("namaPemesan")
    private String namaPemesan;

    @SerializedName("noTelp")
    private String noTelp;

    @SerializedName("modelRambut")
    private String modelRambut;

    @SerializedName("warnaRambut")
    private String warnaRambut;

    @SerializedName("totalHarga")
    private float totalHarga;

    @SerializedName("statusPembayaran")
    private String statusPembayaran;

    public PelangganFromJson(){}

    public PelangganFromJson(int idUser, String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, float totalHarga, String statusPembayaran) {
        this.idUser = idUser;
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
        this.totalHarga = totalHarga;
        this.statusPembayaran = statusPembayaran;
    }

    public PelangganFromJson(int idUser, String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut) {
        this.idUser = idUser;
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
    }

    @Bindable
    public String getLokasiSalon() {
        return lokasiSalon;
    }

    public void setLokasiSalon(String lokasiSalon) {
        this.lokasiSalon = lokasiSalon;
        notifyPropertyChanged(BR.lokasiSalon);
    }

    @Bindable
    public String getNamaPemesan() {
        return namaPemesan;
    }

    public void setNamaPemesan(String namaPemesan) {

        this.namaPemesan = namaPemesan;
        notifyPropertyChanged(BR.namaPemesan);
    }

    @Bindable
    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
        notifyPropertyChanged(BR.noTelp);
    }

    @Bindable
    public String getModelRambut() {
        return modelRambut;
    }

    public void setModelRambut(String modelRambut) {
        this.modelRambut = modelRambut;
        notifyPropertyChanged(BR.modelRambut);
    }

    @Bindable
    public String getWarnaRambut() {
        return warnaRambut;
    }

    public void setWarnaRambut(String warnaRambut) {
        this.warnaRambut = warnaRambut;
        notifyPropertyChanged(BR.warnaRambut);
    }

    @Bindable
    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
        notifyPropertyChanged(BR.statusPembayaran);
    }

    @Bindable
    public float getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(float totalHarga) {
        this.totalHarga = totalHarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
