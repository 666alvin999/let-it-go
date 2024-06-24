package org.letitgo.application.mappers.out;

import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.domain.beans.Album;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.letitgo.application.dtos.out.AlbumViewModel.albumViewModel;

@Component
public class AlbumViewModelMapper {

	public AlbumViewModel mapToViewModel(Album album) {
		return albumViewModel()
			.albumName(album.albumName().value())
			.username(album.username().value())
			.build();
	}

	public List<AlbumViewModel> mapAllToViewModel(List<Album> albums) {
		return albums.stream().map(this::mapToViewModel).toList();
	}

}
