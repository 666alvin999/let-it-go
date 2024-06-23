package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;

public class SaveAlbum {

	private final AlbumPort albumPort;

	public SaveAlbum(AlbumPort albumPort) {
		this.albumPort = albumPort;
	}

	public ActionSuccess execute(Album album) {
		return this.albumPort.save(album);
	}

}
