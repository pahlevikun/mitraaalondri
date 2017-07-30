package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 6/17/17.
 */

public class Message {

    private String id, idOrder, idUser, idKurir, idMitra, namaMitra, namaUser, invoice_number;

    public Message() {
    }

    public Message(String id, String idOrder, String idUser, String idKurir, String idMitra, String namaMitra, String namaUser,
                   String invoice_number) {
        this.id = id;
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idKurir = idKurir;
        this.idMitra = idMitra;
        this.namaMitra = namaMitra;
        this.namaUser = namaUser;
        this.invoice_number = invoice_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdKurir() {
        return idKurir;
    }

    public void setIdKurir(String idKurir) {
        this.idKurir = idKurir;
    }

    public String getIdMitra() {
        return idMitra;
    }

    public void setIdMitra(String idMitra) {
        this.idMitra = idMitra;
    }

    public String getNamaMitra() {
        return namaMitra;
    }

    public void setNamaMitra(String namaMitra) {
        this.namaMitra = namaMitra;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }
}
