package com.casiano.bikes.api.mappers;

import com.casiano.bikes.domain.models.requests.PaginationRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

class SearchBikeRequestMapperTest {

  @Test
  void map_when_input_is_null_then_empty_search() {
    final var underTest = SearchBikeRequestMapper.INSTANCE;

    final var actual = underTest.map(null, null, null, null,  null, null, null);

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.name()).isNull();
    Assertions.assertThat(actual.manufacturer()).isNull();
    Assertions.assertThat(actual.itemType()).isNull();
    Assertions.assertThat(actual.manufacturerId()).isNull();
    Assertions.assertThat(actual.itemTypeId()).isNull();
    Assertions.assertThat(actual.isAscendingOrder()).isNotNull().isFalse();
    Assertions.assertThat(actual.paginationRequest()).isNotNull();
  }

  @ParameterizedTest
  @CsvSource({
      "asc, true",
      "ASC, true",
      "desc, false",
      "any, false"
  })
  void map_when_input_is_valid_then_ok(String order, boolean expectedOrderAsc) {
    final var underTest = SearchBikeRequestMapper.INSTANCE;

    final var actual = underTest.map("anyName", "anyManufacturer", "anyItemType",
        BigDecimal.valueOf(1),  BigDecimal.valueOf(2), order, new PaginationRequest(3, 10));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.name()).isNotNull().isEqualTo("anyName");
    Assertions.assertThat(actual.manufacturer()).isNotNull().isEqualTo("anyManufacturer");
    Assertions.assertThat(actual.itemType()).isNotNull().isEqualTo("anyItemType");
    Assertions.assertThat(actual.manufacturerId()).isNotNull().isEqualTo(1L);
    Assertions.assertThat(actual.itemTypeId()).isNotNull().isEqualTo(2L);
    Assertions.assertThat(actual.isAscendingOrder()).isNotNull().isEqualTo(expectedOrderAsc);
    Assertions.assertThat(actual.paginationRequest()).isNotNull();
    Assertions.assertThat(actual.paginationRequest().page()).isNotNull().isEqualTo(3);
    Assertions.assertThat(actual.paginationRequest().size()).isNotNull().isEqualTo(10);

  }

}
