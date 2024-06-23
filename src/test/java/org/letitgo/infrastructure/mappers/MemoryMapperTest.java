package org.letitgo.infrastructure.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.memoryfields.*;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.MemoryDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;

class MemoryMapperTest {

	private MemoryMapper memoryMapper;

	@BeforeEach
	public void setUp() {
		this.memoryMapper = new MemoryMapper();
	}

	@Test
	public void shouldMapMemoryToMemoryDTO() {
	    // Arrange
		Memory memory = new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			new Content("salut c'est cool"),
			new MediaName("test_img.jpg"),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12))
		);

	    // Act
		MemoryDTO actualMemoryDTO = this.memoryMapper.mapToDTO(memory);

	    // Assert
		MemoryDTO expectedMemoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.textContent("salut c'est cool")
			.mediaName("test_img.jpg")
			.memoryDatetime("2024-01-01 12:12:12")
			.build();

		assertThat(actualMemoryDTO).isEqualTo(expectedMemoryDTO);
	}

	@Test
	public void shouldMapMemoryDTOToMemory() {
		// Arrange
		MemoryDTO memoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.textContent("salut c'est cool")
			.mediaName("test_img.jpg")
			.memoryDatetime("2024-01-01 12:12:12")
			.build();

		// Act
		Memory actualMemory = this.memoryMapper.mapToMemory(memoryDTO);

		// Assert
		Memory expectedMemory = new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			new Content("salut c'est cool"),
			new MediaName("test_img.jpg"),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12))
		);

		assertThat(actualMemory).isEqualTo(expectedMemory);
	}

}