package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

public class Pelanggan extends BaseObservable {
    //TODO: Sesuaiin sama database baru, mungkin ada delet2 beberapa

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

    public Pelanggan(){}

    //TODO: Constructor hapus ini, trs yang baru, kalo tidak perlu, kurangi parameter
    public Pelanggan(String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, float totalHarga, String statusPembayaran) {
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
        this.totalHarga = totalHarga;
        this.statusPembayaran = statusPembayaran;
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
}
