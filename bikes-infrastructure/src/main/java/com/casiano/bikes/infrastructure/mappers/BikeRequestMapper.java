package com.casiano.bikes.infrastructure.mappers;

import com.casiano.bikes.domain.models.Item;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.infrastructure.entities.BikeEntity;
import com.casiano.bikes.infrastructure.entities.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper
public interface BikeRequestMapper {

  BikeRequestMapper INSTANCE = Mappers.getMapper(BikeRequestMapper.class);

  @Mapping(source = "manufacturerId", target = "manufacturer.id")
  @Mapping(source = "items", target = "items", qualifiedByName = "mapItems")
  BikeEntity map(BikeRequest domain);

  @Named("mapItems")
  default Set<ItemEntity> mapItems(List<Item> items) {
    return Optional.ofNullable(items)
        .map(itemsIdAux -> itemsIdAux.stream().map(this::mapItem).collect(Collectors.toSet()))
        .orElse(null);
  }

  @Mapping(source = "typeId", target = "type.id")
  ItemEntity mapItem(Item item);

}