package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 12/15/16.
 */

public class Berita {
    public String id;
    public String title;
    public String isi;
    public String image;


    public Berita() {
    }

    public Berita(String id, String title, String isi, String image) {
        this.title = title;
        this.id = id;
        this.title = title;
        this.isi = isi;
        this.image = image;
    }


    public String getJudul() {
        return title;
    }

    public void setJudul(String name) {
        this.title = name;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getIsi() {
        return isi;
    }

    public void setGambar(String image) {
        this.image = image;
    }

    public String getGambar() {
        return image;
    }




}
