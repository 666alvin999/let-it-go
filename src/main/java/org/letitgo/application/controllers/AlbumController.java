package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.CreateAlbumForm;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.application.mappers.in.CreateAlbumFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.application.presenters.AlbumPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;
import org.letitgo.domain.usecases.GetAlbumsByUsername;
import org.letitgo.domain.usecases.SaveAlbum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumController {

	private final SaveAlbum saveAlbum;
	private final CreateAlbumFormMapper createAlbumFormMapper;
	private final ActionSuccessPresenter actionSuccessPresenter;

	private final GetAlbumsByUsername getAlbumsByUsername;
	private final AlbumPresenter albumPresenter;

	public AlbumController(AlbumPort albumPort, CreateAlbumFormMapper createAlbumFormMapper, ActionSuccessPresenter actionSuccessPresenter, AlbumPresenter albumPresenter) {
		this.saveAlbum = new SaveAlbum(albumPort);
		this.getAlbumsByUsername = new GetAlbumsByUsername(albumPort);
		this.createAlbumFormMapper = createAlbumFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
		this.albumPresenter = albumPresenter;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody CreateAlbumForm  createAlbumForm) {
		Album album = this.createAlbumFormMapper.mapToAlbum(createAlbumForm);
		ActionSuccess actionSuccess = this.saveAlbum.execute(album);

		return actionSuccessPresenter.present(actionSuccess);
	}

	@GetMapping("/getAlbums")
	public ResponseEntity<String> getAlbumsByUsername(@RequestParam String username) {
		List<Album> albums = this.getAlbumsByUsername.execute(username);
		return this.albumPresenter.presentAll(albums);
	}

}
