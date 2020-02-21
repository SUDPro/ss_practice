package com.simbirsoft.models;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.utils.Util;

public class RoomCommand {
    
    private String newRoomName;
    private boolean isPrivateRoom;
    private String command;
    private String userLogin;
    private int minutes;
    private User sender;
    private Room receiveRoom;

    public RoomCommand(Message commandMessage) {
        String[] parameters = commandMessage.getText().split(" ");
        sender = commandMessage.getSender();
        receiveRoom = commandMessage.getRoom();
        command = parameters[1];
        isPrivateRoom = isRoomPrivate(parameters);
        newRoomName = getRoomName(parameters);
        userLogin = getLogin(parameters);
        minutes = getMinutes(parameters);
    }


    private int getMinutes(String[] parameters) {
        if (parameters[1].equals(CMDConst.ROOM_DISCONNECT) && parameters.length > 4) {
            if (Util.isInteger(parameters[5])) {
                return Integer.parseInt(parameters[5]);
            }
        }
        return 0;
    }

    private String getLogin(String[] parameters) {
        if (parameters[1].equals(CMDConst.ROOM_CONNECT) && parameters.length > 3) {
            return parameters[4];
        } else if (parameters[1].equals((CMDConst.ROOM_DISCONNECT)) && parameters.length > 2) {
            return parameters[3];
        }
        return "";
    }


    private String getRoomName(String[] parameters) {
        return !parameters[1].equals(CMDConst.ROOM_DISCONNECT) ? parameters[2] : " ";
    }

    private boolean isRoomPrivate(String[] parameters) {
        return (parameters.length > 3 && parameters[1].equals(CMDConst.ROOM_CREATE) &&
                parameters[3].equals(CMDConst.ROOM_CREATE_PARAMETER_PRIVATE));
    }

    public String getNewRoomName() {
        return newRoomName;
    }

    public boolean isPrivateRoom() {
        return isPrivateRoom;
    }

    public String getCommand() {
        return command;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public int getMinutes() {
        return minutes;
    }

    public User getSender() {
        return sender;
    }

    public Room getReceiveRoom() {
        return receiveRoom;
    }
}