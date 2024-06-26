package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface MemoryPort {

	ActionSuccess save(Memory memory);

	ActionSuccess delete(Memory memory);

	ActionSuccess uploadMedia(FileInfos fileInfos);

	ActionSuccess deleteMedia(FileInfos fileInfos);

	List<Memory> getMemoriesByUsernameAndAlbumName(String username, String albumName);

	ActionSuccess deleteMediasByMediaNames(List<String> mediaNames);

	List<String> getMediaNamesByAlbumNameAndUsername(String albumName, String username);

	Set<LocalDate> getDatesByUsername(String username);

}
