package org.letitgo.application.mappers.in;

import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.fileinfosfields.Username;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SaveFileFormMapper {

	public FileInfos mapToFileInfos(MultipartFile multipartFile, String username, String fileName) {
		try {
			return new FileInfos(
				new File(multipartFile.getInputStream()),
				new FileName(fileName),
				new Username(username)
			);
		} catch (Exception e) {
			return new FileInfos();
		}
	}

}
