package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 5/31/16.
 */
public class Profil {
    //private variables
    int _id;
    int _userid;
    String _username;
    String _email;
    String _token;
    String _create;
    String _tokenFcm;

    // Empty constructor
    public Profil() {
    }

    // constructor
    public Profil(int id, int userid, String username, String email, String token, String create, String tokenFcm) {
        this._id = id;
        this._userid = userid;
        this._username = username;
        this._email = email;
        this._token = token;
        this._create = create;
        this._tokenFcm = tokenFcm;
    }

    // constructor
    public Profil(int userid, String username, String email, String token, String create, String tokenFcm) {
        this._userid = userid;
        this._username = username;
        this._email = email;
        this._token = token;
        this._create = create;
        this._tokenFcm = tokenFcm;
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
    public int getUserID() {
        return this._userid;
    }

    // setting id
    public void setUserID(int userid) {
        this._userid = userid;
    }

    // getting judul
    public String getUsername() {
        return this._username;
    }

    // setting judul
    public void setUsername(String username) {
        this._username = username;
    }

    // getting judul
    public String getEmail() {
        return this._email;
    }

    // setting judul
    public void setEmail(String email) {
        this._email = email;
    }

    // getting ID
    public String getToken() {
        return this._token;
    }

    // setting id
    public void setToken(String token) {
        this._token = token;
    }

    // getting ID
    public String getCreate() {
        return this._create;
    }

    // setting id
    public void setCreate(String create) {
        this._create = create;
    }

    // getting ID
    public String getTokenFcm() {
        return this._tokenFcm;
    }

    // setting id
    public void setTokenFcm(String tokenFcm) {
        this._tokenFcm = tokenFcm;
    }

}