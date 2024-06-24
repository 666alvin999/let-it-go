package org.letitgo.application.presenters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.application.mappers.out.AlbumViewModelMapper;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.userfields.Username;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.out.AlbumViewModel.albumViewModel;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumPresenterTest {

	private AlbumPresenter albumPresenter;

	@Mock
	private AlbumViewModelMapper albumViewModelMapper;

	@BeforeEach
	public void setUp() {
		this.albumPresenter = new AlbumPresenter(albumViewModelMapper);
	}

	@Test
	public void shouldPresentAlbum() {
		// Arrange
		Album album = new Album(
			new AlbumName("album1"),
			new Username("ahamaide")
		);

		AlbumViewModel albumViewModel = albumViewModel()
			.albumName("album1")
			.username("ahamaide")
			.build();

		when(this.albumViewModelMapper.mapToViewModel(album)).thenReturn(albumViewModel);

		// Act
		ResponseEntity<String> actualResponseEntity = this.albumPresenter.present(album);

		// Assert
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("{\"albumName\":\"album1\",\"username\":\"ahamaide\"}");

		assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
	}

	@Test
	public void shouldPresentAllAlbums() {
		// Arrange
		List<Album> albums = List.of(
			new Album(
				new AlbumName("album1"),
				new Username("ahamaide")
			),
			new Album(
				new AlbumName("album2"),
				new Username("ahamaide")
			)
		);

		List<AlbumViewModel> albumViewModels = List.of(
			albumViewModel()
				.albumName("album1")
				.username("ahamaide")
				.build(),
			albumViewModel()
				.albumName("album2")
				.username("ahamaide")
				.build()
		);

		when(this.albumViewModelMapper.mapAllToViewModel(albums)).thenReturn(albumViewModels);

		// Act
		ResponseEntity<String> actualResponseEntity = this.albumPresenter.presentAll(albums);

		// Assert
		ResponseEntity<String> expectedResponseEntity = ResponseEntity.ok("[{\"albumName\":\"album1\",\"username\":\"ahamaide\"},{\"albumName\":\"album2\",\"username\":\"ahamaide\"}]");

		assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
	}

}