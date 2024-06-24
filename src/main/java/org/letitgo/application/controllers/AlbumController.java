package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.AlbumForm;
import org.letitgo.application.mappers.in.CreateAlbumFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.application.presenters.AlbumPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.domain.usecases.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/album")
@CrossOrigin(origins = "*")
public class AlbumController {

	private final SaveAlbum saveAlbum;
	private final DeleteAlbum deleteAlbum;
	private final CreateAlbumFormMapper createAlbumFormMapper;
	private final ActionSuccessPresenter actionSuccessPresenter;

	private final GetAlbumsByUsername getAlbumsByUsername;
	private final AlbumPresenter albumPresenter;

	private final GetMediaNamesByAlbumNameAndUsername getMediaNamesByAlbumNameAndUsername;
	private final DeleteMediasByMediaNames deleteMediasByMediaNames;

	public AlbumController(AlbumPort albumPort, MemoryPort memoryPort, CreateAlbumFormMapper createAlbumFormMapper, ActionSuccessPresenter actionSuccessPresenter, AlbumPresenter albumPresenter) {
		this.saveAlbum = new SaveAlbum(albumPort);
		this.deleteAlbum = new DeleteAlbum(albumPort);
		this.getMediaNamesByAlbumNameAndUsername = new GetMediaNamesByAlbumNameAndUsername(memoryPort);
		this.deleteMediasByMediaNames = new DeleteMediasByMediaNames(memoryPort);
		this.getAlbumsByUsername = new GetAlbumsByUsername(albumPort);
		this.createAlbumFormMapper = createAlbumFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
		this.albumPresenter = albumPresenter;
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody AlbumForm albumForm) {
		Album album = this.createAlbumFormMapper.mapToAlbum(albumForm);
		ActionSuccess actionSuccess = this.saveAlbum.execute(album);

		return actionSuccessPresenter.present(actionSuccess);
	}

	@PostMapping("/delete")
	public ResponseEntity<String> delete(@RequestBody AlbumForm albumForm) {
		List<String> mediaNames = this.getMediaNamesByAlbumNameAndUsername.execute(albumForm.getAlbumName(), albumForm.getUsername());

		ActionSuccess deleteMediasSuccess = this.deleteMediasByMediaNames.execute(mediaNames);

		if (deleteMediasSuccess.success()) {
			Album album = this.createAlbumFormMapper.mapToAlbum(albumForm);
			ActionSuccess deleteAlbumSuccess = this.deleteAlbum.execute(album);

			return actionSuccessPresenter.present(deleteAlbumSuccess);
		}

		return this.actionSuccessPresenter.present(deleteMediasSuccess);
	}

	@GetMapping("/getAlbums")
	public ResponseEntity<String> getAlbumsByUsername(@RequestParam String username) {
		List<Album> albums = this.getAlbumsByUsername.execute(username);
		return this.albumPresenter.presentAll(albums);
	}

}
