package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 5/28/17.
 */

public class Alamat {

    private int _id;
    private String namaAlamat;
    private String alamatAlamat;
    private String deskripsiAlamat;
    private String kodeposAlamat;
    private String namaUser;
    private String teleponUser;
    private String latUser;
    private String lngUser;

    public Alamat() {
    }


    // constructor
    public Alamat(int _id, String namaAlamat, String alamatAlamat, String deskripsiAlamat, String kodeposAlamat, String namaUser, String teleponUser, String latUser, String lngUser) {
        this._id = _id;
        this.namaAlamat = namaAlamat;
        this.alamatAlamat = alamatAlamat;
        this.deskripsiAlamat = deskripsiAlamat;
        this.kodeposAlamat = kodeposAlamat;
        this.namaUser = namaUser;
        this.teleponUser = teleponUser;
        this.latUser = latUser;
        this.lngUser = lngUser;
    }

    // constructor
    public Alamat(String namaAlamat, String alamatAlamat, String deskripsiAlamat, String kodeposAlamat, String namaUser, String teleponUser, String latUser, String lngUser) {
        this.namaAlamat = namaAlamat;
        this.alamatAlamat = alamatAlamat;
        this.deskripsiAlamat = deskripsiAlamat;
        this.kodeposAlamat = kodeposAlamat;
        this.namaUser = namaUser;
        this.teleponUser = teleponUser;
        this.latUser = latUser;
        this.lngUser = lngUser;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNamaAlamat() {
        return namaAlamat;
    }

    public void setNamaAlamat(String namaAlamat) {
        this.namaAlamat = namaAlamat;
    }

    public String getAlamatAlamat() {
        return alamatAlamat;
    }

    public void setAlamatAlamat(String alamatAlamat) {
        this.alamatAlamat = alamatAlamat;
    }

    public String getDeskripsiAlamat() {
        return deskripsiAlamat;
    }

    public void setDeskripsiAlamat(String deskripsiAlamat) {
        this.deskripsiAlamat = deskripsiAlamat;
    }

    public String getKodeposAlamat() {
        return kodeposAlamat;
    }

    public void setKodeposAlamat(String kodeposAlamat) {
        this.kodeposAlamat = kodeposAlamat;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getTeleponUser() {
        return teleponUser;
    }

    public void setTeleponUser(String teleponUser) {
        this.teleponUser = teleponUser;
    }

    public String getLatUser() {
        return latUser;
    }

    public void setLatUser(String latUser) {
        this.latUser = latUser;
    }

    public String getLngUser() {
        return lngUser;
    }

    public void setLngUser(String lngUser) {
        this.lngUser = lngUser;
    }
}
