package org.letitgo.infrastructure.adapters;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.letitgo.infrastructure.mappers.FileInfosMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemoryAdapter implements MemoryPort {

	private final DropboxDao dropboxDao;
	private final FileInfosMapper fileInfosMapper;

	@Autowired
	public MemoryAdapter(DropboxDao dropboxDao, FileInfosMapper fileInfosMapper) {
		this.dropboxDao = dropboxDao;
		this.fileInfosMapper = fileInfosMapper;
	}

	public ActionSuccess save(Memory memory) {
		return new ActionSuccess(true);
	}

	public ActionSuccess uploadFile(FileInfos fileInfos) {
		FileInfosDTO fileInfosDTO = this.fileInfosMapper.mapToDTO(fileInfos);
		return this.dropboxDao.uploadFile(fileInfosDTO);
	}

}
