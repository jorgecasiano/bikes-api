package com.casiano.bikes.application.usecases;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;
import com.casiano.bikes.domain.services.BikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SearchBikesUseCaseImplTest {

  private SearchBikesUseCaseImpl underTest;

  private BikeService bikeService;

  @BeforeEach
  void setup() {
    this.bikeService = mock(BikeService.class);

    this.underTest = new SearchBikesUseCaseImpl(bikeService);
  }

  @ParameterizedTest
  @NullSource
  void searchBikes_when_input_is_null_then_throw_exception(SearchBikeRequest request) {
    Assertions.assertThatThrownBy(() -> underTest.search(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'request' cannot be null");

    verify(bikeService, never()).create(any());
  }

  @Test
  void searchBikes_when_input_is_valid_then_return_bikes() {
    when(bikeService.search(any())).thenReturn(TestData.prepareBikesResponse());

    final var actual = underTest.search(new SearchBikeRequest(null, null, null, null, null, false, null));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.results()).isNotNull()
        .hasSize(2)
        .map(Bike::id)
        .containsExactly(1, 2);

    verify(bikeService, times(1)).search(any());
  }

}