package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 6/17/17.
 */

public class OrderFrag {

    private String id, idOrder, user_id, kurir_id, mitra_id, nama_jalan, nama_alias, phone_alias, invoice_number,
            total_harga, detail_lokasi, catatan, alamat, is_ekspress, tanggal_mulai, tanggal_akhir,
            waktu_mulai, waktu_akhir, is_byitem, status, totalBayar, lat, lng, berat;

    public OrderFrag() {
    }

    public OrderFrag(String id, String idOrder, String user_id, String kurir_id, String mitra_id, String nama_jalan, String nama_alias, String phone_alias,
                     String invoice_number, String total_harga, String detail_lokasi, String catatan, String alamat, String is_ekspress,
                     String tanggal_mulai, String tanggal_akhir, String waktu_mulai, String waktu_akhir, String is_byitem,
                     String status, String totalBayar, String lat, String lng, String berat) {
        this.id = id;
        this.idOrder = idOrder;
        this.user_id = user_id;
        this.kurir_id = kurir_id;
        this.mitra_id = mitra_id;
        this.nama_jalan = nama_jalan;
        this.nama_alias = nama_alias;
        this.phone_alias = phone_alias;
        this.invoice_number = invoice_number;
        this.total_harga = total_harga;
        this.detail_lokasi = detail_lokasi;
        this.catatan = catatan;
        this.alamat = alamat;
        this.is_ekspress = is_ekspress;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_akhir = tanggal_akhir;
        this.waktu_mulai = waktu_mulai;
        this.waktu_akhir = waktu_akhir;
        this.is_byitem = is_byitem;
        this.status = status;
        this.totalBayar = totalBayar;
        this.lat = lat;
        this.lng = lng;
        this.berat = berat;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getKurir_id() {
        return kurir_id;
    }

    public void setKurir_id(String kurir_id) {
        this.kurir_id = kurir_id;
    }

    public String getMitra_id() {
        return mitra_id;
    }

    public void setMitra_id(String mitra_id) {
        this.mitra_id = mitra_id;
    }

    public String getNama_jalan() {
        return nama_jalan;
    }

    public void setNama_jalan(String nama_jalan) {
        this.nama_jalan = nama_jalan;
    }

    public String getNama_alias() {
        return nama_alias;
    }

    public void setNama_alias(String nama_alias) {
        this.nama_alias = nama_alias;
    }

    public String getPhone_alias() {
        return phone_alias;
    }

    public void setPhone_alias(String phone_alias) {
        this.phone_alias = phone_alias;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getDetail_lokasi() {
        return detail_lokasi;
    }

    public void setDetail_lokasi(String detail_lokasi) {
        this.detail_lokasi = detail_lokasi;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getIs_ekspress() {
        return is_ekspress;
    }

    public void setIs_ekspress(String is_ekspress) {
        this.is_ekspress = is_ekspress;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_akhir() {
        return tanggal_akhir;
    }

    public void setTanggal_akhir(String tanggal_akhir) {
        this.tanggal_akhir = tanggal_akhir;
    }

    public String getWaktu_mulai() {
        return waktu_mulai;
    }

    public void setWaktu_mulai(String waktu_mulai) {
        this.waktu_mulai = waktu_mulai;
    }

    public String getWaktu_akhir() {
        return waktu_akhir;
    }

    public void setWaktu_akhir(String waktu_akhir) {
        this.waktu_akhir = waktu_akhir;
    }

    public String getIs_byitem() {
        return is_byitem;
    }

    public void setIs_byitem(String is_byitem) {
        this.is_byitem = is_byitem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(String totalBayar) {
        this.totalBayar = totalBayar;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }
}
