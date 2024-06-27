package org.letitgo.domain.usecases;

import org.letitgo.domain.ports.MemoryPort;

import java.io.InputStream;

public class DownloadFilesByUsernameAndAlbumName {

	private final MemoryPort memoryPort;

	public DownloadFilesByUsernameAndAlbumName(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public InputStream execute(String username, String albumName) {
		return this.memoryPort.getFilesByUsernameAndAlbumName(username, albumName);
	}

}
