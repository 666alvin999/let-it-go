package org.letitgo.application.mappers.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.letitgo.application.dtos.out.AlbumViewModel.albumViewModel;

class AlbumViewModelMapperTest {

	private AlbumViewModelMapper albumMapper;

	@BeforeEach
	public void setUp() {
		this.albumMapper = new AlbumViewModelMapper();
	}

	@Test
	public void shouldMapAlbumToViewModel() {
	    // Arrange
		Album album = new Album(
			new AlbumName("album1"),
			new Username("ahamaide")
		);

	    // Act
		AlbumViewModel actualAlbumViewModel = this.albumMapper.mapToViewModel(album);

	    // Assert
	    AlbumViewModel expectedAlbumViewModel = albumViewModel()
		    .albumName("album1")
		    .username("ahamaide")
		    .build();

		assertThat(actualAlbumViewModel).isEqualTo(expectedAlbumViewModel);
	}

}