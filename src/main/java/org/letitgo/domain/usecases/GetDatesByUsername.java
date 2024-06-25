package org.letitgo.domain.usecases;

import org.letitgo.domain.ports.MemoryPort;

import java.time.LocalDate;
import java.util.Set;

public class GetDatesByUsername {

	private final MemoryPort memoryPort;

	public GetDatesByUsername(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public Set<LocalDate> execute(String username) {
		return this.memoryPort.getDatesByUsername(username);
	}
}
