package com.casiano.bikes.domain.usecases;

import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;

public interface SearchBikesUseCase {

  BikesResponse search(SearchBikeRequest request);

}
