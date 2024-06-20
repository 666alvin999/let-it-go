package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;

public interface MemoryPort {

	ActionSuccess uploadFile(FileInfos fileInfos);

}
