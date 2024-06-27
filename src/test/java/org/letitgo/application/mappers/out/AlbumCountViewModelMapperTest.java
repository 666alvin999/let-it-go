package org.letitgo.application.mappers.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.out.AlbumCountViewModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AlbumCountViewModelMapperTest {

	private AlbumCountViewModelMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new AlbumCountViewModelMapper();
	}

	@Test
	public void shouldReturnAlbumCountViewModel() {
	    // Act
		AlbumCountViewModel actualAlbumCountViewModel = this.mapper.mapToViewModel(3);

	    // Assert
	    AlbumCountViewModel expectedAlbumCountViewModel = new AlbumCountViewModel(3);

		assertThat(actualAlbumCountViewModel).isEqualTo(expectedAlbumCountViewModel);
	}

}