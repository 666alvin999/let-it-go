package org.letitgo.domain.usecases;

import org.letitgo.domain.ports.AlbumPort;

public class GetAlbumCountByUsername {

	private final AlbumPort albumPort;

	public GetAlbumCountByUsername(AlbumPort albumPort) {
		this.albumPort = albumPort;
	}

	public int execute(String username) {
		return albumPort.getAlbumCountByUsername(username);
	}

}
