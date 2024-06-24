package org.letitgo.domain.usecases;

import org.letitgo.domain.ports.MemoryPort;

import java.util.List;

public class GetMediaNamesByAlbumNameAndUsername {

	private final MemoryPort memoryPort;

	public GetMediaNamesByAlbumNameAndUsername(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public List<String> execute(String albumName, String username) {
		return this.memoryPort.getMediaNamesByAlbumNameAndUsername(albumName, username);
	}

}
