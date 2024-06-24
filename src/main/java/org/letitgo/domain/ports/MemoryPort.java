package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;

public interface MemoryPort {

	ActionSuccess save(Memory memory);

	ActionSuccess delete(Memory memory);

	ActionSuccess uploadFile(FileInfos fileInfos);

	ActionSuccess deleteFile(FileInfos fileInfos);

}
