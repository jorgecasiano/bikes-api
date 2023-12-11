package com.casiano.bikes.domain.models.requests;

public record SearchBikeRequest(
    String name,
    String manufacturer,
    String itemType,
    Long manufacturerId,
    Long itemTypeId,
    boolean isAscendingOrder,
    PaginationRequest paginationRequest) {

  @Override
  public String toString() {
    return "SEARCH_BIKE_REQUEST::" + name + "::" + manufacturer + "::" + itemType + "::" + manufacturerId + "::"
        + itemTypeId + "::" + isAscendingOrder + "::" + paginationRequest;
  }

  public static SearchBikeRequest createEmptySearch() {
    return new SearchBikeRequest(null, null, null, null, null,
        false, PaginationRequest.createDefault());
  }

}
