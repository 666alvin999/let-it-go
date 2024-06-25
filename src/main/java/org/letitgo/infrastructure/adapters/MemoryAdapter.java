package org.letitgo.infrastructure.adapters;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.daos.MemoryDao;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.letitgo.infrastructure.mappers.FileInfosMapper;
import org.letitgo.infrastructure.mappers.MemoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class MemoryAdapter implements MemoryPort {

	private final MemoryDao memoryDao;
	private final MemoryMapper memoryMapper;
	private final DropboxDao dropboxDao;
	private final FileInfosMapper fileInfosMapper;

	@Autowired
	public MemoryAdapter(MemoryDao memoryDao, MemoryMapper memoryMapper, DropboxDao dropboxDao, FileInfosMapper fileInfosMapper) {
		this.memoryDao = memoryDao;
		this.memoryMapper = memoryMapper;
		this.dropboxDao = dropboxDao;
		this.fileInfosMapper = fileInfosMapper;
	}

	public ActionSuccess save(Memory memory) {
		MemoryDTO memoryDTO = this.memoryMapper.mapToDTO(memory);
		return this.memoryDao.save(memoryDTO);
	}

	public ActionSuccess delete(Memory memory) {
		MemoryDTO memoryDTO = this.memoryMapper.mapToDTO(memory);
		return this.memoryDao.delete(memoryDTO);
	}

	public ActionSuccess uploadMedia(FileInfos fileInfos) {
		FileInfosDTO fileInfosDTO = this.fileInfosMapper.mapToDTO(fileInfos);
		return this.dropboxDao.uploadFile(fileInfosDTO);
	}

	public ActionSuccess deleteMedia(FileInfos fileInfos) {
		FileInfosDTO fileInfosDTO = this.fileInfosMapper.mapToDTO(fileInfos);
		return this.dropboxDao.deleteFile(fileInfosDTO.getFileName());
	}

	public ActionSuccess deleteMediasByMediaNames(List<String> mediaNames) {
		return this.dropboxDao.deleteMediaByMediaNames(mediaNames);
	}

	public List<String> getMediaNamesByAlbumNameAndUsername(String albumName, String username) {
		return this.memoryDao.getMediaNamesByAlbumNameAndUsername(albumName, username).stream().map(userDTO -> "/" + userDTO.getUsername() + "/" + userDTO.getAlbumName() + "/" + userDTO.getMediaName()).toList();
	}

	public Set<LocalDate> getDatesByUsername(String username) {
		Set<String> datetimes = this.memoryDao.getDatesByUsername(username);

		return this.memoryMapper.mapToLocalDates(datetimes);
	}

}
