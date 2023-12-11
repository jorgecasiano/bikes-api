package com.casiano.bikes.api.controllers;

import com.casiano.bikes.api.TestData;
import com.casiano.bikes.domain.usecases.CreateBikeUseCase;
import com.casiano.bikes.domain.usecases.SearchBikesUseCase;
import com.casiano.bikes.dtos.BikeRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatusCode;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class BikesControllerTest {

  private BikesController underTest;

  private SearchBikesUseCase searchBikesUseCase;

  private CreateBikeUseCase createBikeUseCase;

  @BeforeEach
  void setup() {
    this.searchBikesUseCase = mock(SearchBikesUseCase.class);
    this.createBikeUseCase = mock(CreateBikeUseCase.class);

    this.underTest = new BikesController(searchBikesUseCase, createBikeUseCase);
  }

  @Test
  void createBike_when_input_is_valid_then_201() {
    when(createBikeUseCase.create(any())).thenReturn(TestData.prepareBike(1));

   final var actual = underTest.createBike(new BikeRequestDto());

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.getStatusCode()).isNotNull().isEqualTo(HttpStatusCode.valueOf(201));
    Assertions.assertThat(actual.getBody()).isNotNull();
    Assertions.assertThat(actual.getBody().getId()).isNotNull().isEqualTo(BigDecimal.valueOf(1));

    verify(createBikeUseCase, times(1)).create(any());
  }

  @ParameterizedTest
  @CsvSource({
      "asc, true",
      "desc, false",
      "any, false"
  })
  void searchBike_when_input_is_valid_then_201(String order, boolean expectedOrderAsc) {
    when(searchBikesUseCase.search(any())).thenReturn(TestData.prepareBikesResponse());

    final var actual = underTest.searchBikes("anyName", "anyManufacturer", "anyType", null, null, order, null, null);

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.getStatusCode()).isNotNull().isEqualTo(HttpStatusCode.valueOf(200));
    Assertions.assertThat(actual.getBody()).isNotNull();

    Assertions.assertThat(actual.getBody().getPagination()).isNotNull();
    Assertions.assertThat(actual.getBody().getResults()).isNotNull()
        .hasSize(2);

    verify(searchBikesUseCase, times(1)).search(argThat(searchRequest -> {
      Assertions.assertThat(searchRequest.name()).isNotNull().isEqualTo("anyName");
      Assertions.assertThat(searchRequest.manufacturer()).isNotNull().isEqualTo("anyManufacturer");
      Assertions.assertThat(searchRequest.itemType()).isNotNull().isEqualTo("anyType");
      Assertions.assertThat(searchRequest.isAscendingOrder()).isNotNull().isEqualTo(expectedOrderAsc);
      return true;
    }));
  }

}