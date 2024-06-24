package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.AlbumPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveAlbumTest {

	private SaveAlbum saveAlbum;

	@Mock
	private AlbumPort albumPort;

	@BeforeEach
	public void setUp() {
		this.saveAlbum = new SaveAlbum(albumPort);
	}

	@Test
	public void shouldSaveAlbumSuccessfully() {
		// Arrange
		Album album = new Album(
			new AlbumName("album"),
			new Username("ahamaide")
		);

		when(this.albumPort.save(album)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.saveAlbum.execute(album);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}