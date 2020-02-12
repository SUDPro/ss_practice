package com.simbirsoft.repositories;

import com.simbirsoft.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoomId(Long id);
}