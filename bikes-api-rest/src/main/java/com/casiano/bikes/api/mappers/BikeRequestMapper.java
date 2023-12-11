package com.casiano.bikes.api.mappers;

import com.casiano.bikes.domain.models.Item;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.dtos.BikeRequestDto;
import com.casiano.bikes.dtos.ItemRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public interface BikeRequestMapper {

  BikeRequestMapper INSTANCE = Mappers.getMapper(BikeRequestMapper.class);

  @Mapping(source = "request", target = "items")
  BikeRequest map(BikeRequestDto request);

  default List<Item> mapItems(BikeRequestDto request) {
    var bikeItems = Optional.ofNullable(request)
        .map(BikeRequestDto::getExistingItems)
        .map(itemIds -> itemIds.stream()
            .map(this::mapItemFromId)
            .collect(Collectors.toList()))
        .orElseGet(ArrayList::new);

    Optional.ofNullable(request)
        .map(BikeRequestDto::getNewItems)
        .map(items -> items.stream()
            .map(this::mapItemRequest)
            .collect(Collectors.toList()))
        .ifPresent(bikeItems::addAll);

    return bikeItems;
  }

  @Mapping(source = "id", target = "id")
  Item mapItemFromId(BigDecimal id);

  Item mapItemRequest(ItemRequestDto item);

}
