package com.simbirsoft.repositories;

import com.simbirsoft.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    boolean existsByLogin(String login);
}