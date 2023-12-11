package com.casiano.bikes.domain.models;

import java.io.Serializable;
import java.util.List;

public record Bike(Integer id, String name, String description, Double price, String currency, Manufacturer manufacturer, List<Item> items) implements Serializable {
}
