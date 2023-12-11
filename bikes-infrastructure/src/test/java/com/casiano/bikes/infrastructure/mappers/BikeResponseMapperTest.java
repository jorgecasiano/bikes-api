package com.casiano.bikes.infrastructure.mappers;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.infrastructure.entities.BikeEntity;
import com.casiano.bikes.infrastructure.entities.ItemEntity;
import com.casiano.bikes.infrastructure.entities.ItemTypeEntity;
import com.casiano.bikes.infrastructure.entities.ManufacturerEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

class BikeResponseMapperTest {

  @Test
  void map_when_input_is_null_or_empty_then_response_with_result_empty() {
    final var underTest = BikeResponseMapper.INSTANCE;

    final var actual = underTest.map(null);

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.results()).isNotNull().isEmpty();
  }

  @Test
  void map_when_input_is_valid_then_ok() {
    final var underTest = BikeResponseMapper.INSTANCE;
    final var entity = new PageImpl(List.of(prepareBikeEntity(1L), prepareBikeEntity(2L)), PageRequest.of(1, 2), 6);

    final var actual = underTest.map(entity);

    Assertions.assertThat(actual).isNotNull();

    Assertions.assertThat(actual.pagination()).isNotNull();
    Assertions.assertThat(actual.pagination().totalItems()).isNotNull().isEqualTo(6);
    Assertions.assertThat(actual.pagination().totalPages()).isNotNull().isEqualTo(3);
    Assertions.assertThat(actual.pagination().currentPage()).isNotNull().isEqualTo(1);
    Assertions.assertThat(actual.pagination().size()).isNotNull().isEqualTo(2);

    Assertions.assertThat(actual.results()).isNotNull()
        .hasSize(2)
        .map(Bike::id)
        .containsExactly(1, 2);

    final var bike = actual.results().get(0);

    Assertions.assertThat(bike.name()).isNotNull().isEqualTo("anyName");
    Assertions.assertThat(bike.description()).isNotNull().isEqualTo("anyDescription");
    Assertions.assertThat(bike.price()).isNotNull().isEqualTo(120.25D);
    Assertions.assertThat(bike.currency()).isNotNull().isEqualTo("EUR");

    Assertions.assertThat(bike.manufacturer()).isNotNull();
    Assertions.assertThat(bike.manufacturer().id()).isNotNull().isEqualTo(90L);
    Assertions.assertThat(bike.manufacturer().name()).isNotNull().isEqualTo("anyManufacturer");

    Assertions.assertThat(bike.items()).isNotNull()
        .hasSize(1)
        .first()
        .matches(item -> {
          Assertions.assertThat(item.id()).isNotNull().isEqualTo(100);
          Assertions.assertThat(item.model()).isNotNull().isEqualTo("anyModel");
          Assertions.assertThat(item.description()).isNotNull().isEqualTo("anyDesc");
          Assertions.assertThat(item.type()).isNotNull().isEqualTo("anyType");
          Assertions.assertThat(item.typeId()).isNotNull().isEqualTo(102);

          return true;
        });
  }

  private static BikeEntity prepareBikeEntity(Long id) {
    final var bikeEntity = new BikeEntity();
    bikeEntity.setId(id);
    bikeEntity.setName("anyName");
    bikeEntity.setDescription("anyDescription");
    bikeEntity.setPrice(new BigDecimal("120.25"));
    bikeEntity.setCurrency("EUR");
    ManufacturerEntity manufacturer = new ManufacturerEntity();
    manufacturer.setId(90L);
    manufacturer.setName("anyManufacturer");
    bikeEntity.setManufacturer(manufacturer);

    ItemEntity item = new ItemEntity();
    item.setId(100L);
    item.setModel("anyModel");
    item.setDescription("anyDesc");
    ItemTypeEntity type = new ItemTypeEntity();
    type.setId(102L);
    type.setName("anyType");
    item.setType(type);
    bikeEntity.setItems(Set.of(item));

    return bikeEntity;

  }

}
