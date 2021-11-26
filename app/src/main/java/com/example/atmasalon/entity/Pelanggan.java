package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.atmasalon.BR;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "dataPelanggan")
public class Pelanggan extends BaseObservable {
    //TODO: Sesuaiin sama database baru, mungkin ada delet2 beberapa
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("idUser")
    @ColumnInfo(name = "userId")
    private int userId;

    @SerializedName("lokasiSalon")
    @ColumnInfo(name = "lokasiSalon")
    private String lokasiSalon;

    @SerializedName("namaPemesan")
    @ColumnInfo(name = "namaPemesan")
    private String namaPemesan;

    @SerializedName("noTelp")
    @ColumnInfo(name = "noTelp")
    private String noTelp;

    @SerializedName("modelRambut")
    @ColumnInfo(name = "modelRambut")
    private String modelRambut;

    @SerializedName("warnaRambut")
    @ColumnInfo(name = "warnaRambut")
    private String warnaRambut;

    @SerializedName("totalHarga")
    private float totalHarga;

    @SerializedName("statusPembayaran")
    @ColumnInfo(name = "statusPembayaran")
    private String statusPembayaran;

    public Pelanggan(){}

    //TODO: Constructor hapus ini, trs yang baru, kalo tidak perlu, kurangi parameter
    public Pelanggan(int userId, String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, String statusPembayaran) {
        this.userId = userId;
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
        this.statusPembayaran = statusPembayaran;
    }

    public Pelanggan(int userId, String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, float total, String statusPembayaran) {
        this.userId = userId;
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
        totalHarga = total;
        this.statusPembayaran = statusPembayaran;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
