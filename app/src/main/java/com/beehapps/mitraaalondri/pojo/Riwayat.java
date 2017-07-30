package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 5/31/16.
 */
public class Riwayat {
    //private variables
    int _id;
    String _invoice;
    String _idr;
    String _jenis;
    String _pickup;

    // Empty constructor
    public Riwayat() {
    }

    // constructor
    public Riwayat(int id, String invoice, String idr, String jenis, String pickup) {
        this._id = id;
        this._invoice = invoice;
        this._idr = idr;
        this._jenis = jenis;
        this._pickup = pickup;
    }

    // constructor
    public Riwayat(String invoice, String idr, String jenis, String pickup) {
        this._invoice = invoice;
        this._idr = idr;
        this._jenis = jenis;
        this._pickup = pickup;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting ID
    public String getInvoice() {
        return this._invoice;
    }

    // setting id
    public void setInvoice(String invoice) {
        this._invoice = invoice;
    }

    // getting judul
    public String getIdr() {
        return this._idr;
    }

    // setting judul
    public void setIdr(String idr) {
        this._idr = idr;
    }

    // getting judul
    public String getJenis() {
        return this._jenis;
    }

    // setting judul
    public void setJenis(String jenis) {
        this._jenis = jenis;
    }

    // getting ID
    public String getPickup() {
        return this._pickup;
    }

    // setting id
    public void setPickup(String pickup) {
        this._pickup = pickup;
    }

}