package com.casiano.bikes.application.usecases;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.domain.services.BikeService;
import com.casiano.bikes.domain.usecases.CreateBikeUseCase;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class CreateBikeUseCaseImpl implements CreateBikeUseCase {

  BikeService bikeService;

  public CreateBikeUseCaseImpl(BikeService bikeService) {
    this.bikeService = bikeService;
  }

  @Override
  public Bike create(BikeRequest request) {
    if (Objects.isNull(request)) {
      throw new IllegalArgumentException("'request' cannot be null");
    }

    return this.bikeService.create(request);
  }
}
