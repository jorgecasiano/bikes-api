package com.casiano.bikes.infrastructure.mappers;

import com.casiano.bikes.domain.models.*;
import com.casiano.bikes.infrastructure.entities.BikeEntity;
import com.casiano.bikes.infrastructure.entities.ItemEntity;
import com.casiano.bikes.infrastructure.entities.ManufacturerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public interface BikeResponseMapper {

  BikeResponseMapper INSTANCE = Mappers.getMapper(BikeResponseMapper.class);

  default BikesResponse map(Page<BikeEntity> entities) {
    final var results = Optional.ofNullable(entities)
        .map(entitiesAux -> entitiesAux.stream().map(this::mapBike).collect(Collectors.toList()))
        .orElseGet(Collections::emptyList);
    return new BikesResponse(results, mapPagination(entities));
  }

  @Mapping(source = "totalElements", target = "totalItems")
  @Mapping(source = "totalPages", target = "totalPages")
  @Mapping(source = "pageable.pageNumber", target = "currentPage")
  @Mapping(source = "pageable.pageSize", target = "size")
  Pagination mapPagination(Page<BikeEntity> entities);

  Bike mapBike(BikeEntity entity);

  Manufacturer mapManufacturer(ManufacturerEntity entity);

  @Mapping(source = "type.id", target = "typeId")
  @Mapping(source = "type.name", target = "type")
  Item mapItem(ItemEntity entity);

}
