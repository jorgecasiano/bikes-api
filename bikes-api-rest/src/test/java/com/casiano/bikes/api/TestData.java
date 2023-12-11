package com.casiano.bikes.api;

import com.casiano.bikes.domain.models.*;

import java.util.List;

public final class TestData {

  private TestData() {

  }

  public static BikesResponse prepareBikesResponse() {
    return new BikesResponse(List.of(prepareBike(1), prepareBike(2)), preparePagination());
  }

  public static Bike prepareBike(Integer id) {
    return new Bike(id, "anyName", "anyDescription", 120.25D, "EUR",
        new Manufacturer(100, "anyManufacturer"),
        List.of(prepareItem(3), prepareItem(4))
    );
  }

  public static Item prepareItem(Integer id) {
    return new Item(id, "anyModel", "anyDesc", 99, "anyType");
  }

  public static Pagination preparePagination() {
    return new Pagination(1, 2, 3, 4);
  }

}
