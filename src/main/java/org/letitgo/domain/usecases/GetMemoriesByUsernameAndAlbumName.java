package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;

import java.util.List;

public class GetMemoriesByUsernameAndAlbumName {

	private final MemoryPort memoryPort;

	public GetMemoriesByUsernameAndAlbumName(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public List<Memory> execute(String username, String albumName) {
		return this.memoryPort.getMemoriesByUsernameAndAlbumName(username, albumName);
	}

}
