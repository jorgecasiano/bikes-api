package com.casiano.bikes.domain.models;

import java.io.Serializable;

public record Manufacturer(Integer id, String name) implements Serializable {
}
