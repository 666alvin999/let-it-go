package org.letitgo.application.presenters;

import com.google.gson.Gson;
import org.letitgo.application.dtos.out.AlbumCountViewModel;
import org.letitgo.application.mappers.out.AlbumCountViewModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AlbumCountPresenter {

	private final AlbumCountViewModelMapper albumCountViewModelMapper;

	@Autowired
	public AlbumCountPresenter(AlbumCountViewModelMapper albumCountViewModelMapper) {
		this.albumCountViewModelMapper = albumCountViewModelMapper;
	}

	public ResponseEntity<String> present(int albumCount) {
		AlbumCountViewModel albumCountViewModel = this.albumCountViewModelMapper.mapToViewModel(albumCount);

		return ResponseEntity.ok(new Gson().toJson(albumCountViewModel));
	}

}
