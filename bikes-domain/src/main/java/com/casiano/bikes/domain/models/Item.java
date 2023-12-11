package com.casiano.bikes.domain.models;

import java.io.Serializable;

public record Item(Integer id, String model, String description, Integer typeId, String type) implements Serializable {
}
