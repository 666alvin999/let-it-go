package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.Album;
import org.letitgo.domain.ports.AlbumPort;

import java.util.List;

public class GetAlbumsByUsername {

	private final AlbumPort albumPort;

	public GetAlbumsByUsername(AlbumPort albumPort) {
		this.albumPort = albumPort;
	}

	public List<Album> execute(String username) {
		return this.albumPort.getAlbumsByUsername(username);
	}
}
