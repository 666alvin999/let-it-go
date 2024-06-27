package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.ports.AlbumPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAlbumCountByUsernameTest {

	private GetAlbumCountByUsername getAlbumCountByUsername;

	@Mock
	private AlbumPort albumPort;

	@BeforeEach
	public void setUp() {
		this.getAlbumCountByUsername = new GetAlbumCountByUsername(albumPort);
	}

	@Test
	public void shouldReturnAlbumCount() {
		// Arrange
		int albumCount = 2;
		String username = "ahamaide";

		when(this.albumPort.getAlbumCountByUsername("ahamaide")).thenReturn(albumCount);

		// Act
		int actualAlbumCount = this.getAlbumCountByUsername.execute(username);

		// Assert
		int expectedAlbumCount = 2;

		assertThat(actualAlbumCount).isEqualTo(expectedAlbumCount);
	}

}