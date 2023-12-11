package com.casiano.bikes.api.mappers;

import com.casiano.bikes.domain.models.Item;
import com.casiano.bikes.dtos.BikeRequestDto;
import com.casiano.bikes.dtos.ItemRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.List;

class BikeRequestMapperTest {

  @ParameterizedTest
  @NullSource
  void map_when_input_is_null_then_null(BikeRequestDto request) {
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
    Assertions.assertThat(actual.name()).isNotNull().isEqualTo("anyName");
    Assertions.assertThat(actual.description()).isNotNull().isEqualTo("anyDescription");
    Assertions.assertThat(actual.price()).isNotNull().isEqualTo(120.25D);
    Assertions.assertThat(actual.currency()).isNotNull().isEqualTo("EUR");
    Assertions.assertThat(actual.manufacturerId()).isNotNull().isEqualTo(1);
    Assertions.assertThat(actual.items()).isNotNull()
        .hasSize(3)
        .map(Item::id)
        .containsExactly(2, 3, null);

    Assertions.assertThat(actual.items().get(2)).isNotNull()
        .matches(item -> {
          Assertions.assertThat(item.id()).isNull();
          Assertions.assertThat(item.model()).isNotNull().isEqualTo("anyModel");
          Assertions.assertThat(item.description()).isNotNull().isEqualTo("anyDesc");
          Assertions.assertThat(item.typeId()).isNotNull().isEqualTo(4);
          return true;
        });
  }

  private static BikeRequestDto prepareRequest() {
    return new BikeRequestDto()
        .name("anyName")
        .description("anyDescription")
        .price(120.25D)
        .currency("EUR")
        .manufacturerId(BigDecimal.valueOf(1))
        .existingItems(List.of(BigDecimal.valueOf(2), BigDecimal.valueOf(3)))
        .newItems(List.of(new ItemRequestDto()
            .model("anyModel")
            .description("anyDesc")
            .typeId(BigDecimal.valueOf(4))
        ));
  }

}
