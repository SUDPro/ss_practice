package com.simbirsoft.repositories;

import com.simbirsoft.models.BanInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanInfosRepository extends JpaRepository<BanInfo, Long> {
}