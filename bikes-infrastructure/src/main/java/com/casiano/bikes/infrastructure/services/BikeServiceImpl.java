package com.casiano.bikes.infrastructure.services;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.domain.models.requests.PaginationRequest;
import com.casiano.bikes.domain.models.requests.SearchBikeRequest;
import com.casiano.bikes.domain.services.BikeService;
import com.casiano.bikes.infrastructure.entities.BikeEntity;
import com.casiano.bikes.infrastructure.mappers.BikeRequestMapper;
import com.casiano.bikes.infrastructure.mappers.BikeResponseMapper;
import com.casiano.bikes.infrastructure.repositories.BikeRepository;
import com.casiano.bikes.infrastructure.repositories.ItemRespository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeServiceImpl implements BikeService {

  Logger logger = LoggerFactory.getLogger(BikeServiceImpl.class);

  private BikeRepository bikeRepository;

  private ItemRespository itemRepository;

  public BikeServiceImpl(BikeRepository bikeRepository, ItemRespository itemRepository) {
    this.bikeRepository = bikeRepository;
    this.itemRepository = itemRepository;
  }

  @CacheEvict("bikes")
  @Override
  public Bike create(BikeRequest request) {
    logger.info("Creating bike: {}", request);
    return Optional.ofNullable(request)
        .map(BikeRequestMapper.INSTANCE::map)
        .map(this::retrieveExistingItems)
        .map(this.bikeRepository::saveAndFlush)
        .map(BikeResponseMapper.INSTANCE::mapBike)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected error"));
  }

  private BikeEntity retrieveExistingItems(BikeEntity bikeEntity) {
    var items = bikeEntity.getItems()
        .stream().map(itemEntity -> {
          if (Objects.nonNull(itemEntity.getId())) {
            return itemRepository.getReferenceById(itemEntity.getId());
          } else {
            return itemEntity;
          }
        }).collect(Collectors.toSet());

    bikeEntity.setItems(items);

    return bikeEntity;
  }

  @Cacheable("bikes")
  @Override
  public BikesResponse search(SearchBikeRequest request) {
    logger.info("Searching bikes: {}", request);
    return Optional.ofNullable(request)
        .map(req -> bikeRepository.searchBikes(req.name(), req.manufacturer(), req.itemType(),
            req.manufacturerId(), req.itemTypeId(), calculatePageable(req)))
        .map(BikeResponseMapper.INSTANCE::map)
        .orElseThrow(() -> new IllegalArgumentException("Unexpected error"));
  }

  private static Sort calculateSort(SearchBikeRequest request) {
    return Sort.by(request.isAscendingOrder() ? Sort.Direction.ASC : Sort.Direction.DESC, "name");
  }

  private static Pageable calculatePageable(SearchBikeRequest request) {
    final var pagination = Optional.of(request)
        .map(SearchBikeRequest::paginationRequest)
        .orElseGet(PaginationRequest::createDefault);
    return PageRequest.of(pagination.page(), pagination.size(), calculateSort(request));
  }

}
