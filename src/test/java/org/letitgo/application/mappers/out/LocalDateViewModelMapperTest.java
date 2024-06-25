package org.letitgo.application.mappers.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.out.LocalDateViewModel;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.out.LocalDateViewModel.localDateViewModel;

class LocalDateViewModelMapperTest {

	private LocalDateViewModelMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new LocalDateViewModelMapper();
	}

	@Test
	public void shouldMapToViewModel() {
		// Arrange
		LocalDate localDate = LocalDate.of(2024, 1, 1);

		// Act
		LocalDateViewModel actualViewModel = this.mapper.mapToViewModel(localDate);

		// Assert
		LocalDateViewModel expectedViewModel = localDateViewModel()
			.date("2024-01-01")
			.build();

		assertThat(actualViewModel).isEqualTo(expectedViewModel);
	}

}