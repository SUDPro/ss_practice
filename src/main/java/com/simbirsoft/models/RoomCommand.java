package com.simbirsoft.models;

import com.simbirsoft.constants.CMDConst;
import com.simbirsoft.utils.Util;
//        0     1      2
//<p>//room create {roomName}  --  create public room</p>
//       0      1    2         3
//<p>//room create {roomName} -с  --  create private room</p>
//       0      1       2
//<p>//room remove {roomName}  --  remove room</p>
//       0      1       2
//<p>//room connect {roomName}  --  enter in room</p>
//       0      1       2      3    4
//<p>//room connect {roomName} -l {login}  --  enter user in room</p>
//       0      1
//<p>//room disconnect  --  exit from room</p>
//       0      1       2    3     4    5
//<p>//room disconnect -l {login} -m {minutes}  --  exit user from room for {minutes}</p>

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


    private int getMinutes(String[] parameters){
        if (parameters[1].equals(CMDConst.ROOM_DISCONNECT) && parameters.length > 4){
            if (Util.isInteger(parameters[5])){
                return Integer.parseInt(parameters[5]);
            }
        }
        return 0;
    }

    private String getLogin(String[] parameters) {
        if (parameters[1].equals(CMDConst.ROOM_CONNECT) && parameters.length > 3){
            return parameters[4];
        } else if (parameters[1].equals((CMDConst.ROOM_DISCONNECT)) && parameters.length > 2){
            return parameters[3];
        }
        return "";
    }


    private String getRoomName(String[] parameters) {
       return !parameters[1].equals(CMDConst.ROOM_DISCONNECT) ? parameters[2] : " ";
    }

    private boolean isRoomPrivate(String[] parameters) {
        return (parameters[1].equals(CMDConst.ROOM_CREATE) &&
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