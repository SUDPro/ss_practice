package com.simbirsoft.models;

import java.util.Date;

public class Message {
    private Long id;
    private String sender;
    private String text;
    private Date date;

    public Message() {
    }

    public Message(String sender, String text, Date date) {
        this.sender = sender;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
