package com.simbirsoft.models;

public class UserCommand {

    private String command;
    private String login;
    private int minutes;
    boolean isPointModerator;

    public UserCommand() {
    }

    public UserCommand(String command, String login, int minutes, boolean isPointModerator) {
        this.command = command;
        this.login = login;
        this.minutes = minutes;
        this.isPointModerator = isPointModerator;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public boolean isPointModerator() {
        return isPointModerator;
    }

    public void setPointModerator(boolean pointModerator) {
        isPointModerator = pointModerator;
    }
}
