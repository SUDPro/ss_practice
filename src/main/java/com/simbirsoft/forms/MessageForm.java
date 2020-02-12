package com.simbirsoft.forms;

public class MessageForm {
    private String from;
    private String text;
    private Long roomId;

    public MessageForm() {
    }

    public MessageForm(String from, String text, Long roomId) {
        this.from = from;
        this.text = text;
        this.roomId = roomId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}