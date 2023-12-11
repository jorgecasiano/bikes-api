package com.casiano.bikes.infrastructure.services;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.Item;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;
import com.casiano.bikes.infrastructure.entities.BikeEntity;
import com.casiano.bikes.infrastructure.entities.ItemEntity;
import com.casiano.bikes.infrastructure.repositories.BikeRepository;
import com.casiano.bikes.infrastructure.repositories.ItemRespository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BikeServiceImplTest {

  private BikeServiceImpl underTest;

  private BikeRepository bikeRepository;

  private ItemRespository itemRepository;

  @BeforeEach
  void setup() {
    this.bikeRepository = mock(BikeRepository.class);
    this.itemRepository = mock(ItemRespository.class);

    this.underTest = new BikeServiceImpl(bikeRepository, itemRepository);
  }

  @Test
  void createBike_when_input_is_valid_and_new_items_then_save_bike() {
    when(bikeRepository.saveAndFlush(any())).thenReturn(prepareBikeEntity(1L));
    final var items = List.of(new Item(null, "model", "desc", null, null));

    final var actual = underTest.create(new BikeRequest(null, null, null, null, null, items));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.id()).isNotNull().isEqualTo(1);

    verify(bikeRepository, times(1)).saveAndFlush(any());
    verify(itemRepository, never()).getReferenceById(any());
  }

  @Test
  void createBike_when_input_is_valid_and_existing_items_then_save_bike() {
    when(bikeRepository.saveAndFlush(any())).thenReturn(prepareBikeEntity(1L));
    when(itemRepository.getReferenceById(any())).thenReturn(prepareItemEntity(1L));
    final var items = List.of(new Item(1, null, null, null, null));

    final var actual = underTest.create(new BikeRequest(null, null, null, null, null, items));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.id()).isNotNull().isEqualTo(1);

    verify(bikeRepository, times(1)).saveAndFlush(any());
    verify(itemRepository, times(1)).getReferenceById(any());
  }

  @Test
  void searchBikes_when_input_is_valid_then_return_bikes() {
    final var response = new PageImpl(List.of(prepareBikeEntity(1L), prepareBikeEntity(2L)), PageRequest.of(1, 2), 6);
    when(bikeRepository.searchBikes(any(), any(), any(), any(), any(), any())).thenReturn(response);

    final var actual = underTest.search(new SearchBikeRequest(null, null, null, null, null, false, null));

    Assertions.assertThat(actual).isNotNull();
    Assertions.assertThat(actual.results()).isNotNull()
        .hasSize(2)
        .map(Bike::id)
        .containsExactly(1, 2);

    verify(bikeRepository, times(1)).searchBikes(any(), any(), any(), any(), any(), any());
  }

  private static BikeEntity prepareBikeEntity(Long id) {
    final var bikeEntity = new BikeEntity();
    bikeEntity.setId(id);
    return bikeEntity;
  }

  private static ItemEntity prepareItemEntity(Long id) {
    final var itemEntity = new ItemEntity();
    itemEntity.setId(id);
    return itemEntity;
  }


}
