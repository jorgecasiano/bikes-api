package com.casiano.bikes.api.mappers;

import com.casiano.bikes.domain.models.Bike;
import com.casiano.bikes.domain.models.BikesResponse;
import com.casiano.bikes.domain.models.Pagination;
import com.casiano.bikes.domain.models.requests.BikeRequest;
import com.casiano.bikes.dtos.BikeDto;
import com.casiano.bikes.dtos.BikeRequestDto;
import com.casiano.bikes.dtos.BikesResponseDto;
import com.casiano.bikes.dtos.PaginationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BikeResponseMapper {

  BikeResponseMapper INSTANCE = Mappers.getMapper(BikeResponseMapper.class);


  BikesResponseDto map(BikesResponse domain);

  BikeDto map(Bike domain);

  PaginationDto mapPagination(Pagination pagination);

}
