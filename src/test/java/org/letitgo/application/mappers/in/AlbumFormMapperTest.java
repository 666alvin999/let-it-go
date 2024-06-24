package org.letitgo.application.mappers.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.in.AlbumForm;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.in.AlbumForm.createAlbumForm;

@Component
class AlbumFormMapperTest {

	private CreateAlbumFormMapper createAlbumFormMapper;

	@BeforeEach
	public void setUp() {
		this.createAlbumFormMapper = new CreateAlbumFormMapper();
	}

	@Test
	public void shouldMapToAlbum() {
	    // Arrange
		AlbumForm albumForm = createAlbumForm()
			.albumName("album")
			.username("ahamaide")
			.build();

	    // Act
		Album actualAlbum = this.createAlbumFormMapper.mapToAlbum(albumForm);

	    // Assert
		Album expectedAlbum = new Album(
			new AlbumName("album"),
			new Username("ahamaide")
		);

		assertThat(actualAlbum).isEqualTo(expectedAlbum);
	}

}