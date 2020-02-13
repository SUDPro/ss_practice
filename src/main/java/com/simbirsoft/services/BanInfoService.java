package com.simbirsoft.services;

import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import com.simbirsoft.repositories.BanInfosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BanInfoService {

    @Autowired
    private BanInfosRepository banInfosRepository;

    public BanInfo findBanInfoByUserAndRoom(User user, Room room){
        return banInfosRepository.findBanInfoByUserAndRoom(user, room);
    }

    public boolean isUserBanned(User user, Room room){
        BanInfo banInfo = findBanInfoByUserAndRoom(user, room);
        return (banInfo.getDateTime() != null && banInfo.getDateTime().after(new Date()));
    }

    public int countUsersInChat(Room room){
        return banInfosRepository.findAllByRoom(room).size();
    }

    public void save(BanInfo banInfo){
        banInfosRepository.save(banInfo);
    }

    public void delete(BanInfo banInfo){
        banInfosRepository.delete(banInfo);
    }
}