package com.simbirsoft.models;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.utils.Util;

public class UserCommand {

    private String command;
    private String login;
    private String newUsername;
    private int minutes;
    private boolean isPointModerator;
    private User sender;
    private Room receiveRoom;

    public UserCommand(Message commandMessage) {
        String[] parameters = commandMessage.getText().split(" ");
        command = parameters[1];
        sender = commandMessage.getSender();
        receiveRoom = commandMessage.getRoom();
        newUsername = getNewName(parameters);
        isPointModerator = pointModerator(parameters);
        minutes = getMinutes(parameters);
        login = getUserLogin(parameters);
    }

    public String getCommand() {
        return command;
    }

    public String getLogin() {
        return login;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean isPointModerator() {
        return isPointModerator;
    }

    public User getSender() {
        return sender;
    }

    public Room getReceiveRoom() {
        return receiveRoom;
    }

    private String getNewName(String [] parameters){
        if (parameters[1].equals(CMDConst.USER_RENAME)){
            return parameters[2];
        }
        return "";
    }

    private boolean pointModerator(String [] parameters){
        if (parameters[1].equals(CMDConst.USER_MODERATOR)){
            return parameters[2].equals(CMDConst.USER_MODERATOR_ADD);
        }
        return false;
    }

    private int getMinutes(String [] parameters){
        if (parameters[1].equals(CMDConst.USER_BAN) && parameters.length > 4){
            if (Util.isInteger(parameters[5])){
                return Integer.parseInt(parameters[5]);
            }
        }
        return 0;
    }

    private String getUserLogin(String [] parameters){
        if (parameters.length > 3){
            return parameters[3];
        }
        return " ";
    }
}