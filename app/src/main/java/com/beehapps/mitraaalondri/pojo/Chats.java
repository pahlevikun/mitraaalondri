package com.beehapps.mitraaalondri.pojo;

/**
 * Created by farhan on 7/16/17.
 */

public class Chats {

    private String id;
    private String sender_id;
    private String receiver_id;
    private String sender_name;
    private String receiver_name;
    private String content;

    public Chats(){
    }

    public Chats(String id, String sender_id, String receiver_id, String sender_name, String receiver_name, String content){
        this.id = id;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sender_name = sender_name;
        this.receiver_name = receiver_name;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
