package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.ports.MemoryPort;

public class DeleteMedia {

	private final MemoryPort memoryPort;

	public DeleteMedia(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public ActionSuccess execute(FileInfos fileInfos) {
		return this.memoryPort.deleteMedia(fileInfos);
	}

}
