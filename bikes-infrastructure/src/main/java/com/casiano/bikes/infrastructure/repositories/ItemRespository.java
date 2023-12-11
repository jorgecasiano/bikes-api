package com.casiano.bikes.infrastructure.repositories;

import com.casiano.bikes.infrastructure.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRespository extends JpaRepository<ItemEntity, Long> {
}
