package com.casiano.bikes.domain.usecases;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.requests.BikeRequest;

public interface CreateBikeUseCase {

  Bike create(BikeRequest request);

}
