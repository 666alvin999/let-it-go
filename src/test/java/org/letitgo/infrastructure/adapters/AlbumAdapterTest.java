package org.letitgo.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.daos.AlbumDao;
import org.letitgo.infrastructure.dtos.AlbumDTO;
import org.letitgo.infrastructure.mappers.AlbumMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.letitgo.infrastructure.dtos.AlbumDTO.albumDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumAdapterTest {

	private AlbumAdapter albumAdapter;

	@Mock
	private AlbumDao albumDao;

	@Mock
	private AlbumMapper albumMapper;

	@BeforeEach
	public void setUp() {
		this.albumAdapter = new AlbumAdapter(this.albumDao, this.albumMapper);
	}

	@Test
	public void shouldSaveAlbumSuccessfully() {
	    // Arrange
		Album album = new Album(
			new AlbumName("album"),
			new Username("ahamaide")
		);

		AlbumDTO albumDTO = albumDTO()
			.albumName("album")
			.username("ahamaide")
			.build();

		when(this.albumMapper.mapToDTO(album)).thenReturn(albumDTO);
		when(this.albumDao.save(albumDTO)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.albumAdapter.save(album);

	    // Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}