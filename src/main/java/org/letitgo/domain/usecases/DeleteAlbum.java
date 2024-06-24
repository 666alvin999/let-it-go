package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;

public class DeleteAlbum {

	private final AlbumPort albumPort;

	public DeleteAlbum(AlbumPort albumPort) {
		this.albumPort = albumPort;
	}

	public ActionSuccess execute(Album album) {
		return this.albumPort.delete(album);
	}

}
