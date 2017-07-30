package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 5/29/17.
 */

public class TipeLondri {

    private String nama;
    private int harga;

    public TipeLondri() {
    }

    public TipeLondri(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
