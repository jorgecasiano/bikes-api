package com.casiano.bikes.domain.models.requests;

import java.math.BigDecimal;
import java.util.Optional;

public record PaginationRequest(Integer page, Integer size) {


  private static final int DEFAULT_PAGE = 0;
  private static final int DEFAULT_SIZE = 10;

  public static PaginationRequest create(BigDecimal page, BigDecimal size) {
    final var pageAux = Optional.ofNullable(page)
        .map(BigDecimal::intValue)
        .or(() -> Optional.of(DEFAULT_PAGE))
        .filter(p -> p.intValue() >= DEFAULT_PAGE)
        .orElseThrow(() -> new IllegalArgumentException("'page' cannot be less than 0"));

    final var sizeAux = Optional.ofNullable(size)
        .map(BigDecimal::intValue)
        .or(() -> Optional.of(DEFAULT_SIZE))
        .filter(s -> s.intValue() >= 1)
        .orElseThrow(() -> new IllegalArgumentException("'size' cannot be less than 1"));

    return new PaginationRequest(pageAux, sizeAux);
  }

  public static PaginationRequest createDefault() {
    return new PaginationRequest(DEFAULT_PAGE, DEFAULT_SIZE);
  }

}
