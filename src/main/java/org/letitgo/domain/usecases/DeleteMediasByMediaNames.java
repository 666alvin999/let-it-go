package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.MemoryPort;

import java.util.List;

public class DeleteMediasByMediaNames {

	private final MemoryPort memoryPort;

	public DeleteMediasByMediaNames(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public ActionSuccess execute(List<String> mediaNames) {
		return this.memoryPort.deleteMediasByMediaNames(mediaNames);
	}

}
