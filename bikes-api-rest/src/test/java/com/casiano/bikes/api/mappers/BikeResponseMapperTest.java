package com.casiano.bikes.api.mappers;

import com.casiano.bikes.api.TestData;
import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.dtos.BikeDto;
import com.casiano.bikes.dtos.BikesResponseDto;
import com.casiano.bikes.dtos.ItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

class BikeResponseMapperTest {

  @ParameterizedTest
  @NullSource
  void map_when_bikesResponse_is_null_then_null(BikesResponse domain) {
    final var underTest = BikeResponseMapper.INSTANCE;

    final var actual = underTest.map(domain);

    Assertions.assertThat(actual).isNull();
  }

  @ParameterizedTest
  @NullSource
  void map_when_bike_is_null_then_null(Bike domain) {
    final var underTest = BikeResponseMapper.INSTANCE;

    final var actual = underTest.map(domain);

    Assertions.assertThat(actual).isNull();
  }

  @Test
  void map_when_bikesResponse_is_valid_then_ok() {
    final var underTest = BikeResponseMapper.INSTANCE;
    final var request = TestData.prepareBikesResponse();

    final var actual = underTest.map(request);

    Assertions.assertThat(actual).isNotNull().isInstanceOf(BikesResponseDto.class);
    Assertions.assertThat(actual.getResults()).isNotNull()
        .hasSize(2)
        .map(BikeDto::getId)
        .containsExactly(BigDecimal.valueOf(1), BigDecimal.valueOf(2));

    assertBike(actual.getResults().get(0));

    Assertions.assertThat(actual.getPagination()).isNotNull();
    Assertions.assertThat(actual.getPagination().getTotalItems()).isNotNull().isEqualTo(BigDecimal.valueOf(1));
    Assertions.assertThat(actual.getPagination().getTotalPages()).isNotNull().isEqualTo(BigDecimal.valueOf(2));
    Assertions.assertThat(actual.getPagination().getCurrentPage()).isNotNull().isEqualTo(BigDecimal.valueOf(3));
    Assertions.assertThat(actual.getPagination().getSize()).isNotNull().isEqualTo(BigDecimal.valueOf(4));
  }

  @Test
  void map_when_bikes_is_valid_then_ok() {
    final var underTest = BikeResponseMapper.INSTANCE;
    final var request = TestData.prepareBike(1);

    final var actual = underTest.map(request);

    Assertions.assertThat(actual).isNotNull().isInstanceOf(BikeDto.class);
    assertBike(actual);
  }

  private static void assertBike(BikeDto bike) {
    Assertions.assertThat(bike.getId()).isNotNull().isEqualTo(BigDecimal.valueOf(1));
    Assertions.assertThat(bike.getName()).isNotNull().isEqualTo("anyName");
    Assertions.assertThat(bike.getDescription()).isNotNull().isEqualTo("anyDescription");
    Assertions.assertThat(bike.getPrice()).isNotNull().isEqualTo(120.25D);
    Assertions.assertThat(bike.getCurrency()).isNotNull().isEqualTo("EUR");
    Assertions.assertThat(bike.getManufacturer()).isNotNull();
    Assertions.assertThat(bike.getManufacturer().getId()).isNotNull().isEqualTo(BigDecimal.valueOf(100));
    Assertions.assertThat(bike.getManufacturer().getName()).isNotNull().isEqualTo("anyManufacturer");
    Assertions.assertThat(bike.getItems()).isNotNull()
        .hasSize(2)
        .map(ItemDto::getId)
        .containsExactly(BigDecimal.valueOf(3), BigDecimal.valueOf(4));

    final var item = bike.getItems().get(0);
    Assertions.assertThat(item.getId()).isNotNull().isEqualTo(BigDecimal.valueOf(3));
    Assertions.assertThat(item.getModel()).isNotNull().isEqualTo("anyModel");
    Assertions.assertThat(item.getDescription()).isNotNull().isEqualTo("anyDesc");
    Assertions.assertThat(item.getType()).isNotNull().isEqualTo("anyType");
    Assertions.assertThat(item.getTypeId()).isNotNull().isEqualTo(BigDecimal.valueOf(99));
  }


}
