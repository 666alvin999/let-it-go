package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.SaveMemoryForm;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.memoryfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SaveMemoryFormMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public FileInfos mapToFileInfos(SaveMemoryForm saveMemoryForm) {
		try {
			return new FileInfos(
				new File(saveMemoryForm.getMultipartFile().getInputStream()),
				new FileName(saveMemoryForm.getFileName()),
				new Username(saveMemoryForm.getUsername())
			);
		} catch (Exception e) {
			return new FileInfos();
		}
	}

	public Memory mapToMemory(SaveMemoryForm saveMemoryForm) {
		return new Memory(
			new AlbumName(saveMemoryForm.getAlbumName()),
			new Username(saveMemoryForm.getUsername()),
			new Content(saveMemoryForm.getContent()),
			new MediaName(saveMemoryForm.getFileName()),
			new MemoryDatetime(LocalDateTime.parse(saveMemoryForm.getMemoryDatetime(), this.dateFormatter))
		);
	}

}
