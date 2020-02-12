package com.simbirsoft.forms;

public class RoomForm {
    private String name;
    private boolean type;

    public RoomForm() {
    }

    public RoomForm(String name, Boolean type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}