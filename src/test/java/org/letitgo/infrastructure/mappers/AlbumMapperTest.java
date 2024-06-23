package org.letitgo.infrastructure.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.AlbumDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.letitgo.infrastructure.dtos.AlbumDTO.albumDTO;

class AlbumMapperTest {

	private AlbumMapper albumMapper;

	@BeforeEach
	public void setUp() {
		this.albumMapper = new AlbumMapper();
	}

	@Test
	public void shouldMapToAlbumDTO() {
	    // Arrange
		Album album = new Album(
			new AlbumName("album"),
			new Username("ahamaide")
		);

	    // Act
		AlbumDTO actualAlbumDTO = this.albumMapper.mapToDTO(album);

	    // Assert
		AlbumDTO expectedAlbumDTO = albumDTO()
			.albumName("album")
			.username("ahamaide")
			.build();

		assertThat(actualAlbumDTO).isEqualTo(expectedAlbumDTO);
	}

	@Test
	public void shouldMapToAlbum() {
		// Arrange
		AlbumDTO albumDTO = albumDTO()
			.albumName("album")
			.username("ahamaide")
			.build();

		// Act
		Album actualAlbum = this.albumMapper.mapToAlbum(albumDTO);

		// Assert
		Album expectedAlbum = new Album(
			new AlbumName("album"),
			new Username("ahamaide")
		);

		assertThat(actualAlbum).isEqualTo(expectedAlbum);
	}

}