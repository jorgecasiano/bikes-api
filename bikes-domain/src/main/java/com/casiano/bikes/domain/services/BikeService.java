package com.casiano.bikes.domain.services;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;

public interface BikeService {

  Bike create(BikeRequest request);

  BikesResponse search(SearchBikeRequest request);

}
