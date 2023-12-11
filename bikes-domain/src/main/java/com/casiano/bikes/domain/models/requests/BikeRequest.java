package com.casiano.bikes.domain.models.requests;

import com.casiano.bikes.domain.models.Item;

import java.util.List;

public record BikeRequest(String name, String description, Double price, String currency, Integer manufacturerId, List<Item> items) {
}
