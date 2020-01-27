package com.simbirsoft.repositories;

import com.simbirsoft.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
