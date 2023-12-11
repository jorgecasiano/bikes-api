package com.casiano.bikes.infrastructure.repositories;

import com.casiano.bikes.infrastructure.entities.BikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BikeRepository extends JpaRepository<BikeEntity, Long> {

  @Query("SELECT DISTINCT b FROM BikeEntity b " +
      "JOIN FETCH b.items i " +
      "WHERE " +
      "(:name IS NULL OR b.name LIKE cast(CONCAT('%', :name, '%') AS text)) " +
      "AND (:manufacturer IS NULL OR b.manufacturer.name LIKE cast(CONCAT('%', :manufacturer, '%') AS text)) " +
      "AND (:itemType IS NULL OR i.type.name LIKE cast(CONCAT('%', :itemType, '%') AS text)) " +
      "AND (:manufacturerId IS NULL OR b.manufacturer.id = :manufacturerId) " +
      "AND (:itemTypeId IS NULL OR i.type.id = :itemTypeId) "
  )
  Page<BikeEntity> searchBikes(
      @Param("name") String name,
      @Param("manufacturer") String manufacturer,
      @Param("itemType") String itemType,
      @Param("manufacturerId") Long manufacturerId,
      @Param("itemTypeId") Long itemTypeId,
//      Sort sort,
      Pageable pageable);

}