package com.casiano.bikes.api.controllers;

import com.casiano.bikes.api.BikesApi;
import com.casiano.bikes.api.mappers.BikeRequestMapper;
import com.casiano.bikes.api.mappers.BikeResponseMapper;
import com.casiano.bikes.api.mappers.SearchBikeRequestMapper;
import com.casiano.bikes.domain.models.requests.PaginationRequest;
import com.casiano.bikes.domain.usecases.CreateBikeUseCase;
import com.casiano.bikes.domain.usecases.SearchBikesUseCase;
import com.casiano.bikes.dtos.BikeDto;
import com.casiano.bikes.dtos.BikeRequestDto;
import com.casiano.bikes.dtos.BikesResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
public class BikesController implements BikesApi {

  private static final String ASC = "ASC";
  private SearchBikesUseCase searchBikesUseCase;

  private CreateBikeUseCase createBikeUseCase;

  public BikesController(SearchBikesUseCase searchBikesUseCase, CreateBikeUseCase createBikeUseCase) {
    this.searchBikesUseCase = searchBikesUseCase;
    this.createBikeUseCase = createBikeUseCase;
  }

  @Override
  public ResponseEntity<BikeDto> createBike(BikeRequestDto bikeRequest) {
    return Optional.ofNullable(bikeRequest)
        .map(BikeRequestMapper.INSTANCE::map)
        .map(this.createBikeUseCase::create)
        .map(BikeResponseMapper.INSTANCE::map)
        .map(bike -> ResponseEntity.status(HttpStatus.CREATED).body(bike))
        .orElseGet(this::defaultError);
  }

  @Override
  public ResponseEntity<BikesResponseDto> searchBikes(String name, String manufacturer, String itemType, BigDecimal manufacturerId, BigDecimal itemTypeId, String order, BigDecimal page, BigDecimal size) {
    return Optional.of(PaginationRequest.create(page, size))
        .map(paginationRequest -> SearchBikeRequestMapper.INSTANCE.map(name, manufacturer, itemType, manufacturerId, itemTypeId, order, paginationRequest))
        .map(this.searchBikesUseCase::search)
        .map(BikeResponseMapper.INSTANCE::map)
        .map(ResponseEntity::ok)
        .orElseGet(this::defaultError);
  }



  private ResponseEntity defaultError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Unexpected error");
  }

}
