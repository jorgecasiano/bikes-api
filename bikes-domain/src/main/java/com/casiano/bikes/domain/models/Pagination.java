package com.casiano.bikes.domain.models;

import java.io.Serializable;

public record Pagination(Integer totalItems, Integer totalPages, Integer currentPage, Integer size) implements Serializable {

}
