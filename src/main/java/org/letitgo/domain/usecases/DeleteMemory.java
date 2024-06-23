package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;

public class DeleteMemory {

	private final MemoryPort memoryPort;

	public DeleteMemory(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public ActionSuccess execute(Memory memory) {
		return this.memoryPort.delete(memory);
	}

}
