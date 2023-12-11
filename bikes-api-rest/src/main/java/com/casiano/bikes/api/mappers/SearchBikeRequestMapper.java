package com.casiano.bikes.api.mappers;

import com.casiano.bikes.domain.models.requests.PaginationRequest;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Optional;

@Mapper
public interface SearchBikeRequestMapper {

  SearchBikeRequestMapper INSTANCE = Mappers.getMapper(SearchBikeRequestMapper.class);

  String ASC = "ASC";

  default SearchBikeRequest map(String name, String manufacturer, String itemType,
                                BigDecimal manufacturerId, BigDecimal itemTypeId, String order,
                                PaginationRequest paginationRequest) {
    return Optional.ofNullable(intenalMap(name, manufacturer, itemType, manufacturerId, itemTypeId, order, paginationRequest))
        .orElseGet(SearchBikeRequest::createEmptySearch);
  }

  @Mapping(source = "order", target = "isAscendingOrder")
  SearchBikeRequest intenalMap(String name, String manufacturer, String itemType,
                               BigDecimal manufacturerId, BigDecimal itemTypeId, String order,
                               PaginationRequest paginationRequest);

  default Long mapToLong(BigDecimal value) {
    return Optional.ofNullable(value)
        .map(BigDecimal::longValue)
        .orElse(null);
  }

  default boolean mapOrder(String order) {
    return Optional.ofNullable(order)
        .map(String::toUpperCase)
        .filter(ASC::equals)
        .isPresent();
  }

}
