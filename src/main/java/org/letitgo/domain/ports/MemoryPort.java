package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;

import java.util.List;

public interface MemoryPort {

	ActionSuccess save(Memory memory);

	ActionSuccess delete(Memory memory);

	ActionSuccess uploadMedia(FileInfos fileInfos);

	ActionSuccess deleteMedia(FileInfos fileInfos);

	ActionSuccess deleteMediasByMediaNames(List<String> mediaNames);

	List<String> getMediaNamesByAlbumNameAndUsername(String albumName, String username);

}
