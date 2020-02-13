package com.simbirsoft.repositories;

import com.simbirsoft.models.BanInfo;
import com.simbirsoft.models.Room;
import com.simbirsoft.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BanInfosRepository extends JpaRepository<BanInfo, Long> {
    BanInfo findBanInfoByUserAndRoom(User user, Room room);

    List<BanInfo> findAllByRoom(Room room);
}