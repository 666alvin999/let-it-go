package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.AlbumPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAlbumsByUsernameTest {

	private GetAlbumsByUsername getAlbumsByUsername;

	@Mock
	private AlbumPort albumPort;

	@BeforeEach
	public void setUp() {
		this.getAlbumsByUsername = new GetAlbumsByUsername(albumPort);
	}

	@Test
	public void Name() {
	    // Arrange
	    when(this.albumPort.getAlbumsByUsername("ahamaide")).thenReturn(this.getAlbums());

	    // Act
		List<Album> actualAlbums = this.getAlbumsByUsername.execute("ahamaide");

	    // Assert
		List<Album> expectedAlbums = this.getAlbums();

		assertThat(actualAlbums).isEqualTo(expectedAlbums);
	}

	private List<Album> getAlbums() {
		return List.of(
			new Album(
				new AlbumName("album1"),
				new Username("ahamaide")
			),
			new Album(
				new AlbumName("album2"),
				new Username("ahamaide")
			)
		);
	}

}