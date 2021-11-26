package com.example.atmasalon.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.atmasalon.BR;

@Entity(tableName = "dataPelanggan")
public class Pelanggan extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "lokasiSalon")
    private String lokasiSalon;

    @ColumnInfo(name = "namaPemesan")
    private String namaPemesan;

    @ColumnInfo(name = "noTelp")
    private String noTelp;

    @ColumnInfo(name = "modelRambut")
    private String modelRambut;

    @ColumnInfo(name = "warnaRambut")
    private String warnaRambut;

    @ColumnInfo(name = "statusPembayaran")
    private String statusPembayaran;

    public Pelanggan(){}

    public Pelanggan(int userId, String lokasiSalon, String namaPemesan, String noTelp, String modelRambut, String warnaRambut, String statusPembayaran) {
        this.userId = userId;
        this.lokasiSalon = lokasiSalon;
        this.namaPemesan = namaPemesan;
        this.noTelp = noTelp;
        this.modelRambut = modelRambut;
        this.warnaRambut = warnaRambut;
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
}
