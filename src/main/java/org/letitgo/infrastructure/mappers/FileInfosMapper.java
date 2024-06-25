package org.letitgo.infrastructure.mappers;

import org.letitgo.domain.beans.FileInfos;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.springframework.stereotype.Component;

@Component
public class FileInfosMapper {

	public FileInfosDTO mapToDTO(FileInfos fileInfos) {
		return new FileInfosDTO(
			fileInfos.file().value(),
			"/" + fileInfos.username().value() + "/" + fileInfos.albumName().value() + "/" + fileInfos.fileName().value()
		);
	}

}
