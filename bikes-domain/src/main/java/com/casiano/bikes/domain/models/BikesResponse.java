package com.casiano.bikes.domain.models;

import java.io.Serializable;
import java.util.List;

public record BikesResponse(List<Bike> results, Pagination pagination) implements Serializable {
}
