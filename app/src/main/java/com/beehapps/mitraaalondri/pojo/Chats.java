package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 7/16/17.
 */

public class Chats {

    private String id;
    private String name;
    private String chat;
    private String sender;
    private String mitra;

    public Chats(){
    }

    public Chats(String id, String name, String chat, String sender, String mitra){
        this.id = id;
        this.name = name;
        this.chat = chat;
        this.sender = sender;
        this.mitra = mitra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMitra() {
        return mitra;
    }

    public void setMitra(String mitra) {
        this.mitra = mitra;
    }
}
