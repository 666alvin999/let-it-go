package org.letitgo.infrastructure.mappers;

import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.memoryfields.*;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static java.lang.String.valueOf;
import static java.util.stream.Collectors.toSet;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;

@Component
public class MemoryMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public MemoryDTO mapToDTO(Memory memory) {
		return memoryDTO()
			.albumName(memory.albumName().value())
			.username(memory.username().value())
			.textContent(memory.content().value())
			.mediaName(memory.mediaName().value())
			.memoryDatetime(this.dateFormatter.format(memory.memoryDatetime().value()))
			.mood(memory.mood().getValue())
			.build();
	}

	public Memory mapToMemory(MemoryDTO memoryDTO) {
		return new Memory(
			new AlbumName(memoryDTO.getAlbumName()),
			new Username(memoryDTO.getUsername()),
			new Content(memoryDTO.getTextContent()),
			new MediaName(memoryDTO.getMediaName()),
			new MemoryDatetime(LocalDateTime.parse(memoryDTO.getMemoryDatetime(), this.dateFormatter)),
			Mood.valueOf(memoryDTO.getMood().toUpperCase())
		);
	}

	public Set<LocalDate> mapToLocalDates(Set<String> datetimes) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return datetimes.stream().map(datetime ->
			LocalDate.parse(datetime.substring(0, 10), dateFormatter)
		).collect(toSet());
	}

}
