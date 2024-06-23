package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.CreateAlbumForm;
import org.letitgo.application.mappers.in.CreateAlbumFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;
import org.letitgo.domain.usecases.SaveAlbum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/album")
public class AlbumController {

	private final SaveAlbum saveAlbum;
	private final CreateAlbumFormMapper createAlbumFormMapper;
	private final ActionSuccessPresenter actionSuccessPresenter;

	public AlbumController(AlbumPort albumPort, CreateAlbumFormMapper createAlbumFormMapper, ActionSuccessPresenter actionSuccessPresenter) {
		this.saveAlbum = new SaveAlbum(albumPort);
		this.createAlbumFormMapper = createAlbumFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody CreateAlbumForm  createAlbumForm) {
		Album album = this.createAlbumFormMapper.mapToAlbum(createAlbumForm);
		ActionSuccess actionSuccess = this.saveAlbum.execute(album);

		return actionSuccessPresenter.present(actionSuccess);
	}

}
