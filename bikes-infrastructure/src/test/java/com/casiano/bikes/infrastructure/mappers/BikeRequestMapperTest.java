package com.casiano.bikes.infrastructure.mappers;

import com.casiano.bikes.domain.models.Item;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.infrastructure.entities.ItemEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

class BikeRequestMapperTest {

  @ParameterizedTest
  @NullSource
  void map_when_input_is_null_then_null(BikeRequest request) {
    final var underTest = BikeRequestMapper.INSTANCE;

    final var actual = underTest.map(request);

    Assertions.assertThat(actual).isNull();
  }

  @Test
  void map_when_input_is_valid_then_ok() {
    final var underTest = BikeRequestMapper.INSTANCE;
    final var request = prepareRequest();

    final var actual = underTest.map(request);

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.getName()).isNotNull().isEqualTo("anyName");
    Assertions.assertThat(actual.getDescription()).isNotNull().isEqualTo("anyDescription");
    Assertions.assertThat(actual.getPrice()).isNotNull().isEqualTo(new BigDecimal("120.25"));
    Assertions.assertThat(actual.getCurrency()).isNotNull().isEqualTo("EUR");
    Assertions.assertThat(actual.getManufacturer().getId()).isNotNull().isEqualTo(90L);
    Assertions.assertThat(actual.getItems()).isNotNull()
        .hasSize(3)
        .map(ItemEntity::getId)
        .containsExactlyInAnyOrder(100L, 101L, null);

    Assertions.assertThat(actual.getItems()).isNotNull()
        .filteredOn(item -> Objects.isNull(item.getId()))
        .hasSize(1)
        .first()
        .matches(item -> {
          Assertions.assertThat(item.getModel()).isNotNull().isEqualTo("anyModel");
          Assertions.assertThat(item.getDescription()).isNotNull().isEqualTo("anyDesc");
          Assertions.assertThat(item.getType()).isNotNull();
          Assertions.assertThat(item.getType().getId()).isNotNull().isEqualTo(55L);
          Assertions.assertThat(item.getType().getName()).isNull();
          return true;
        });

  }

  private static BikeRequest prepareRequest() {
    return new BikeRequest("anyName", "anyDescription", 120.25D, "EUR", 90,
        List.of(new Item(100, null, null, null, null),
            new Item(101, null, null, null, null),
            new Item(null, "anyModel", "anyDesc", 55, null)));
  }

}
