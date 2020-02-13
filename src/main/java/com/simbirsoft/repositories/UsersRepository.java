package com.simbirsoft.repositories;

import com.simbirsoft.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    boolean existsByLogin(String login);

    @Query("select u from BanInfo b join User u on u.id = b.user.id where b.room.id=:roomId and b.user.id=:userId")
    User getUserByRoomId(Long userId, Long roomId);
}