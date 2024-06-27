package org.letitgo.application.presenters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.out.AlbumCountViewModel;
import org.letitgo.application.mappers.out.AlbumCountViewModelMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumCountPresenterTest {

	private AlbumCountPresenter presenter;

	@Mock
	private AlbumCountViewModelMapper albumCountViewModelMapper;

	@BeforeEach
	public void setUp() {
		this.presenter = new AlbumCountPresenter(albumCountViewModelMapper);
	}

	@Test
	public void shouldReturnResponseEntity() {
	    // Arrange
		int albumCount = 3;
		AlbumCountViewModel viewModel = new AlbumCountViewModel(3);

		when(this.albumCountViewModelMapper.mapToViewModel(albumCount)).thenReturn(viewModel);

	    // Act
		ResponseEntity<String> actualResponseEntity = this.presenter.present(albumCount);

	    // Assert
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("{\"count\":" + albumCount + "}");

		assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
	}

}