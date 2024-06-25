package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.MemoryForm;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.memoryfields.*;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

@Component
public class MemoryFormMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public FileInfos mapToFileInfos(MemoryForm memoryForm) {
		try {
			File file;
			if (isNull(memoryForm.getMultipartFile())) {
				file = new File(null);
			} else {
				file = new File(memoryForm.getMultipartFile().getInputStream());
			}

			return new FileInfos(
				file,
				new AlbumName(memoryForm.getAlbumName()),
				new FileName(memoryForm.getFileName()),
				new Username(memoryForm.getUsername())
			);
		} catch (Exception e) {
			return new FileInfos(
				new File(null),
				new AlbumName(memoryForm.getAlbumName()),
				new FileName(null),
				new Username(null)
			);
		}
	}

	public Memory mapToMemory(MemoryForm memoryForm) {
		return new Memory(
			new AlbumName(memoryForm.getAlbumName()),
			new Username(memoryForm.getUsername()),
			new Content(memoryForm.getContent()),
			new MediaName(memoryForm.getFileName()),
			new MemoryDatetime(LocalDateTime.parse(memoryForm.getMemoryDatetime(), this.dateFormatter)),
			Mood.valueOf(memoryForm.getMood().toUpperCase())
		);
	}

}
