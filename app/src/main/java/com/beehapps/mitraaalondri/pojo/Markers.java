package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 12/15/16.
 */

public class Markers {
    public String marker_id;
    public String mitra_id;
    public String nama_mitra;
    public String alamat_mitra;
    public String telepon_mitra;
    public Double Lat;
    public Double Long;


    public Markers() {
    }

    public Markers(String marker_id, String mitra_id, String nama_mitra, Double Lat, Double Long, String alamat_mitra, String telepon_mitra) {
        this.marker_id = marker_id;
        this.mitra_id = mitra_id;
        this.nama_mitra = nama_mitra;
        this.Lat = Lat;
        this.Long = Long;
        this.alamat_mitra = alamat_mitra;
        this.telepon_mitra = telepon_mitra;
    }


    public void setMitra_id(String mitra_id) {
        this.mitra_id = mitra_id;
    }

    public String getMitra_id() {
        return mitra_id;
    }


    public void setNama_mitra(String nama_mitra) {
        this.nama_mitra = nama_mitra;
    }

    public String getNama_mitra() {
        return nama_mitra;
    }


    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLong() {
        return Long;
    }

    public void setLong(Double l) {
        Long = l;
    }


    public void setAlamat_mitra(String alamat_mitra) {
        this.alamat_mitra = alamat_mitra;
    }

    public String getAlamat_mitra() {
        return alamat_mitra;
    }


    public void setTelepon_mitra(String telepon_mitra) {
        this.telepon_mitra = telepon_mitra;
    }

    public String getTelepon_mitra() {
        return telepon_mitra;
    }

}
