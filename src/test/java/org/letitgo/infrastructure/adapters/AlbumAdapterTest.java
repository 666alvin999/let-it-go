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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
		Album album = this.getAlbums().getFirst();

		AlbumDTO albumDTO = this.getAlbumDTOs().getFirst();

		when(this.albumMapper.mapToDTO(album)).thenReturn(albumDTO);
		when(this.albumDao.save(albumDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.albumAdapter.save(album);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDeleteAlbumSuccessfully() {
		// Arrange
		Album album = this.getAlbums().getFirst();

		AlbumDTO albumDTO = this.getAlbumDTOs().getFirst();

		when(this.albumMapper.mapToDTO(album)).thenReturn(albumDTO);
		when(this.albumDao.delete(albumDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.albumAdapter.delete(album);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldGetAlbumsByUsername() {
		// Arrange
		String username = "ahamaide";

		List<AlbumDTO> albumDTOs = this.getAlbumDTOs();

		List<Album> albums = this.getAlbums();

		when(this.albumDao.getAlbumsByUsername("ahamaide")).thenReturn(albumDTOs);
		when(this.albumMapper.mapAllToAlbums(albumDTOs)).thenReturn(albums);

		// Act
		List<Album> actualAlbums = this.albumAdapter.getAlbumsByUsername(username);

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

	private List<AlbumDTO> getAlbumDTOs() {
		return List.of(
			albumDTO()
				.albumName("album1")
				.username("ahamaide")
				.build(),
			albumDTO()
				.albumName("album2")
				.username("ahamaide")
				.build()
		);
	}

}