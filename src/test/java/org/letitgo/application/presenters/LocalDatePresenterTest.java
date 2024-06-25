package org.letitgo.application.presenters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.application.dtos.out.LocalDateViewModel;
import org.letitgo.application.mappers.out.LocalDateViewModelMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.out.AlbumViewModel.albumViewModel;
import static org.letitgo.application.dtos.out.LocalDateViewModel.localDateViewModel;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocalDatePresenterTest {

	private LocalDatePresenter localDatePresenter;

	@Mock
	private LocalDateViewModelMapper mapper;

	@BeforeEach
	public void setUp() {
		this.localDatePresenter = new LocalDatePresenter(mapper);
	}

	@Test
	public void shouldPresentLocalDate() {
		// Arrange
		LocalDate localDate = LocalDate.of(2024, 1, 1);

		LocalDateViewModel localDateViewModel = localDateViewModel()
			.date("2024-01-01")
			.build();

		when(this.mapper.mapToViewModel(localDate)).thenReturn(localDateViewModel);

		// Act
		ResponseEntity<String> actualResponseEntity = this.localDatePresenter.present(localDate);

		// Assert
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("{\"date\":\"2024-01-01\"}");

		assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
	}

	@Test
	public void shouldPresentAllLocalDate() {
		// Arrange
		Set<LocalDate> localDates = Set.of(
			LocalDate.of(2024, 1, 1),
			LocalDate.of(2024, 1, 2)
		);

		List<LocalDateViewModel> localDateViewModels = List.of(
			localDateViewModel()
				.date("2024-01-01")
				.build(),
			localDateViewModel()
				.date("2024-01-02")
				.build()
		);

		when(this.mapper.mapAllToViewModel(localDates)).thenReturn(localDateViewModels);

		// Act
		ResponseEntity<String> actualResponseEntity = this.localDatePresenter.presentAll(localDates);

		// Assert
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("[{\"date\":\"2024-01-01\"},{\"date\":\"2024-01-02\"}]");

		assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
	}

}