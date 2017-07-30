package com.beehapps.mitraaalondri.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.beehapps.mitraaalondri.pojo.Alamat;
import com.beehapps.mitraaalondri.pojo.FCM;
import com.beehapps.mitraaalondri.pojo.Mitra;
import com.beehapps.mitraaalondri.pojo.Profil;
import com.beehapps.mitraaalondri.pojo.Riwayat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farhan on 10/17/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Versi Database
    public static final int DATABASE_VERSION = 12;

    // Nama Database
    public static final String DATABASE_NAME = "aalondri";

    // Nama Tabel
    public static final String TABLE_PROFIL = "profil";
    public static final String TABLE_RIWAYAT = "riwayat";
    public static final String TABLE_FCM = "fcm";
    public static final String TABLE_ALAMAT = "alamat";
    public static final String TABLE_MITRA = "mitra";

    //Tabel Profil
    public static final String KEY_ID_PROFIL = "_id";
    public static final String KEY_USERID_PROFIL = "userid";
    public static final String KEY_USERNAME_PROFIL = "username";
    public static final String KEY_EMAIL_PROFIL = "email";
    public static final String KEY_TOKEN_PROFIL = "token";
    public static final String KEY_TOKEN_FCM = "token_fcm";
    public static final String KEY_CREATED_AT = "created_at";

    //Tabel Riwayat
    public static final String KEY_ID_RIWAYAT = "_id";
    public static final String KEY_INVOICE_RIWAYAT = "invoice";
    public static final String KEY_IDR_RIWAYAT = "biaya";
    public static final String KEY_JENIS_RIWAYAT = "jenis";
    public static final String KEY_PICKUP_RIWAYAT = "pickup";

    //Tabel Riwayat
    public static final String KEY_ID_FCM = "_id";
    public static final String KEY_TOKENS_FCM = "token";


    //Tabel Mitra
    public static final String KEY__ID_MITRA = "_id";
    public static final String KEY_ID_MITRA = "id_mitra";
    public static final String KEY_NAMA_MITRA = "nama";
    public static final String KEY_ALAMAT_MITRA = "alamat";
    public static final String KEY_DESKRIPSI_MITRA = "deskripsi";
    public static final String KEY_LAT_MITRA = "lat_user";
    public static final String KEY_LNG_MITRA = "lng_user";

    //Tabel Riwayat
    public static final String KEY_ID_ALAMAT = "_id";
    public static final String KEY_NAMA_ALAMAT = "nama_alamat";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_DESKRIPSI_ALAMAT = "deskripsi_alamat";
    public static final String KEY_KODE_POS = "kodepos_alamat";
    public static final String KEY_NAMA_USER = "nama_user";
    public static final String KEY_TELEPON_USER = "telepon_user";
    public static final String KEY_LAT_USER = "lat_user";
    public static final String KEY_LNG_USER = "lng_user";

    public Resources res;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        res = context.getResources();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROFIL = "CREATE TABLE " + TABLE_PROFIL + "("
                + KEY_ID_PROFIL + " INTEGER PRIMARY KEY," + KEY_USERID_PROFIL + " INTEGER,"
                + KEY_USERNAME_PROFIL + " TEXT," + KEY_EMAIL_PROFIL + " TEXT,"
                + KEY_TOKEN_PROFIL + " TEXT," + KEY_TOKEN_FCM + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";

        String CREATE_TABLE_RIWAYAT = "CREATE TABLE " + TABLE_RIWAYAT + "("
                + KEY_ID_RIWAYAT + " INTEGER PRIMARY KEY," + KEY_INVOICE_RIWAYAT + " TEXT,"
                + KEY_IDR_RIWAYAT + " TEXT," + KEY_JENIS_RIWAYAT + " TEXT," + KEY_PICKUP_RIWAYAT + " TEXT" + ")";

        String CREATE_TABLE_FCM = "CREATE TABLE " + TABLE_FCM + "("
                + KEY_ID_FCM + " INTEGER PRIMARY KEY," + KEY_TOKENS_FCM + " TEXT" + ")";

        String CREATE_TABLE_MITRA = "CREATE TABLE " + TABLE_MITRA + "("
                + KEY__ID_MITRA + " INTEGER PRIMARY KEY," + KEY_ID_MITRA + " TEXT,"
                + KEY_NAMA_MITRA + " TEXT," + KEY_ALAMAT_MITRA + " TEXT,"
                + KEY_DESKRIPSI_MITRA + " TEXT," + KEY_LAT_MITRA + " TEXT,"
                + KEY_LNG_MITRA + " TEXT" + ")";

        String CREATE_TABLE_ALAMAT = "CREATE TABLE " + TABLE_ALAMAT + "("
                + KEY_ID_ALAMAT + " INTEGER PRIMARY KEY," + KEY_NAMA_ALAMAT + " TEXT,"
                + KEY_ALAMAT + " TEXT," + KEY_DESKRIPSI_ALAMAT + " TEXT,"
                + KEY_KODE_POS + " TEXT," + KEY_NAMA_USER + " TEXT,"
                + KEY_TELEPON_USER + " TEXT," + KEY_LAT_USER + " TEXT,"
                + KEY_LNG_USER + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_PROFIL);
        db.execSQL(CREATE_TABLE_RIWAYAT);
        db.execSQL(CREATE_TABLE_FCM);
        db.execSQL(CREATE_TABLE_MITRA);
        db.execSQL(CREATE_TABLE_ALAMAT);

        ContentValues values = new ContentValues();
        values.put(KEY_ID_FCM, 1);
        db.insert(TABLE_FCM, null, values);

    }

    public void update(SQLiteDatabase db) {
        String CREATE_TABLE_RIWAYAT = "CREATE TABLE " + TABLE_RIWAYAT + "("
                + KEY_ID_RIWAYAT + " INTEGER PRIMARY KEY," + KEY_INVOICE_RIWAYAT + " TEXT,"
                + KEY_IDR_RIWAYAT + " TEXT," + KEY_JENIS_RIWAYAT + " TEXT," + KEY_PICKUP_RIWAYAT + " TEXT" + ")";

        String CREATE_TABLE_MITRA = "CREATE TABLE " + TABLE_MITRA + "("
                + KEY__ID_MITRA + " INTEGER PRIMARY KEY," + KEY_ID_MITRA + " TEXT,"
                + KEY_NAMA_MITRA + " TEXT," + KEY_ALAMAT_MITRA + " TEXT,"
                + KEY_DESKRIPSI_MITRA + " TEXT," + KEY_LAT_MITRA + " TEXT,"
                + KEY_LNG_MITRA + " TEXT" + ")";

        String CREATE_TABLE_ALAMAT = "CREATE TABLE " + TABLE_ALAMAT + "("
                + KEY_ID_ALAMAT + " INTEGER PRIMARY KEY," + KEY_NAMA_ALAMAT + " TEXT,"
                + KEY_ALAMAT + " TEXT," + KEY_DESKRIPSI_ALAMAT + " TEXT,"
                + KEY_KODE_POS + " TEXT," + KEY_NAMA_USER + " TEXT,"
                + KEY_TELEPON_USER + " TEXT," + KEY_LAT_USER + " TEXT,"
                + KEY_LNG_USER + " TEXT" + ")";

        db.execSQL(CREATE_TABLE_RIWAYAT);
        db.execSQL(CREATE_TABLE_MITRA);
        db.execSQL(CREATE_TABLE_ALAMAT);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIWAYAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MITRA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALAMAT);

        // Create tables again
        update(db);
    }

    public void hapusDbaseProfil() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PROFIL);
    }

    public void hapusDbaseRiwayat() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_RIWAYAT);
    }

    public void hapusDbaseMitra() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_MITRA);
    }

    public void hapusDbaseAlamat() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ALAMAT);
    }

    public void tambahProfil(Profil profil) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_USERID_PROFIL, profil.getUserID());
        values.put(KEY_USERNAME_PROFIL, profil.getUsername());
        values.put(KEY_EMAIL_PROFIL, profil.getEmail());
        values.put(KEY_TOKEN_PROFIL, profil.getToken());
        values.put(KEY_TOKEN_FCM, profil.getTokenFcm());
        values.put(KEY_CREATED_AT, profil.getCreate());

        // Inserting Row
        db.insert(TABLE_PROFIL, null, values);
        db.close();
    }

    public void tambahRiwayat(Riwayat riwayat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_INVOICE_RIWAYAT, riwayat.getInvoice());
        values.put(KEY_IDR_RIWAYAT, riwayat.getIdr());
        values.put(KEY_JENIS_RIWAYAT, riwayat.getJenis());
        values.put(KEY_PICKUP_RIWAYAT, riwayat.getPickup());

        // Inserting Row
        db.insert(TABLE_RIWAYAT, null, values);
        db.close();
    }

    public void tambahMitra(Mitra mitra) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID_MITRA, mitra.getId_mitra());
        values.put(KEY_NAMA_MITRA, mitra.getNama_mitra());
        values.put(KEY_ALAMAT_MITRA, mitra.getAlamat_mitra());
        values.put(KEY_DESKRIPSI_MITRA, mitra.getDeskripsi_mitra());
        values.put(KEY_LAT_MITRA, mitra.getLat());
        values.put(KEY_LNG_MITRA, mitra.getLng());

        // Inserting Row
        db.insert(TABLE_MITRA, null, values);
        db.close();
    }

    public void tambahAlamat(Alamat alamat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAMA_ALAMAT, alamat.getNamaAlamat());
        values.put(KEY_ALAMAT, alamat.getAlamatAlamat());
        values.put(KEY_DESKRIPSI_ALAMAT, alamat.getDeskripsiAlamat());
        values.put(KEY_KODE_POS, alamat.getKodeposAlamat());
        values.put(KEY_NAMA_USER, alamat.getNamaUser());
        values.put(KEY_TELEPON_USER, alamat.getTeleponUser());
        values.put(KEY_LAT_USER, alamat.getLatUser());
        values.put(KEY_LNG_USER, alamat.getLngUser());

        // Inserting Row
        db.insert(TABLE_ALAMAT, null, values);
        db.close();
    }


    public List<Profil> getAllProfils() {
        List<Profil> profilList = new ArrayList<Profil>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_PROFIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Profil profil = new Profil();
                profil.setID(Integer.parseInt(cursor.getString(0)));
                profil.setUserID(Integer.parseInt(cursor.getString(1)));
                profil.setUsername(cursor.getString(2));
                profil.setEmail(cursor.getString(3));
                profil.setToken(cursor.getString(4));
                profil.setTokenFcm(cursor.getString(5));
                profil.setCreate(cursor.getString(6));
                // Adding contact to list
                profilList.add(profil);
            } while (cursor.moveToNext());
        }

        // return contact list
        return profilList;
    }

    public List<Riwayat> getAllRiwayats() {
        List<Riwayat> profilList = new ArrayList<Riwayat>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RIWAYAT + " ORDER BY _id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Riwayat riwayat = new Riwayat();
                riwayat.setID(Integer.parseInt(cursor.getString(0)));
                riwayat.setInvoice(cursor.getString(1));
                riwayat.setIdr(cursor.getString(2));
                riwayat.setJenis(cursor.getString(3));
                riwayat.setPickup(cursor.getString(4));
                // Adding contact to list
                profilList.add(riwayat);
            } while (cursor.moveToNext());
        }

        // return contact list
        return profilList;
    }

    public List<FCM> getAllFCMs() {
        List<FCM> profilList = new ArrayList<FCM>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FCM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FCM profil = new FCM();
                profil.setID(Integer.parseInt(cursor.getString(0)));
                profil.setTokenFCM(cursor.getString(1));
                // Adding contact to list
                profilList.add(profil);
            } while (cursor.moveToNext());
        }

        // return contact list
        return profilList;
    }

    public List<Alamat> getAllAlamats() {
        List<Alamat> alamatList = new ArrayList<Alamat>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_ALAMAT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Alamat alamat = new Alamat();
                alamat.set_id(Integer.parseInt(cursor.getString(0)));
                alamat.setNamaAlamat(cursor.getString(1));
                alamat.setAlamatAlamat(cursor.getString(2));
                alamat.setDeskripsiAlamat(cursor.getString(3));
                alamat.setKodeposAlamat(cursor.getString(4));
                alamat.setNamaUser(cursor.getString(5));
                alamat.setTeleponUser(cursor.getString(6));
                alamat.setLatUser(cursor.getString(7));
                alamat.setLngUser(cursor.getString(8));
                // Adding contact to list
                alamatList.add(alamat);
            } while (cursor.moveToNext());
        }

        // return contact list
        return alamatList;
    }

    public List<Mitra> getAllMitras() {
        List<Mitra> mitraList = new ArrayList<Mitra>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_MITRA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Mitra mitra = new Mitra();
                mitra.set_id(Integer.parseInt(cursor.getString(0)));
                mitra.setId_mitra(cursor.getString(1));
                mitra.setNama_mitra(cursor.getString(2));
                mitra.setAlamat_mitra(cursor.getString(3));
                mitra.setDeskripsi_mitra(cursor.getString(4));
                mitra.setLat(cursor.getDouble(5));
                mitra.setLng(cursor.getDouble(6));
                // Adding contact to list
                mitraList.add(mitra);
            } while (cursor.moveToNext());
        }

        // return contact list
        return mitraList;
    }

}
