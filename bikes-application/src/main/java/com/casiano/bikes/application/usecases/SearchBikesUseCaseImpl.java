package com.casiano.bikes.application.usecases;


import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;
import com.casiano.bikes.domain.services.BikeService;
import com.casiano.bikes.domain.usecases.SearchBikesUseCase;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SearchBikesUseCaseImpl implements SearchBikesUseCase {

  BikeService bikeService;

  public SearchBikesUseCaseImpl(BikeService bikeService) {
    this.bikeService = bikeService;
  }

  @Override
  public BikesResponse search(SearchBikeRequest request) {
    if (Objects.isNull(request)) {
      throw new IllegalArgumentException("'request' cannot be null");
    }

    return this.bikeService.search(request);
  }
}
