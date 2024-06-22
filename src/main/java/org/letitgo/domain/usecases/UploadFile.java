package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.ports.MemoryPort;

public class UploadFile {

	private MemoryPort memoryPort;

	public UploadFile(MemoryPort memoryPort) {
		this.memoryPort = memoryPort;
	}

	public ActionSuccess execute(FileInfos fileInfos) {
		return this.memoryPort.uploadFile(fileInfos);
	}

}
