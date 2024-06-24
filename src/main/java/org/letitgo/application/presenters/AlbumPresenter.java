package org.letitgo.application.presenters;

import com.google.gson.Gson;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.application.mappers.out.AlbumViewModelMapper;
import org.letitgo.domain.beans.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AlbumPresenter {

	private final AlbumViewModelMapper albumViewModelMapper;

	@Autowired
	public AlbumPresenter(AlbumViewModelMapper albumViewModelMapper) {
		this.albumViewModelMapper = albumViewModelMapper;
	}

	public ResponseEntity<String> present(Album album) {
		AlbumViewModel albumViewModel = this.albumViewModelMapper.mapToViewModel(album);
		return ResponseEntity.ok(new Gson().toJson(albumViewModel));
	}

	public ResponseEntity<String> presentAll(List<Album> albums) {
		List<AlbumViewModel> albumViewModels = this.albumViewModelMapper.mapAllToViewModel(albums);
		return ResponseEntity.ok(new Gson().toJson(albumViewModels));
	}
}
