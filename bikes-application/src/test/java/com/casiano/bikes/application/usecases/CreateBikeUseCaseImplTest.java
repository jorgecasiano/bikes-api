package com.casiano.bikes.application.usecases;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.domain.services.BikeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateBikeUseCaseImplTest {

  private CreateBikeUseCaseImpl underTest;

  private BikeService bikeService;

  @BeforeEach
  void setup() {
    this.bikeService = mock(BikeService.class);

    this.underTest = new CreateBikeUseCaseImpl(bikeService);
  }

  @ParameterizedTest
  @NullSource
  void createBike_when_input_is_null_then_throw_exception(BikeRequest request) {
    Assertions.assertThatThrownBy(() -> underTest.create(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("'request' cannot be null");

    verify(bikeService, never()).create(any());
  }

  @Test
  void createBike_when_input_is_valid_then_return_bike() {
    when(bikeService.create(any())).thenReturn(TestData.prepareBike(1));

    final var actual = underTest.create(new BikeRequest(null, null, null, null, null, null));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.id()).isNotNull().isEqualTo(1);

    verify(bikeService, times(1)).create(any());
  }

}