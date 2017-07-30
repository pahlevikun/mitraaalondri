package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 5/28/17.
 */

public class Mitra {

    private int _id;
    private String id_mitra, nama_mitra, alamat_mitra, deskripsi_mitra;
    public Double lat;
    public Double lng;

    public Mitra() {
    }

    // constructor
    public Mitra(int _id, String id_mitra, String nama_mitra, String alamat_mitra, String deskripsi_mitra, Double lat, Double lng) {
        this._id = _id;
        this.id_mitra = id_mitra;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.deskripsi_mitra = deskripsi_mitra;
        this.lat = lat;
        this.lng = lng;
    }

    // constructor
    public Mitra( String id_mitra, String nama_mitra, String alamat_mitra, String deskripsi_mitra, Double lat, Double lng) {
        this.id_mitra = id_mitra;
        this.nama_mitra = nama_mitra;
        this.alamat_mitra = alamat_mitra;
        this.deskripsi_mitra = deskripsi_mitra;
        this.lat = lat;
        this.lng = lng;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getId_mitra() {
        return id_mitra;
    }

    public void setId_mitra(String id_mitra) {
        this.id_mitra = id_mitra;
    }

    public String getNama_mitra() {
        return nama_mitra;
    }

    public void setNama_mitra(String nama_mitra) {
        this.nama_mitra = nama_mitra;
    }

    public String getAlamat_mitra() {
        return alamat_mitra;
    }

    public void setAlamat_mitra(String alamat_mitra) {
        this.alamat_mitra = alamat_mitra;
    }

    public String getDeskripsi_mitra() {
        return deskripsi_mitra;
    }

    public void setDeskripsi_mitra(String deskripsi_mitra) {
        this.deskripsi_mitra = deskripsi_mitra;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
