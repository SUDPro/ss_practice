package com.simbirsoft.repositories;

import com.simbirsoft.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomsRepository extends JpaRepository<Room, Long> {

    boolean existsByName(String name);

    @Query("select r from Room r join BanInfo bi on r=bi.room where bi.user.id=:id")
    List<Room> findAllByUserId(Long id);
}