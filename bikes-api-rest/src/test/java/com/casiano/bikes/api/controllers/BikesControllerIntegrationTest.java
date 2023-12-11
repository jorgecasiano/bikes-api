package com.casiano.bikes.api.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.casiano.bikes.api.TestData;
import com.casiano.bikes.domain.usecases.CreateBikeUseCase;
import com.casiano.bikes.domain.usecases.SearchBikesUseCase;
import com.casiano.bikes.dtos.BikeRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled // Pending configuring h2 for tests, it can be run for now with docker-compose-local up
@SpringBootTest
@AutoConfigureMockMvc
class BikesControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SearchBikesUseCase searchBikesUseCase;

	@MockBean
	private CreateBikeUseCase createBikeUseCase;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testCreateBike() throws Exception {
		BikeRequestDto requestDto = new BikeRequestDto();

		when(createBikeUseCase.create(any())).thenReturn(TestData.prepareBike(1));

		mockMvc.perform(post("/bikes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestDto)))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(1))
			.andExpect(content().json("{\"id\":1,\"name\":\"anyName\",\"description\":\"anyDescription\",\"price\":120.25,"
				+ "\"currency\":\"EUR\",\"manufacturer\":{\"id\":100,\"name\":\"anyManufacturer\"},"
				+ "\"items\":[{\"id\":3,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"}"
				+ ",{\"id\":4,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"}]}"));
	}

	@Test
	void testSearchBikes() throws Exception {
		when(searchBikesUseCase.search(any())).thenReturn(TestData.prepareBikesResponse());

		mockMvc.perform(get("/bikes")
				.param("name", "anyName")
				.param("manufacturer", "anyManufacturer")
				.param("itemType", "anyItemType")
				.param("order", "ASC"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.pagination.total_items").value(1))
			.andExpect(jsonPath("$.results", hasSize(2)))
			.andExpect(content().json("{\"results\":["
				+ "{\"id\":1,\"name\":\"anyName\",\"description\":\"anyDescription\",\"price\":120.25,\"currency\":\"EUR\",\"manufacturer\":"
				+ "{\"id\":100,\"name\":\"anyManufacturer\"},"
				+ "\"items\":[{\"id\":3,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"},{\"id\":4,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"}]},"
				+ "{\"id\":2,\"name\":\"anyName\",\"description\":\"anyDescription\",\"price\":120.25,\"currency\":\"EUR\","
				+ "\"manufacturer\":{\"id\":100,\"name\":\"anyManufacturer\"},"
				+ "\"items\":[{\"id\":3,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"},{\"id\":4,\"model\":\"anyModel\",\"type_id\":99,\"type\":\"anyType\",\"description\":\"anyDesc\"}]}],"
				+ "\"pagination\":{\"total_items\":1,\"total_pages\":2,\"current_page\":3,\"size\":4}}"));
	}

}